from fastapi import FastAPI, HTTPException
from fastapi.responses import StreamingResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from deep_translator import GoogleTranslator
import requests
from io import BytesIO
from gtts import gTTS

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

# Models
class TranslationRequest(BaseModel):
    text: str
    target_language: str  # ISO codes: 'en', 'fr', 'ar', etc.

class VoiceRequest(BaseModel):
    text: str
    language: str | None = None
    user_id: int | None = None

# Simulated user DB (replace with real DB lookup)
USER_DB = {
    2: "arabic",     # e.g., Mohamed Ben Ammar
    3: "french"      # e.g., test@example.com
}

def get_user_language(user_id: int) -> str:
    # Fallback to English
    return USER_DB.get(user_id, "english").split(",")[0].strip().lower()

# ElevenLabs and gTTS settings
ELEVENLABS_API_KEY = "sk_a22ee6f3715cb94a63acdf1f552110d3c320cc6e5adea549"

ELEVENLABS_VOICES = {
    "english": "21m00Tcm4TlvDq8ikWAM",
    "french": None,  # fallback to gTTS
    "german": None,
    "arabic": None,
    "spanish": None,
}

GTTS_LANGUAGE_CODES = {
    "afrikaans": "af", "albanian": "sq", "arabic": "ar", "armenian": "hy",
    "bengali": "bn", "bosnian": "bs", "catalan": "ca", "chinese": "zh",
    "croatian": "hr", "czech": "cs", "danish": "da", "dutch": "nl",
    "english": "en", "esperanto": "eo", "finnish": "fi", "french": "fr",
    "german": "de", "greek": "el", "gujarati": "gu", "hindi": "hi",
    "hungarian": "hu", "icelandic": "is", "indonesian": "id", "italian": "it",
    "japanese": "ja", "javanese": "jv", "kannada": "kn", "khmer": "km",
    "korean": "ko", "latin": "la", "latvian": "lv", "lithuanian": "lt",
    "macedonian": "mk", "malay": "ms", "malayalam": "ml", "marathi": "mr",
    "myanmar (burmese)": "my", "nepali": "ne", "norwegian": "no", "polish": "pl",
    "portuguese": "pt", "punjabi": "pa", "romanian": "ro", "russian": "ru",
    "serbian": "sr", "sinhala": "si", "slovak": "sk", "slovenian": "sl",
    "spanish": "es", "sundanese": "su", "swahili": "sw", "swedish": "sv",
    "tamil": "ta", "telugu": "te", "thai": "th", "turkish": "tr",
    "ukrainian": "uk", "urdu": "ur", "vietnamese": "vi", "welsh": "cy",
    "xhosa": "xh"
}

# Translation endpoint
@app.post("/translate")
async def translate_text(request: TranslationRequest):
    try:
        translated_text = GoogleTranslator(source='auto', target=request.target_language.lower()).translate(request.text)
        return {"translated_text": translated_text}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Voice generation endpoint
@app.post("/generate-voice")
async def generate_voice(request: VoiceRequest):
    try:
        # Determine target language
        language = request.language.lower() if request.language else None

        if not language and request.user_id is not None:
            language = get_user_language(request.user_id)

        if language not in GTTS_LANGUAGE_CODES:
            raise HTTPException(status_code=400, detail=f"Unsupported language: {language}")

        voice_id = ELEVENLABS_VOICES.get(language)
        language_code = GTTS_LANGUAGE_CODES[language]

        # ElevenLabs path (English only here)
        if voice_id:
            url = f"https://api.elevenlabs.io/v1/text-to-speech/{voice_id}"
            headers = {
                "Accept": "audio/mpeg",
                "Content-Type": "application/json",
                "xi-api-key": ELEVENLABS_API_KEY
            }
            payload = {
                "text": request.text,
                "voice_settings": {
                    "stability": 0.5,
                    "similarity_boost": 0.5
                }
            }
            response = requests.post(url, json=payload, headers=headers)
            if response.status_code != 200:
                raise HTTPException(status_code=500, detail="ElevenLabs API Error: " + response.text)

            audio_bytes = BytesIO(response.content)
            audio_bytes.seek(0)
            return StreamingResponse(audio_bytes, media_type="audio/mpeg")

        # Fallback to gTTS (non-English)
        tts = gTTS(text=request.text, lang=language_code)
        audio_bytes = BytesIO()
        tts.write_to_fp(audio_bytes)
        audio_bytes.seek(0)
        return StreamingResponse(audio_bytes, media_type="audio/mpeg")

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Run with uvicorn
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8001)
