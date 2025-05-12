import os
from pathlib import Path
import requests
from flask import Flask, request, jsonify
from dotenv import load_dotenv


load_dotenv(dotenv_path=Path(__file__).parent / "chat.env")
class HeritageChatbot:
    def __init__(self):
        self.api_key = os.getenv("GEMINI_API_KEY")  # Utilise la variable d'environnement
        print(f"API Key: {self.api_key}")
        if not self.api_key:
            raise ValueError("GEMINI_API_KEY environment variable is not set")
        self.model = "models/gemini-1.5-flash"
        self.api_url = f"https://generativelanguage.googleapis.com/v1beta/{self.model}:generateContent"
        self.system_prompt = self._load_system_prompt()
        self.conversation_history = []

    def _load_system_prompt(self):
        prompt_path = Path(__file__).parent / "prompts" / "system_prompt.txt"
        with open(prompt_path, 'r', encoding='utf-8') as f:
            return f.read().strip()

    def chat(self, user_message, language="en"):
        headers = {
            "Content-Type": "application/json"
        }

        # Construction du contenu de la requête
        contents = []

        # Ajout du prompt système si existant
        if self.system_prompt:
            contents.append({
                "role": "user",
                "parts": [{"text": self.system_prompt}]
            })

        # Historique (Google Gemini ne distingue pas "user"/"assistant", on passe une séquence de 'parts')
        for entry in self.conversation_history[-5:]:
            contents.append({
                "role": entry["role"],
                "parts": [{"text": entry["content"]}]
            })

        # Message actuel de l'utilisateur
        contents.append({
            "role": "user",
            "parts": [{"text": user_message}]
        })

        payload = {
            "contents": contents,
            "generationConfig": {
                "temperature": 0.7,
                "maxOutputTokens": 1000
            }
        }

        response = requests.post(
            f"{self.api_url}?key={self.api_key}",
            headers=headers,
            json=payload
        )

        if response.status_code != 200:
            print("Status Code:", response.status_code)
            print("Response Text:", response.text)
            return None, response.status_code, response.text

        reply = response.json()['candidates'][0]['content']['parts'][0]['text']
        self.conversation_history.append({"role": "user", "content": user_message})
        self.conversation_history.append({"role": "model", "content": reply})

        return reply, 200, None

# Flask app setup
app = Flask(__name__)
chatbot = HeritageChatbot()

@app.route('/chat', methods=['POST'])
def handle_chat():
    data = request.json
    user_message = data.get('message')
    language = data.get('language', 'en')

    if not user_message:
        return jsonify({"error": "Missing 'message' in request"}), 400

    reply, status_code, error = chatbot.chat(user_message, language)

    if status_code != 200:
        return jsonify({"error": "Failed to get a valid response", "details": error}), status_code

    return jsonify({"response": reply}), 200

if __name__ == '__main__':
    app.run(port=5001)
