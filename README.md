# TurathAI Backend

## 🚀 Overview
TurathAI Backend powers the **TurathAI** platform, an AI-driven initiative to promote sustainable tourism by showcasing Tunisia’s rich heritage. Built with **Spring Boot**, this backend provides robust APIs for managing users, heritage sites, events, reviews, and more.

---

## ✨ Features
- 🔹 **User Management**: Register, authenticate, and manage user profiles.
- 🏛 **Heritage Site Management**: Add, update, and explore Tunisian heritage sites.
- 🎉 **Event Management**: Create and manage cultural and historical events.
- ⭐ **Review System**: Users can leave reviews and ratings for heritage sites.
- 🤖 **AI-Powered Recommendations**: Personalized suggestions based on user interactions.
- 🎮 **Gamification**: Engage users with badges, points, and rewards.

---

## 🛠 Technologies Used
- **Backend**: Spring Boot, Java
- **Database**: MySQL (or any relational database)
- **AI Services**: Python, Flask, Pandas
- **API Documentation**: Swagger/OpenAPI
- **Version Control**: Git, GitHub
- **Build Tool**: Maven
- **Testing**: JUnit, Mockito
- **Deployment**: Docker, AWS (optional)

---

## 🚀 Getting Started

### ✅ Prerequisites
Ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Maven 3.x
- MySQL or any relational database
- Git
- Python 3.11 or higher
- Virtualenv (for Python environments)

### 📥 Installation

#### 1. Clone the Repository
```bash
git clone https://github.com/MarwenDev2/TurathAI-Backend.git
cd TurathAI-Backend
   ```
2. **Configure Database**: Update `application.properties` with your database credentials.
3. **Build & Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. **Access API Documentation**: Visit `http://localhost:8080/swagger-ui.html`
5. Set Up and Run AI Services

The backend integrates with three AI services implemented in Python, located in the python-ai directory. Follow these steps to set them up:

a. Navigate to the AI Services Directory :
cd python-ai

b. Set Up a Virtual Environment :
python -m venv venv
.\venv\Scripts\activate

c. Install Dependencies !
.\venv\Scripts\activate
pip install flask python-dotenv pandas flask-cors requests

e. Run the AI Services

Heritage Chatbot Service (Handles conversational AI for heritage-related queries):
bash

Copy
python heritage_chatbot.py
This service runs on http://127.0.0.1:5001.
Heritage Connector Service (Processes heritage data, requires pandas):
bash

Copy
python heritage_connector.py
Ensure any additional configuration (e.g., database credentials) is set if required by this script.

---

## 📌 Contributing
We welcome contributions! Follow these steps:
1. Fork the repository.
2. Create a new branch: `git checkout -b feature-branch`
3. Make your changes and commit: `git commit -m "Added new feature"`
4. Push to your fork and submit a pull request.
