# SWRMS — Sanitary Ware Retail Management System (API Prototype v1)

## Overview
First functional REST API prototype for the **Sanitary Ware Retail Management System (SWRMS)**. Built with Java 21 and Spring Boot 3.4.1 using in-memory mocked data to validate the resource-oriented API contract, Bearer token Role-Based Access Control (RBAC), payload validation, and structured JSON envelopes prior to connecting a production database.

> **Note on Persistence:** Mock product data is stored in memory and resets automatically whenever the server restarts.

---

## Installation & Running

### Prerequisites
- **Java 21+**
- **Maven 3.9+** (or the included Maven Wrapper `./mvnw`)

### Steps
1. Clone the repository:

   ```bash
   git clone https://github.com/AhmadliAhmad/Group12-Project.git
   cd Group12-Project
   ```

2. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows, use `mvnw.cmd spring-boot:run`.

3. Open the HTML greeting page in your browser:

   ```text
   http://localhost:8080/api/v1/greeting
   ```

## Mock Tokens & Test Data

All `/api/v1/products` endpoints require an `Authorization: Bearer <token>` header:

| Role | Token | Permissions & Initial Resources |
|---|---|---|
| Regular User A | `user-a-token` | Full CRUD on own products (`prod_101`, `prod_102`); `403 Forbidden` on User B's resources |
| Regular User B | `user-b-token` | Full CRUD on own products (`prod_103`, `prod_104`); `403 Forbidden` on User A's resources |
| Administrator | `admin-token` | Full CRUD access across all products in the system |

Non-existent ID for `404 Not Found` testing: `prod_9999`.

- Full API Reference: See [`API_DOCUMENTATION.md`](API_DOCUMENTATION.md).
- Postman Collection: Import `swrms_v1_postman_collection.json`.

## Contributors & Git Activity

See [`CONTRIBUTORS.md`](CONTRIBUTORS.md) for detailed role breakdowns:

Yavar Jafarli (20489) — Project architecture, /api/v1/products CRUD operations, in-memory mock data setup, and Bearer token RBAC.

Youtube Video Link : https://youtu.be/h_vYt9ejSrE

Ahmad Ahmadli (20012) — Request payload validation, JSON envelope responses, HTML greeting endpoint, Postman collection, and API documentation.

```text
18  AhmadliAhmad
```
