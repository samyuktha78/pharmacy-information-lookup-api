# pharmacy-information-lookup-api
RESTful API (Java Spring Boot) simulating a pharmacy information system with CRUD endpoints for drug records, prescription validation logic, and relational DB integration.


## Overview
A RESTful backend service simulating a pharmacy information system. Provides CRUD operations for drug/medication records, prescription validation logic, and integrates with a relational database — reflecting real-world pharmacy and healthcare information system workflows.

## Features
- CRUD REST endpoints for drug/medication records
- Prescription validation logic (dosage limits, drug interaction checks, expiry checks)
- Relational DB schema (PostgreSQL/MySQL) for drugs, prescriptions, patients
- Search/filter endpoints (by drug name, category, prescription status)
- Exception handling and input validation
- Unit and integration tests (JUnit, Mockito)

## Tech Stack
- Java 17, Spring Boot
- Spring Data JPA, Hibernate
- SQL (PostgreSQL/MySQL)
- JUnit 5, Mockito
- Maven/Gradle

## Project Structure
```
src/main/java/com/pharmacy/
  controller/      # REST endpoints
  service/         # Business logic (validation rules)
  repository/      # DB access layer
  model/           # Entity classes (Drug, Prescription, Patient)
  exception/       # Custom exception handling
src/test/java/com/pharmacy/
  ...unit and integration tests
```

## API Endpoints (planned)
- `GET /api/drugs` - list all drugs
- `GET /api/drugs/{id}` - get drug by ID
- `POST /api/drugs` - add new drug
- `PUT /api/drugs/{id}` - update drug info
- `DELETE /api/drugs/{id}` - remove drug
- `POST /api/prescriptions/validate` - validate a prescription request

## Getting Started
1. Clone the repo
2. Configure `application.properties` with your DB credentials
3. Run `mvn spring-boot:run`
4. Access API at `http://localhost:8080/api/drugs`

## Roadmap
- [ ] Define DB schema (drugs, prescriptions, patients)
- [ ] Build CRUD endpoints for drug records
- [ ] Implement prescription validation logic
- [ ] Add unit/integration tests
- [ ] Write API documentation (Swagger/OpenAPI)
