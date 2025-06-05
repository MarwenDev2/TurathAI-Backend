package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Services.IUserService;

import java.util.Map;

@RestController
@RequestMapping("/api/narration")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NarrationController {

    private final IUserService userService;
    private final RestTemplate restTemplate;

    private static final Map<String, String> LANGUAGE_MAP = Map.ofEntries(
            Map.entry("afrikaans", "af"), Map.entry("albanian", "sq"), Map.entry("arabic", "ar"),
            Map.entry("armenian", "hy"), Map.entry("bengali", "bn"), Map.entry("bosnian", "bs"),
            Map.entry("catalan", "ca"), Map.entry("chinese", "zh"), Map.entry("croatian", "hr"),
            Map.entry("czech", "cs"), Map.entry("danish", "da"), Map.entry("dutch", "nl"),
            Map.entry("english", "en"), Map.entry("esperanto", "eo"), Map.entry("finnish", "fi"),
            Map.entry("french", "fr"), Map.entry("german", "de"), Map.entry("greek", "el"),
            Map.entry("gujarati", "gu"), Map.entry("hindi", "hi"), Map.entry("hungarian", "hu"),
            Map.entry("icelandic", "is"), Map.entry("indonesian", "id"), Map.entry("italian", "it"),
            Map.entry("japanese", "ja"), Map.entry("javanese", "jv"), Map.entry("kannada", "kn"),
            Map.entry("khmer", "km"), Map.entry("korean", "ko"), Map.entry("latin", "la"),
            Map.entry("latvian", "lv"), Map.entry("lithuanian", "lt"), Map.entry("macedonian", "mk"),
            Map.entry("malay", "ms"), Map.entry("malayalam", "ml"), Map.entry("marathi", "mr"),
            Map.entry("myanmar (burmese)", "my"), Map.entry("nepali", "ne"), Map.entry("norwegian", "no"),
            Map.entry("polish", "pl"), Map.entry("portuguese", "pt"), Map.entry("punjabi", "pa"),
            Map.entry("romanian", "ro"), Map.entry("russian", "ru"), Map.entry("serbian", "sr"),
            Map.entry("sinhala", "si"), Map.entry("slovak", "sk"), Map.entry("slovenian", "sl"),
            Map.entry("spanish", "es"), Map.entry("sundanese", "su"), Map.entry("swahili", "sw"),
            Map.entry("swedish", "sv"), Map.entry("tamil", "ta"), Map.entry("telugu", "te"),
            Map.entry("thai", "th"), Map.entry("turkish", "tr"), Map.entry("ukrainian", "uk"),
            Map.entry("urdu", "ur"), Map.entry("vietnamese", "vi"), Map.entry("welsh", "cy"),
            Map.entry("xhosa", "xh")
    );

    @PostMapping("/voice")
    public ResponseEntity<byte[]> generateNarration(
            @RequestBody Map<String, String> body,
            @RequestHeader("X-User-Email") String userEmail) {

        try {
            String inputText = body.get("text");
            if (inputText == null || inputText.isBlank()) {
                return ResponseEntity.badRequest().body("Missing text".getBytes());
            }

            User user = userService.findUserByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found".getBytes());
            }

            String spokenLanguageRaw = user.getSpokenLanguage();
            String[] spokenLanguages = spokenLanguageRaw.split(",");
            String spokenLanguage = spokenLanguages[0].trim().toLowerCase(); // First one is default

            String languageCode = LANGUAGE_MAP.getOrDefault(spokenLanguage, "en");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 1. Translate
            String translateUrl = "http://localhost:8001/translate";
            String translationJson = new JSONObject()
                    .put("text", inputText)
                    .put("target_language", languageCode)
                    .toString();

            HttpEntity<String> translationRequest = new HttpEntity<>(translationJson, headers);
            ResponseEntity<String> translationResponse = restTemplate.postForEntity(translateUrl, translationRequest, String.class);

            if (!translationResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.internalServerError().body("Translation error".getBytes());
            }

            String translatedText = new JSONObject(translationResponse.getBody()).getString("translated_text");

            // 2. Generate Voice
            String voiceUrl = "http://localhost:8001/generate-voice";
            String voiceJson = new JSONObject()
                    .put("text", translatedText)
                    .put("language", spokenLanguage)
                    .toString();

            HttpEntity<String> voiceRequest = new HttpEntity<>(voiceJson, headers);
            ResponseEntity<byte[]> voiceResponse = restTemplate.postForEntity(voiceUrl, voiceRequest, byte[].class);

            if (!voiceResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.internalServerError().body("Voice generation error".getBytes());
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("audio/mpeg"))
                    .body(voiceResponse.getBody());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Server error: " + e.getMessage()).getBytes());
        }
    }
}

