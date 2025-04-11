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
