# WorkTime

REST API for tracking employee work hours. Built with Spring Boot 3.4.1 and Java 21.

## Overview

WorkTime allows you to register employees and track their daily work sessions. The API provides a simple toggle mechanism - sending a POST request starts a work session, and sending another ends it. This makes it easy to integrate with frontend applications or CLI tools.

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Spring Data JPA + Hibernate
- H2 (in-memory database)
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Bean Validation (Jakarta)
- Spring Boot Actuator
- JUnit 5 + Mockito + MockMvc

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven (or use the included Maven Wrapper)

### Run the application

```bash
./mvnw spring-boot:run
```

The application starts on port `8080` by default.

### Available endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/employees/get` | Get all employees |
| `GET` | `/employees/get/{id}` | Get employee by ID |
| `POST` | `/employees/post` | Create a new employee |
| `DELETE` | `/employees/delete/{id}` | Delete an employee |
| `POST` | `/work-time/{employeeId}` | Start or end work session (toggle) |
| `GET` | `/work-time/{employeeId}` | Check if work session is active |

### Swagger UI

After starting the application, visit:

```
http://localhost:8080/swagger-ui.html
```

### H2 Console

```
http://localhost:8080/h2-console
```

JDBC URL: `jdbc:h2:mem:worktime`, Username: `sa`, Password: *(empty)*

## API Examples

### Create an employee

```bash
curl -X POST http://localhost:8080/employees/post \
  -H "Content-Type: application/json" \
  -d '{"name": "Jan Kowalski"}'
```

Response:
```json
{
  "id": 1,
  "name": "Jan Kowalski"
}
```

### Start work session

```bash
curl -X POST http://localhost:8080/work-time/1
```

### Check if working

```bash
curl http://localhost:8080/work-time/1
```

Response: `true` (working) or `false` (not working)

### End work session

```bash
curl -X POST http://localhost:8080/work-time/1
```

## Validation

The API validates input data and returns meaningful error messages:

| Field | Rule |
|-------|------|
| `name` | Required, 1-128 characters |
| `id` | Must be `null` on creation (auto-generated) |
| Path variables | Must be positive numbers |

Error response example:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "name: Name is required"
}
```

## Project Structure

```
src/main/java/com/my/TimeWork/
  TimeWorkApplication.java        -- Application entry point
  controller/
    EmployeeController.java       -- Employee CRUD endpoints
    WorkTimeController.java       -- Work time tracking endpoints
    GlobalExceptionHandler.java   -- Centralized error handling
  dto/
    EmployeeDto.java              -- Data transfer object (input/output)
    ErrorResponse.java            -- Error response format
  entity/
    Employee.java                 -- JPA entity
    WorkTime.java                 -- JPA entity
  repository/
    EmployeeRepository.java       -- Spring Data JPA repository
    WorkTimeRepository.java       -- Spring Data JPA repository
  service/
    EmployeeService.java          -- Employee business logic
    WorkTimeService.java          -- Work time business logic
```

## Testing

```bash
./mvnw test
```

The project includes:

- **Unit tests** (Mockito) - service layer logic
- **Integration tests** (SpringBootTest + H2) - full context with database
- **Controller tests** (WebMvcTest + MockMvc) - HTTP layer with validation

## Database

Uses H2 in-memory database by default. Schema is created automatically on startup from `schema.sql`.

To switch to a persistent database (e.g. MariaDB), update `application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/worktime
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
```
