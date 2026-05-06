# System Zarządzania Zasobami i Oprogramowaniem (ASMS)

Kompleksowe RESTful API zbudowane przy użyciu **Spring Boot 3.2.0** do zarządzania zasobami IT (komputerami) oraz instalacjami oprogramowania w organizacji.

## Opis

ASMS umożliwia administratorom IT oraz użytkownikom:
- Śledzenie komputerów i zasobów sprzętowych wraz z ich systemami operacyjnymi
- Zarządzanie katalogiem oprogramowania z obsługą wersjonowania
- Rejestrowanie instalacji oprogramowania na poszczególnych maszynach
- Oznaczanie przestarzałego oprogramowania i przypisywanie zadań aktualizacyjnych administratorom
- Dodawanie komentarzy do zasobów i wpisów oprogramowania
- Zabezpieczanie wszystkich operacji za pomocą uwierzytelniania JWT i kontroli dostępu opartej na rolach

---

## Wymagania wstępne

- **Java 17+**
- **MySQL 8.0+** (lub baza danych H2 in-memory do celów deweloperskich)
- **Maven 3.6+**

---

## Instalacja

```bash
git clone https://github.com/KonHaw-s25453/Projekt_JAVA_EE.git
cd Projekt_JAVA_EE
mvn install -DskipTests
```

---

## Konfiguracja

### Konfiguracja MySQL

Utwórz bazę danych i użytkownika:

```sql
CREATE DATABASE asms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'asms_user'@'localhost' IDENTIFIED BY 'twoje_haslo';
GRANT ALL PRIVILEGES ON asms.* TO 'asms_user'@'localhost';
FLUSH PRIVILEGES;
```

Zaktualizuj plik `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/asms?useSSL=false&serverTimezone=UTC
spring.datasource.username=asms_user
spring.datasource.password=twoje_haslo
```

---

## Uruchamianie aplikacji

### Domyślny tryb (MySQL)

```bash
mvn spring-boot:run
```

### Profil deweloperski (baza H2 in-memory)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Lub za pomocą Javy:

```bash
java -Dspring.profiles.active=dev -jar target/asms-1.0.0.jar
```

### Profil produkcyjny

```bash
java -Dspring.profiles.active=prod \
  -DDB_HOST=twoj-host-bazy \
  -DDB_NAME=asms \
  -DDB_USERNAME=asms_user \
  -DDB_PASSWORD=twoje_haslo \
  -jar target/asms-1.0.0.jar
```

Aplikacja uruchamia się pod adresem **http://localhost:8080**

---

## Endpointy API

### Uwierzytelnianie (`/api/auth`)

| Metoda | Endpoint              | Opis                          | Wymagane uwierzytelnienie |
|--------|-----------------------|-------------------------------|---------------------------|
| POST   | `/api/auth/register`  | Rejestracja nowego użytkownika | Nie                       |
| POST   | `/api/auth/login`     | Logowanie i pobranie tokenu JWT | Nie                      |

### Użytkownicy (`/api/users`)

| Metoda | Endpoint          | Opis                        | Rola     |
|--------|-------------------|-----------------------------|----------|
| GET    | `/api/users`      | Lista wszystkich użytkowników | ADMIN  |
| GET    | `/api/users/{id}` | Pobierz użytkownika po ID   | ADMIN    |
| PUT    | `/api/users/{id}` | Zaktualizuj użytkownika     | ADMIN    |
| DELETE | `/api/users/{id}` | Usuń użytkownika            | ADMIN    |

### Komputery (`/api/computers`)

| Metoda | Endpoint                  | Opis                              | Rola              |
|--------|---------------------------|-----------------------------------|-------------------|
| POST   | `/api/computers`          | Dodaj komputer                    | ADMIN             |
| GET    | `/api/computers`          | Lista wszystkich komputerów       | Zalogowany        |
| GET    | `/api/computers/{id}`     | Pobierz komputer po ID            | Zalogowany        |
| PUT    | `/api/computers/{id}`     | Zaktualizuj komputer              | ADMIN             |
| DELETE | `/api/computers/{id}`     | Usuń komputer                     | ADMIN             |
| GET    | `/api/computers/search`   | Wyszukaj po lokalizacji/statusie  | Zalogowany        |
| GET    | `/api/computers/stats`    | Statystyki komputerów             | ADMIN             |

### Katalog oprogramowania (`/api/software`)

| Metoda | Endpoint                        | Opis                                    | Rola       |
|--------|---------------------------------|-----------------------------------------|------------|
| POST   | `/api/software`                 | Dodaj oprogramowanie do katalogu        | ADMIN      |
| GET    | `/api/software`                 | Lista całego oprogramowania             | Zalogowany |
| GET    | `/api/software/{id}`            | Pobierz oprogramowanie po ID            | Zalogowany |
| PUT    | `/api/software/{id}`            | Zaktualizuj oprogramowanie              | ADMIN      |
| DELETE | `/api/software/{id}`            | Usuń oprogramowanie                     | ADMIN      |
| GET    | `/api/software/{id}/versions`   | Lista wersji oprogramowania             | Zalogowany |
| POST   | `/api/software/{id}/versions`   | Dodaj wersję oprogramowania             | ADMIN      |

### Zainstalowane oprogramowanie (`/api/installed-software`)

| Metoda | Endpoint                              | Opis                                    | Rola       |
|--------|---------------------------------------|-----------------------------------------|------------|
| POST   | `/api/installed-software`             | Zarejestruj instalację                  | ADMIN      |
| GET    | `/api/installed-software`             | Lista wszystkich instalacji             | Zalogowany |
| GET    | `/api/installed-software/{id}`        | Pobierz instalację po ID                | Zalogowany |
| DELETE | `/api/installed-software/{id}`        | Usuń instalację                         | ADMIN      |
| PUT    | `/api/installed-software/{id}/flag`   | Oznacz do aktualizacji                  | Zalogowany |
| PUT    | `/api/installed-software/{id}/status` | Zmień status aktualizacji               | ADMIN      |
| GET    | `/api/installed-software/outdated`    | Lista przestarzałych instalacji         | Zalogowany |
| PUT    | `/api/installed-software/{id}/assign` | Przypisz do administratora              | ADMIN      |

### Komentarze (`/api/comments`)

| Metoda | Endpoint                                  | Opis                            | Rola       |
|--------|-------------------------------------------|---------------------------------|------------|
| POST   | `/api/comments`                           | Dodaj komentarz                 | Zalogowany |
| GET    | `/api/comments`                           | Lista wszystkich komentarzy     | Zalogowany |
| GET    | `/api/comments/{id}`                      | Pobierz komentarz po ID         | Zalogowany |
| PUT    | `/api/comments/{id}`                      | Zaktualizuj komentarz           | Zalogowany |
| DELETE | `/api/comments/{id}`                      | Usuń komentarz                  | ADMIN      |
| GET    | `/api/comments/public`                    | Lista publicznych komentarzy    | Zalogowany |
| GET    | `/api/comments/entity/{type}/{entityId}`  | Komentarze do danego zasobu     | Zalogowany |

---

## Przepływ uwierzytelniania

1. **Rejestracja** użytkownika przez `POST /api/auth/register` z podaniem `username`, `email`, `password` oraz `role` (`ROLE_USER` lub `ROLE_ADMIN`).
2. **Logowanie** przez `POST /api/auth/login` z podaniem `username` i `password`. W odpowiedzi otrzymujesz token JWT.
3. **Użycie tokenu** w kolejnych zapytaniach jako token Bearer:
   ```
   Authorization: Bearer <twoj-token-jwt>
   ```

---

## Swagger / OpenAPI

Po uruchomieniu aplikacji przejdź pod adres:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## Struktura projektu

```
src/main/java/com/asms/
├── AsmsApplication.java          # Główny punkt wejścia
├── config/
│   ├── JpaConfig.java            # Konfiguracja JPA + ModelMapper
│   └── SecurityConfig.java       # Konfiguracja Spring Security + JWT
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

## Użyte technologie

| Technologia             | Wersja   | Przeznaczenie                         |
|-------------------------|----------|---------------------------------------|
| Spring Boot             | 3.2.0    | Framework aplikacyjny                 |
| Spring Security         | 6.x      | Uwierzytelnianie i autoryzacja        |
| Spring Data JPA         | 3.x      | Warstwa dostępu do bazy danych        |
| Hibernate               | 6.x      | ORM                                   |
| MySQL Connector/J       | 8.0.33   | Sterownik JDBC do MySQL               |
| H2 Database             | -        | Baza in-memory do celów deweloperskich|
| JJWT                    | 0.12.3   | Generowanie i walidacja tokenów JWT   |
| Lombok                  | -        | Redukcja kodu szablonowego            |
| ModelMapper             | 3.1.1    | Mapowanie obiektów                    |
| SpringDoc OpenAPI       | 2.0.2    | Dokumentacja API (Swagger UI)         |
| Java                    | 17       | Język programowania                   |
| Maven                   | 3.6+     | Narzędzie do budowania projektu       |
