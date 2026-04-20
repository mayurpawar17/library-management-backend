# 📚 Library Management System (Spring Boot)

A scalable and production-ready **Library Management System API** built using **Spring Boot**, following clean architecture principles and best practices.

---

# 🚀 Features

## 👤 Authentication & Authorization

* User Registration & Login
* JWT-based Authentication
* Role-Based Access Control (ADMIN, USER)

## 📖 Book Management

* Add, Update, Delete Books (Admin only)
* View all books
* Search books by title, author, category
* Pagination & sorting support

## 🔄 Book Issue System

* Issue books to users
* Return books
* Track issued books
* Availability management

## 💰 Fine System

* Automatic fine calculation for late returns
* Configurable fine per day

## ⚙️ Backend Features

* Global Exception Handling
* DTO-based API design
* Clean layered architecture
* Validation support
* RESTful APIs

---

# 🏗️ Tech Stack

* **Backend:** Spring Boot
* **Database:** PostgreSQL / MySQL
* **ORM:** Spring Data JPA (Hibernate)
* **Security:** Spring Security + JWT
* **Build Tool:** Maven / Gradle
* **Other Tools:** Lombok, MapStruct (optional)

---

# 📁 Project Structure

```
src/main/java/com/example/library
│── controller/        # REST Controllers
│── service/           # Business logic interfaces
│   ├── impl/          # Service implementations
│── repository/        # JPA repositories
│── entity/            # Database entities
│── dto/               # Request & Response DTOs
│── mapper/            # DTO ↔ Entity mapping
│── security/          # JWT & Spring Security config
│── exception/         # Custom exceptions & handlers
│── config/            # App configurations
│── util/              # Utility classes
```

---

# 🧩 Database Design

## 📘 Book

| Field           | Type   |
| --------------- | ------ |
| id              | Long   |
| title           | String |
| author          | String |
| category        | String |
| totalCopies     | int    |
| availableCopies | int    |

## 👤 User

| Field    | Type   |
| -------- | ------ |
| id       | Long   |
| name     | String |
| email    | String |
| password | String |
| role     | Enum   |

## 🔄 IssueRecord

| Field      | Type      |
| ---------- | --------- |
| id         | Long      |
| user       | User      |
| book       | Book      |
| issueDate  | LocalDate |
| dueDate    | LocalDate |
| returnDate | LocalDate |
| fine       | double    |

---

# 🔌 API Endpoints

## 🔐 Auth APIs

```
POST /api/auth/register
POST /api/auth/login
```

## 📚 Book APIs

```
POST   /api/books        # Add book (ADMIN)
GET    /api/books        # Get all books (pagination)
GET    /api/books/{id}   # Get book by ID
PUT    /api/books/{id}   # Update book (ADMIN)
DELETE /api/books/{id}   # Delete book (ADMIN)
```

## 🔄 Issue APIs

```
POST /api/issues/{bookId}/user/{userId}   # Issue book
POST /api/issues/return/{recordId}        # Return book
```

---

# ⚙️ Setup & Installation

## 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/library-management.git
cd library-management
```

## 2️⃣ Configure Database

Update `application.yml` or `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 3️⃣ Run Application

```bash
mvn spring-boot:run
```

App will run at:

```
http://localhost:8080
```

---

# 🔐 Security Overview

* JWT Token-based authentication
* Password encryption using BCrypt
* Role-based endpoint protection

---

# 🧪 Testing APIs

Use tools like:

* Postman
* Thunder Client

---

# 📈 Future Improvements

* Redis caching for performance
* Elasticsearch for advanced search
* Email notifications (due reminders)
* Admin dashboard (analytics)
* Docker & CI/CD pipeline

---

# 🚀 Deployment

You can deploy using:

* Render
* AWS EC2
* Docker + Kubernetes

---

# 🤝 Contribution

Contributions are welcome!

1. Fork the repo
2. Create a new branch
3. Commit your changes
4. Open a Pull Request

---

# 📜 License

This project is licensed under the MIT License.

---

# 💡 Author

Developed by **Your Name**

---

# ⭐ Support

If you like this project, give it a ⭐ on GitHub!
