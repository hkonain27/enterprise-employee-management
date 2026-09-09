# Enterprise Employee Management & AI Assistant

Starter full-stack project using:
- Java 21
- Spring Boot 3
- Spring Security
- JWT
- MongoDB
- Angular

## Backend
```bash
cd backend
mvn spring-boot:run
```

MongoDB should be running locally on `mongodb://localhost:27017/employee_management`.

## Frontend
```bash
cd frontend
npm install
npm start
```

Open http://localhost:4200

## Starter users
Create users through `/api/auth/register`.

Example admin:
```json
{
  "name": "Admin User",
  "email": "admin@example.com",
  "password": "Password123!",
  "role": "ADMIN"
}
```

## Main endpoints
- POST `/api/auth/register`
- POST `/api/auth/login`
- GET `/api/employees`
- GET `/api/employees/{id}`
- POST `/api/employees`
- PUT `/api/employees/{id}`
- DELETE `/api/employees/{id}`
- GET `/api/employees/search?q=...`
- GET `/api/assistant/ask?q=...`

The AI assistant is intentionally implemented as a safe contextual assistant over employee data, not as a generic LLM. You can later replace the service logic with an LLM/tool-calling layer.
