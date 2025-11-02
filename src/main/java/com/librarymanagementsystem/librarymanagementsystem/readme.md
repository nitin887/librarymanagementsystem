# Library Management System

## Table of Contents

## Project Overview

The Library Management System is a robust **Spring Boot** application designed to streamline the management of library resources. It provides a **RESTful API** for handling books, authors, patrons, and borrowing records. The system is secured using **JWT (JSON Web Token) authentication** and **role-based authorization**, ensuring that only authorized users can perform specific actions. This project aims to demonstrate modern backend development practices with Spring Boot, Spring Security, and a clear API design.

## Features

*   **Book Management:** Comprehensive CRUD (Create, Read, Update, Delete) operations for books.
*   **Author Management:** Dedicated endpoints to manage author details and their associated books.
*   **Patron Management:** Functionality to register new library patrons and manage their information.
*   **Borrowing Records:** Efficient tracking of book borrowing and return processes.
*   **User Authentication:** Secure user registration and login mechanisms powered by JWT.
*   **Role-Based Authorization:** Granular access control based on user roles (PATRON, LIBRARIAN, ADMIN, USER).
*   **API Documentation:** Interactive and auto-generated API documentation using Swagger UI (OpenAPI 3).

## Technologies Used

*   **Spring Boot:** `3.2.6` - Rapid application development framework.
*   **Spring Security:** For robust authentication and authorization.
*   **JWT (JSON Web Token):** `io.jsonwebtoken:jjwt-api:0.11.5` - For stateless and secure API authentication.
*   **Spring Data JPA:** Simplifies data access and persistence with Hibernate.
*   **MySQL/H2 Database:** Primary database is MySQL; H2 is available for in-memory development and testing.
*   **Maven:** `3.6.0+` - Project build automation and dependency management.
*   **MapStruct:** `1.5.5.Final` - Code generator for simplified object mapping.
*   **Swagger UI (OpenAPI 3):** `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0` - Interactive API documentation.
*   **Java:** `17`

## Getting Started

### Prerequisites

Ensure you have the following installed on your system:

*   **Java Development Kit (JDK) 17 or higher**
*   **Apache Maven 3.6.0 or higher**
*   **A MySQL database instance** (or you can opt for the in-memory H2 database for local development)

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/librarymanagementsystem.git
    cd librarymanagementsystem
    ```

2.  **Build the project:**
    ```bash
    mvn clean install
    ```
    This command compiles the code, runs tests, and packages the application.

### Configuration

The application's configuration is managed via `src/main/resources/application.properties`.

**For MySQL Database:**

Update the following properties with your MySQL database details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

**For H2 In-Memory Database (Development/Testing):**

If you prefer to use H2, comment out the MySQL properties and uncomment/add these:

```properties
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Running the Application

Execute the following Maven command from the project root:

```bash
mvn spring-boot:run
```

The application will start and be accessible at `http://localhost:8080` by default.

## API Documentation (Swagger UI)

### Accessing Swagger UI

Once the application is running, you can access the interactive API documentation in your web browser:

http://localhost:8080/swagger-ui.html

### Authorizing Requests in Swagger UI

To test protected endpoints directly from Swagger UI:

1.  Click the **"Authorize"** button (or the lock icon next to any protected endpoint).
2.  In the dialog that appears, locate the `bearerAuth` section.
3.  Enter your obtained JWT token in the format: `Bearer <YOUR_JWT_TOKEN>`.
    *   **Example:** `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsInJvbGVzIjoiVVNFUiIsImlhdCI6MTY3ODg4NjQwMCwiZXhwIjoxNjc4ODg4MjAwfQ.signature`
4.  Click "Authorize" and then "Close".

All subsequent requests made from Swagger UI will now include this JWT token in the `Authorization` header.

## Authentication and Authorization

The system uses JWT for securing its RESTful APIs.

### 1. User Registration

To create a new user account, send a `POST` request to the registration endpoint.

*   **Endpoint:** `POST /api/auth/register`
*   **Request Body Example:**
    ```json
    {
      "username": "newuser",
      "password": "securepassword",
      "roles": "USER"
    }
    ```
*   **Available Roles:** `PATRON`, `LIBRARIAN`, `ADMIN`, `USER`. For multiple roles, use a comma-separated string (e.g., `"LIBRARIAN,ADMIN"`).

### 2. User Login and Token Acquisition

To obtain a JWT token for authentication, send a `POST` request to the login endpoint.

*   **Endpoint:** `POST /api/auth/login`
*   **Request Body Example:**
    ```json
    {
      "username": "newuser",
      "password": "securepassword"
    }
    ```
*   **Successful Response:** Returns `200 OK` with the JWT token as a plain string in the response body.
*   **Failed Login:** Returns `401 Unauthorized` with a message like `"Failed to login: Invalid username or password"`.

### 3. Using the JWT Token

Once you have successfully logged in and received a JWT token, you must include it in the `Authorization` header of all subsequent requests to protected API endpoints.

*   **Header Format:** `Authorization: Bearer <YOUR_JWT_TOKEN>`

**Example `curl` command for login:**

```bash
curl -X POST \
  http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Example `curl` command to access a protected resource (after obtaining a token):**

```bash
curl -X GET \
  http://localhost:8080/api/books/1 \
  -H 'Authorization: Bearer <YOUR_JWT_TOKEN>'
```

### Role-Based Access Control

The application enforces the following access rules based on user roles:

*   **Publicly Accessible Endpoints (`.permitAll()`):**
    *   `/`, `/index.html`, `/api/auth/register`, `/api/auth/login`, `/login.html`
    *   `/v3/api-docs/**`, `/swagger-ui/**`, `/swagger-ui.html` (Swagger UI documentation endpoints)

*   **Accessible by `PATRON`, `LIBRARIAN`, `ADMIN`, `USER` roles:**
    *   `GET /api/books/**` (View book details)
    *   `GET /api/authors/**` (View author details)
    *   `GET /api/borrowing-records/**` (View borrowing records)
    *   `GET /api/patrons/**` (View patron details)
    *   `POST /api/borrowing-records/**` (Borrow a book)

*   **Accessible by `LIBRARIAN`, `ADMIN` roles only:**
    *   `POST /api/books/**` (Add new books)
    *   `POST /api/authors/**` (Add new authors)
    *   `POST /api/patrons/**` (Add new patrons)
    *   `PUT /api/books/**` (Update book details)
    *   `PUT /api/authors/**` (Update author details)
    *   `PUT /api/borrowing-records/**` (Update borrowing records, e.g., return book)
    *   `PUT /api/patrons/**` (Update patron details)
    *   `DELETE /api/books/**` (Delete books)
    *   `DELETE /api/authors/**` (Delete authors)
    *   `DELETE /api/borrowing-records/**` (Delete borrowing records)
    *   `DELETE /api/patrons/**` (Delete patrons)

*   **All other requests:** Require any authenticated user (`.authenticated()`).

## Database Schema

The application utilizes a relational database to store its data. The core entities and their relationships are:

*   **`User`**: Stores user authentication details (username, password, roles).
*   **`Book`**: Represents a book in the library, linked to an `Author`.
*   **`Author`**: Stores information about book authors.
*   **`Patron`**: Represents a library member, linked to `BorrowingRecord`s.
*   **`BorrowingRecord`**: Tracks which `Patron` has borrowed which `Book`, including borrow and return dates.

The database schema is automatically managed by Hibernate (via Spring Data JPA) based on the defined JPA entities.

## Contributing

Contributions to this project are highly welcome! If you have suggestions, bug reports, or want to contribute code, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and ensure tests pass.
4.  Submit a pull request with a clear description of your changes.

## License

```
MIT License

Copyright (c) [year] [fullname]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
