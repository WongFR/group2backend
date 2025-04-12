# 🎮 Group2Backend

A backend system built with **Java 17**, **Spring Boot**, **MySQL**, and **MyBatis**, allowing users to:

- Register and log in
- Browse game info from the [RAWG](https://rawg.io/apidocs) API
- Post and view game comments
- (Upcoming) Create and join teams

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.x
- MySQL 8.x
- MyBatis
- JWT (JSON Web Tokens) for authentication
- RAWG API for game data

---

## 📁 Project Structure

```
com.example.group2backend/
├── controller/                      # Endpoints
│   └── bodies/                      # Request/response DTOs
│
├── database/
│   ├── entity/                      # Entity classes mapped to DB
│   ├── mapper/                      # MyBatis Mapper interfaces
│   └── service/                     # CURD service
│
├── service/                         # Advanced service
│   ├── JwtUtil                      # Token generation/validation
│   ├── JwtAuthenticationFilter      # JWT filter for authentication
│   └── AppConfig, SecurityConfig    # Spring Security setup
│
└── Group2backendApplication         # Application entry point
```

---

## ⚙️ Configuration

### application.yml

```yaml
rawg:
  api:
    base-url: https://api.rawg.io/api
    key: your_key

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/group2?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
    username: root
    password: your_password_here
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  type-aliases-package: com.example.group2backend.database.entity
```

---

## 🧰 Setup Instructions

### ✅ 1. Create MySQL Database

```sql
CREATE DATABASE group2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Example table (for users):

```sql
CREATE TABLE comment (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         game_id INT NOT NULL,
                         user_id BIGINT NOT NULL,
                         content TEXT NOT NULL,
                         timestamp DATETIME(6) NOT NULL
);

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100),
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```



### ✅ 2. Build and Run

```bash
./gradlew build
java -jar build/libs/gameconnect-backend-0.0.1-SNAPSHOT.jar
```

Server runs at: `http://localhost:8080`

---

## 🔐 Authentication

The system uses stateless JWT-based authentication.

- On login, you’ll receive a JWT token.
- For protected endpoints, include it in headers like this:

```http
Authorization: Bearer <your-token>
```

---

## 📦 API Overview

### 👤 User

| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| POST   | /user/register      | Register a new user       |
| POST   | /user/login         | Login and receive token   |
| GET    | /user/me            | Get current user info     |

### 🎮 Game

| Method | Endpoint                 | Description                     |
|--------|--------------------------|---------------------------------|
| GET    | /game/{id}               | Get game details from RAWG      |
| GET    | /game/{id}/comments      | View comments for a game        |
| POST   | /game/{id}/comments      | Add a comment (requires token)  |

---
## ▶️ To Run the App

1. Make sure MySQL is running and database `group2` is created
2. Configure credentials in `application.yml`
3. Then run:

```bash
./gradlew build
java -jar build/libs/gameconnect-backend-0.0.1-SNAPSHOT.jar
```

Runs at: `http://localhost:8080`

---

## 📄 License

MIT License. Free to use and modify.



### 🔄 Comment Interaction Feature( Implimented by group 6 )

Users can now interact with comments through likes and dislikes:

- Like/dislike comments on games
- Toggle votes (clicking twice removes a vote)
- Switch vote type (from like to dislike or vice versa)

#### Database Schema Addition

```sql
CREATE TABLE comment_vote (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    comment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    is_like BOOLEAN NOT NULL,
    timestamp DATETIME(6) NOT NULL,
    
    -- Constraints
    CONSTRAINT fk_comment_vote_comment FOREIGN KEY (comment_id) REFERENCES comment(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_vote_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_comment_vote_user UNIQUE (comment_id, user_id)
);

-- Indexes for performance
CREATE INDEX idx_comment_vote_comment ON comment_vote(comment_id);
CREATE INDEX idx_comment_vote_user ON comment_vote(user_id);