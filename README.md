# Simple Banking API
Beginner-oriented Spring Boot REST API sample. This is an educational project, not production banking software.

## Requirements
- Java 21
- MySQL 8+
- IntelliJ IDEA
- Git

## Database
In MySQL Workbench run:
```sql
CREATE DATABASE banking_db;
```

## Configuration
Edit `src/main/resources/application.properties` and set your MySQL username/password.

## Run
In the project root:
```bash
mvn spring-boot:run
```
Or use IntelliJ's Run button on `SimpleBankingApplication`.

## API endpoints
- `POST /api/accounts` create account: `{"customerName":"Asha","initialDeposit":1000}`
- `GET /api/accounts` list accounts
- `GET /api/accounts/{id}` retrieve account
- `POST /api/accounts/{id}/deposit` deposit: `{"amount":500}`
- `POST /api/accounts/{id}/withdraw` withdraw: `{"amount":100}`
- `POST /api/transfers` transfer: `{"fromAccountId":1,"toAccountId":2,"amount":100}`
- `GET /api/accounts/{id}/transactions` transaction history

This demo omits authentication, authorization, fraud monitoring, audit controls, rate limits, secrets management, formal compliance and deployment hardening. Do not use with real funds or sensitive customer data.
