import threading
import subprocess

python_path = r"C:\Program Files\Python313\python.exe"

def run_heritage_connector():
    subprocess.run([python_path, "heritage_connector.py"])

def run_heritage_chatbot():
    subprocess.run([python_path, "-m", "chatbot.heritage_chatbot"])

thread1 = threading.Thread(target=run_heritage_connector)
thread2 = threading.Thread(target=run_heritage_chatbot)

thread1.start()
thread2.start()

thread1.join()
thread2.join()
