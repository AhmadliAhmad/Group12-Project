# SWRMS REST API v1 Documentation

## 1. Authentication & Authorization
All `/api/v1/products` endpoints require a Bearer token in the `Authorization` header:
```http
Authorization: Bearer <token>
```

| Role | Token | Permissions |
|---|---|---|
| Regular User A | `user-a-token` | Can list, view, create, update, and delete only user-a products |
| Regular User B | `user-b-token` | Can list, view, create, update, and delete only user-b products |
| Administrator | `admin-token` | Full CRUD access to all products across all users |

### Design Decision (403 Forbidden vs 404 Not Found)

When an authenticated regular user (user-a) attempts to read, update, or delete a resource owned by another user (user-b), the API returns 403 Forbidden (FORBIDDEN). In an internal retail inventory system (SWRMS), product IDs are shared branch catalog identifiers; explicitly informing the seller that they lack permission for another branch's record is clearer for POS troubleshooting than pretending the product does not exist.

## 2. Endpoints

### 2.1 HTML Greeting Page
Endpoint: GET /api/v1/greeting

Auth: None (Public)

Response (200 OK, text/html; charset=utf-8): Renders a responsive HTML page containing the project description, team members, endpoint table, and running status (v1).

### 2.2 List Products
Endpoint: GET /api/v1/products

Headers: Authorization: Bearer <token>

Query Parameters: page (default: 1), perPage (default: 10, max: 100)

Success Response (`200 OK`):

```json
{
  "success": true,
  "message": "Products retrieved successfully.",
  "data": [
    {
      "id": "prod_101",
      "name": "Grohe Eurosmart Basin Mixer",
      "barcode": "4005176865015",
      "category": "Mixers",
      "purchasePrice": 85.00,
      "salePrice": 125.00,
      "stockQuantity": 18,
      "ownerId": "user-a",
      "createdAt": "2026-11-22T16:00:00Z",
      "updatedAt": "2026-11-22T16:00:00Z"
    }
  ],
  "meta": {
    "total": 2,
    "page": 1,
    "perPage": 10
  }
}
```

Error Responses: 400 Bad Request (invalid pagination), 401 Unauthorized (missing/invalid token).

### 2.3 Get Single Product
Endpoint: GET /api/v1/products/{id}

Headers: Authorization: Bearer <token>

Path Parameter: id (e.g., prod_101; use prod_9999 to test 404 Not Found)

Success Response (200 OK): Returns single product inside data.

Error Responses: 401 Unauthorized, 403 Forbidden (owned by another user), 404 Not Found.

### 2.4 Create Product
Endpoint: POST /api/v1/products

Headers: Authorization: Bearer <token>, Content-Type: application/json

Validation Rules:

name: Required string, 3–100 chars.

barcode: Required unique string, 5–30 chars.

category: Required string, 2–50 chars.

purchasePrice: Required positive number (> 0).

salePrice: Required positive number (> 0).

stockQuantity: Required integer (>= 0).

Unknown fields are rejected with 400 Bad Request.

Example Request:

```json
{
  "name": "Hansgrohe Raindance Showerhead",
  "barcode": "4011097732109",
  "category": "Showers",
  "purchasePrice": 140.00,
  "salePrice": 210.00,
  "stockQuantity": 15
}
```
Success Response (201 Created):

```json
{
  "success": true,
  "message": "Product created successfully.",
  "data": {
    "id": "prod_105",
    "name": "Hansgrohe Raindance Showerhead",
    "barcode": "4011097732109",
    "category": "Showers",
    "purchasePrice": 140.00,
    "salePrice": 210.00,
    "stockQuantity": 15,
    "ownerId": "user-a",
    "createdAt": "2026-11-22T16:10:00Z",
    "updatedAt": "2026-11-22T16:10:00Z"
  }
}
```
Validation Error Response (400 Bad Request):

```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "The request contains invalid or missing fields.",
    "details": [
      {
        "field": "stockQuantity",
        "message": "Stock quantity is required and must be an integer >= 0."
      }
    ]
  }
}
```
Other Error Responses: 401 Unauthorized, 409 Conflict (duplicate barcode).

### 2.5 Update Product
Endpoint: PUT /api/v1/products/{id}

Headers: Authorization: Bearer <token>, Content-Type: application/json

Success Response (200 OK): Returns updated product object inside data.

Error Responses: 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict.

### 2.6 Delete Product
Endpoint: DELETE /api/v1/products/{id}

Headers: Authorization: Bearer <token>

Success Response (200 OK):

```json
{
  "success": true,
  "message": "Product deleted successfully.",
  "data": null
}
```
Error Responses: 401 Unauthorized, 403 Forbidden, 404 Not Found.
