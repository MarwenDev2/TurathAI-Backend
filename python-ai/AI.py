import threading
import subprocess
import os
import sys

# Get the current directory (where AI.py is located)
current_dir = os.path.dirname(os.path.abspath(__file__))
python_path = r"C:\Program Files\Python313\python.exe"

def run_heritage_connector():
    # Run heritage_connector.py with the correct path
    subprocess.run([python_path, os.path.join(current_dir, "heritage_connector.py")])

def run_heritage_chatbot():
    # Add the current directory to sys.path so Python can find the chatbot module
    os.environ["PYTHONPATH"] = current_dir
    subprocess.run([python_path, os.path.join(current_dir, "chatbot", "heritage_chatbot.py")])

thread1 = threading.Thread(target=run_heritage_connector)
thread2 = threading.Thread(target=run_heritage_chatbot)

thread1.start()
thread2.start()

thread1.join()
thread2.join()
