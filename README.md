<<<<<<< HEAD
# TurathAI-Backend
## Installation
1. Install Python 3.8
2. Install Pipenv
3. Install Python dependencies
```
pipenv install
```
4. Run the server
```
pipenv run python manage.py runserver
```
## Usage
### Endpoints
- `/api/register/` [POST]: Register a new user
- `/api/login/` [POST]: Login
- `/api/logout/` [POST]: Logout
- `/api/user/` [GET]: Get the current user
- `/api/user/` [PUT]: Update the current user
- `/api/user/` [DELETE]: Delete the current user
- `/api/user/` [PATCH]: Change the current user's password
- `/api/user/` [POST]: Send a password reset email
- `/api/user/reset_password/` [POST]: Reset the password using the token from the email
- `/api/ai/` [POST]: Get the AI response
- `/api/ai/` [DELETE]: Delete the AI response
- `/api/ai/` [PUT]: Update the AI response
- `/api/ai/` [PATCH]: Update the AI response's status
- `/api/ai/` [GET]: Get the AI response
- `/api/ai/` [POST]: Create an AI response
- `/api/ai/` [PATCH]: Update the AI response's status
- `/api/ai/` [GET]: Get the AI response
- `/api/ai/` [POST]: Create an AI response
- `/api/ai/` [GET]: Get the
=======
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

### 📥 Installation
1. **Clone the Repository**:
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

---

## 📌 Contributing
We welcome contributions! Follow these steps:
1. Fork the repository.
2. Create a new branch: `git checkout -b feature-branch`
3. Make your changes and commit: `git commit -m "Added new feature"`
4. Push to your fork and submit a pull request.
>>>>>>> main
