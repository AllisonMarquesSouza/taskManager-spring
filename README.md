
# ✅ Task Manager API

A **RESTful API** built with **Java + Spring Boot**, designed to manage tasks and comments. Users can register, log in, and perform task operations such as create, update, filter, and delete — all with simple endpoints.

## 🚀 Overview

**Version:** 1.0  
**API Spec:** OpenAPI 3.0 (OAS 3.0)  
**Docs:** `/v3/api-docs`  
**Purpose:** API to manage personal tasks efficiently.  




## 🌍 Deployment

The application is deployed at:

**🔗 Live API URL:** https://taskmanager-spring-4rt7.onrender.com/swagger-ui/index.html#/

## 📬 Contact

- **Email:** allisonsouza10261@gmail.com


## 📄 License

This project is licensed under the terms of your choice.
## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- Hibernate
- PostgreSQL
- Flyway
- Maven
- Lombok
- Swagger / OpenAPI

## 🧱 Folder Structure
```
src/
├── controller
├── service
├── repository
├── model
├── dto
├── config
├── security
└── exception
```

## 🔐 Authentication

| Method | Endpoint         | Description                        |
|--------|------------------|------------------------------------|
| POST   | `/auth/register` | Create your account                |
| POST   | `/auth/login`    | Log in with username and password  |

## 📋 Tasks Endpoints

| Method | Endpoint                                           | Description                       |
|--------|----------------------------------------------------|-----------------------------------|
| POST   | `/tasks/create`                                    | Create a task                     |
| PATCH  | `/tasks/updateStatus/{userId}/{taskId}`            | Update task status                |
| PATCH  | `/tasks/updatePriority/{userId}/{taskId}`          | Update task priority              |
| PATCH  | `/tasks/updateName/{userId}/{taskId}`              | Update task name                  |
| PATCH  | `/tasks/updateDueDate/{userId}/{taskId}`           | Update task due date              |
| PATCH  | `/tasks/updateDescription/{userId}/{taskId}`       | Update task description           |
| PATCH  | `/tasks/completeTask/{userId}/{taskId}`            | Mark task as completed            |
| GET    | `/tasks/listAll/{userId}`                          | Get all tasks by user ID          |
| GET    | `/tasks/getById/{taskId}`                          | Get task by ID                    |
| GET    | `/tasks/filter/{userId}`                           | Filter tasks (by name, status...) |
| DELETE | `/tasks/delete/{userId}/{taskId}`                  | Delete task                       |


## 💬 Comments Endpoints

| Method | Endpoint                                            | Description                         |
|--------|-----------------------------------------------------|-------------------------------------|
| POST   | `/comments/create`                                  | Create a comment for a task         |
| PATCH  | `/comments/updateComment/{userId}/{commentId}`      | Update a comment                    |
| GET    | `/comments/getAllByUserId/{userId}`                 | Get all comments by user ID         |
| DELETE | `/comments/delete/{userId}/{commentId}`             | Delete a comment                    |

## 🔐 Authorization

JWT-based security is implemented. Use the `Authorize` button in Swagger UI or attach the token in headers like this:

```
Authorization: Bearer <your_token_here>
```

## 📝 Swagger Documentation

Swagger/OpenAPI docs are available at:

```
/v3/api-docs
```

You can also use **Swagger UI** (if added) at:

```
/swagger-ui.html
```

## ✅ Future Improvements

- Pagination for task lists
- Task assignment between users
- Comment reactions
- Admin dashboard


## 📌 How to Run Locally

```bash
# Clone repository
git clone https://github.com/yourusername/task-manager-api.git
cd task-manager-api

# Run with Maven
./mvnw spring-boot:run
```

Before you run, make sure you set your database credentials in `application.properties`.


## 📂 Database Tables

- `users`
- `tasks`
- `comments`


## 📎 Contributing

Pull requests are welcome. For major changes, please open an issue first or send me an email.
