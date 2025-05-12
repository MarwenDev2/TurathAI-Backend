# python-ai/chatbot/config.py
import os
from dotenv import load_dotenv

load_dotenv()

class Config:
    OPENAI_API_KEY = os.getenv("OPENAI_API_KEY", "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")
    CHAT_MODEL = "gpt-4"
    MAX_TOKENS = 1000