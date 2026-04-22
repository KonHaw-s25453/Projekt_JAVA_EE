# Asset & Software Management System (ASMS)

A comprehensive RESTful API built with **Spring Boot 3.2.0** for managing IT assets (computers) and software installations across an organization.

## Description

ASMS enables IT administrators and users to:
- Track computers/hardware assets with their operating systems
- Manage a software catalog with versioning
- Record software installations per machine
- Flag outdated software and assign update tasks to admins
- Leave comments on assets and software entries
- Secure all operations with JWT-based authentication and role-based access control

---

## Prerequisites

- **Java 17+**
- **MySQL 8.0+** (or use the H2 in-memory database for development)
- **Maven 3.6+**

---

## Installation

```bash
git clone https://github.com/KonHaw-s25453/Projekt_JAVA_EE.git
cd Projekt_JAVA_EE
mvn install -DskipTests
```

---

## Configuration

### MySQL Setup

Create a database and user:

```sql
CREATE DATABASE asms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'asms_user'@'localhost' IDENTIFIED BY 'yourpassword';
GRANT ALL PRIVILEGES ON asms.* TO 'asms_user'@'localhost';
FLUSH PRIVILEGES;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/asms?useSSL=false&serverTimezone=UTC
spring.datasource.username=asms_user
spring.datasource.password=yourpassword
```

---

## Running the Application

### Default (MySQL)

```bash
mvn spring-boot:run
```

### Development Profile (H2 in-memory database)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Or with Java:

```bash
java -jar target/asms-1.0.0.jar -Dspring.profiles.active=dev
```

### Production Profile

```bash
java -jar target/asms-1.0.0.jar \
  -Dspring.profiles.active=prod \
  -DDB_HOST=your-db-host \
  -DDB_NAME=asms \
  -DDB_USERNAME=asms_user \
  -DDB_PASSWORD=yourpassword
```

The application starts on **http://localhost:8080**

---

## API Endpoints

### Authentication (`/api/auth`)

| Method | Endpoint              | Description           | Auth Required |
|--------|-----------------------|-----------------------|---------------|
| POST   | `/api/auth/register`  | Register a new user   | No            |
| POST   | `/api/auth/login`     | Login and get JWT     | No            |

### Users (`/api/users`)

| Method | Endpoint          | Description         | Role     |
|--------|-------------------|---------------------|----------|
| GET    | `/api/users`      | List all users      | ADMIN    |
| GET    | `/api/users/{id}` | Get user by ID      | ADMIN    |
| PUT    | `/api/users/{id}` | Update user         | ADMIN    |
| DELETE | `/api/users/{id}` | Delete user         | ADMIN    |

### Computers (`/api/computers`)

| Method | Endpoint                  | Description           | Role        |
|--------|---------------------------|-----------------------|-------------|
| POST   | `/api/computers`          | Create computer       | ADMIN       |
| GET    | `/api/computers`          | List all computers    | Authenticated |
| GET    | `/api/computers/{id}`     | Get computer by ID    | Authenticated |
| PUT    | `/api/computers/{id}`     | Update computer       | ADMIN       |
| DELETE | `/api/computers/{id}`     | Delete computer       | ADMIN       |
| GET    | `/api/computers/search`   | Search by location/status | Authenticated |
| GET    | `/api/computers/stats`    | Computer statistics   | ADMIN       |

### Software Catalog (`/api/software`)

| Method | Endpoint                        | Description              | Role        |
|--------|---------------------------------|--------------------------|-------------|
| POST   | `/api/software`                 | Add software to catalog  | ADMIN       |
| GET    | `/api/software`                 | List all software        | Authenticated |
| GET    | `/api/software/{id}`            | Get software by ID       | Authenticated |
| PUT    | `/api/software/{id}`            | Update software          | ADMIN       |
| DELETE | `/api/software/{id}`            | Delete software          | ADMIN       |
| GET    | `/api/software/{id}/versions`   | List software versions   | Authenticated |
| POST   | `/api/software/{id}/versions`   | Add software version     | ADMIN       |

### Installed Software (`/api/installed-software`)

| Method | Endpoint                              | Description                   | Role        |
|--------|---------------------------------------|-------------------------------|-------------|
| POST   | `/api/installed-software`             | Record installation           | ADMIN       |
| GET    | `/api/installed-software`             | List all installations        | Authenticated |
| GET    | `/api/installed-software/{id}`        | Get installation by ID        | Authenticated |
| DELETE | `/api/installed-software/{id}`        | Remove installation           | ADMIN       |
| PUT    | `/api/installed-software/{id}/flag`   | Flag for update               | Authenticated |
| PUT    | `/api/installed-software/{id}/status` | Change update status          | ADMIN       |
| GET    | `/api/installed-software/outdated`    | List outdated installations   | Authenticated |
| PUT    | `/api/installed-software/{id}/assign` | Assign to admin               | ADMIN       |

### Comments (`/api/comments`)

| Method | Endpoint                                  | Description              | Role        |
|--------|-------------------------------------------|--------------------------|-------------|
| POST   | `/api/comments`                           | Add comment              | Authenticated |
| GET    | `/api/comments`                           | List all comments        | Authenticated |
| GET    | `/api/comments/{id}`                      | Get comment by ID        | Authenticated |
| PUT    | `/api/comments/{id}`                      | Update comment           | Authenticated |
| DELETE | `/api/comments/{id}`                      | Delete comment           | ADMIN       |
| GET    | `/api/comments/public`                    | List public comments     | Authenticated |
| GET    | `/api/comments/entity/{type}/{entityId}`  | Comments by entity       | Authenticated |

---

## Authentication Flow

1. **Register** a user via `POST /api/auth/register` with `username`, `email`, `password`, and `role` (`ROLE_USER` or `ROLE_ADMIN`).
2. **Login** via `POST /api/auth/login` with `username` and `password`. You receive a JWT token.
3. **Use the token** in subsequent requests as a Bearer token:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

---

## Swagger / OpenAPI

When the application is running, visit:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## Project Structure

```
src/main/java/com/asms/
├── AsmsApplication.java          # Main entry point
├── config/
│   ├── JpaConfig.java            # JPA + ModelMapper config
│   └── SecurityConfig.java       # Spring Security + JWT config
├── controller/
│   ├── UserController.java
│   ├── ComputerController.java
│   ├── SoftwareController.java
│   ├── InstalledSoftwareController.java
│   └── CommentController.java
├── dto/
│   ├── UserDTO.java
│   ├── ComputerDTO.java
│   ├── InstalledSoftwareDTO.java
│   └── CommentDTO.java
├── entity/
│   ├── User.java
│   ├── OperatingSystem.java
│   ├── Computer.java
│   ├── SoftwareCatalog.java
│   ├── SoftwareVersion.java
│   ├── Compatibility.java
│   ├── InstalledSoftware.java
│   ├── Comment.java
│   └── AuditLog.java
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── UserRepository.java
│   ├── ComputerRepository.java
│   ├── SoftwareCatalogRepository.java
│   ├── SoftwareVersionRepository.java
│   ├── InstalledSoftwareRepository.java
│   └── CommentRepository.java
├── security/
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── service/
    ├── UserService.java
    ├── ComputerService.java
    ├── SoftwareService.java
    ├── InstalledSoftwareService.java
    └── CommentService.java
```

---

## Technologies

| Technology              | Version  | Purpose                          |
|-------------------------|----------|----------------------------------|
| Spring Boot             | 3.2.0    | Application framework            |
| Spring Security         | 6.x      | Authentication & authorization   |
| Spring Data JPA         | 3.x      | Database access layer            |
| Hibernate               | 6.x      | ORM                              |
| MySQL Connector/J       | 8.0.33   | MySQL JDBC driver                |
| H2 Database             | -        | In-memory DB for development     |
| JJWT                    | 0.12.3   | JWT generation & validation      |
| Lombok                  | -        | Boilerplate reduction            |
| ModelMapper             | 3.1.1    | Object mapping                   |
| SpringDoc OpenAPI       | 2.0.2    | API documentation (Swagger UI)   |
| Java                    | 17       | Programming language             |
| Maven                   | 3.6+     | Build tool                       |
