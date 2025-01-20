# HMSApp

**HMSApp** is a Spring Boot-based application for managing hotel or property reservations, reviews, and more. It provides REST APIs for authentication, booking, property management, and review handling.

---

## Features

- **Authentication**: Secure login with JWT-based authentication.
- **Booking Management**: Create, update, and view bookings.
- **Property Management**: Manage properties and their availability.
- **Image Handling**: Upload and retrieve property images.
- **Reviews**: Add and view reviews for properties.
- **AWS S3 Integration**: Store and retrieve files securely.
- **Twilio Integration**: Send SMS notifications.

---

## Technologies Used

- **Backend**: Java, Spring Boot
- **Database**: MySQL
- **Cloud Storage**: AWS S3
- **SMS Notifications**: Twilio
- **Build Tool**: Maven
- **Authentication**: JWT

---

## Prerequisites

1. **Java**: Ensure JDK 17 or later is installed.
2. **Maven**: Install Apache Maven to manage dependencies.
3. **MySQL**: Set up a MySQL database and configure connection details.
4. **AWS**: Configure S3 credentials for file handling.
5. **Twilio**: Set up Twilio account credentials for SMS services.

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd HMSApp
   ```

2. Configure application properties:
    - Edit `src/main/resources/application.properties` with your database, AWS, and Twilio credentials.

3. Build the project:
   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Access the application:
    - The APIs will be available at `http://localhost:8080`.
    - You can also access the API using swagger-ui at `http://localhost:8080/swagger-ui/index.html`.

---

## Project Structure

- **`src/main/java/com/HMSApp`**: Main application codebase.
    - **`config`**: Configuration files for security, JWT, AWS, and Twilio.
    - **`controller`**: REST controllers for handling API requests.
    - **`entity`**: JPA entities for database tables.
    - **`repository`**: Repositories for database operations.
    - **`service`**: Service layer for business logic.
    - **`payload`**: DTOs for API requests and responses.

- **`src/main/resources`**: Resource files.
    - **`application.properties`**: Configuration file.

---

## Usage

- **Endpoints**:
    - Authentication: `/api/auth/login`
    - Bookings: `/api/bookings`
    - Properties: `/api/properties`
    - Reviews: `/api/reviews`

Refer to the API documentation for details.

---

## Contributors

- E Srinibash

---

## License

This project is licensed under the MIT License.

---

