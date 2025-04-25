# 🎮 Group2Backend

A backend system built with **Java 17**, **Spring Boot**, **MySQL**, and **MyBatis**, allowing users to:

- Register and log in
- Browse game information via the [RAWG API](https://rawg.io/apidocs)
- Post and view comments on games
- like comments
- Create and join teams
- upload file
---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.x
- MySQL 8.x
- MyBatis
- JWT (JSON Web Tokens) for authentication
- Swagger for API documentation
- RAWG API for game data

---

## ⚙️ Project Setup

### 1️⃣ Create the MySQL Database

Start your MySQL server and run the following SQL to create the database and necessary tables:

```sql
CREATE DATABASE group2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Sample tables:

```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  username VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  email VARCHAR(255),
  phone VARCHAR(50),
  bio TEXT,
  favorite_genres VARCHAR(255),
  created_at DATETIME,
  updated_at DATETIME,
  avatar VARCHAR(255)
);

CREATE TABLE comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  game_id INT NOT NULL,
  user_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  timestamp DATETIME(6) NOT NULL,
  like_count BIGINT NOT NULL
);

CREATE TABLE team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  game_id INT,
  creator_id BIGINT NOT NULL,
  team_name VARCHAR(100) NOT NULL,
  team_size INT,
  description TEXT,
  from_time DATETIME,
  to_time DATETIME,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  member_ids TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE comment_like (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  comment_id BIGINT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uniq_user_comment (user_id, comment_id)
);

CREATE TABLE join_team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
  request_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  host_id BIGINT NOT NULL,
  UNIQUE KEY uniq_user_team (team_id, user_id)
);
```

---

### 2️⃣ Configure the Application

Add `src/main/resources/application.yml`:

(To make things easier, I’ve shared the RAWG API key with you via Discord.)
```yaml
rawg:
  api:
    base-url: https://api.rawg.io/api
    key: your_key_here

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/group2?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
    username: your_username
    password: your_password_here
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  type-aliases-package: com.example.group2backend.database.entity
  configuration:
    map-underscore-to-camel-case: true
```

---

### 3️⃣ Build and Run

```bash
./gradlew build
java -jar build/libs/gameconnect-backend-0.0.1-SNAPSHOT.jar
```

📍 Server will run at: `http://localhost:8080`

---

### 📜 API Docs

After you launch the project, Swagger UI is available at:  
📄 `http://localhost:8080/swagger-ui/index.html`
---

## 📁 Project Structure

```
com.example.group2backend/
├── controller/                  # REST controllers
│   └── bodies/                  # Request/response DTOs
│
├── database/
│   ├── entity/                  # Entity classes mapped to MySQL
│   ├── mapper/                  # MyBatis Mapper interfaces
│   └── service/                 # Basic CRUD service layer
│
├── service/                     # Business logic services
│   └── model/                   # Advanced DTOs
│
└── Group2backendApplication     # Spring Boot application entry
```

---

## 🔐 Authentication

This project uses **JWT-based stateless authentication**.

- After login, you’ll receive a JWT token.
- Include it in the `Authorization` header:

```http
Authorization: Bearer <your-token>
```

---

## 📄 License

MIT License. Free to use, modify, and distribute.