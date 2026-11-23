package com.retail.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MockProductV1Controller {

    private final List<MockProduct> products = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(105);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Set<String> ALLOWED_FIELDS = Set.of(
            "name", "barcode", "category", "purchasePrice", "salePrice", "stockQuantity"
    );

    @PostConstruct
    public void initMockData() {
        String now = Instant.now().toString();
        products.add(MockProduct.builder()
                .id("prod_101")
                .name("Grohe Eurosmart Basin Mixer")
                .barcode("4005176865015")
                .category("Mixers")
                .purchasePrice(new BigDecimal("85.00"))
                .salePrice(new BigDecimal("125.00"))
                .stockQuantity(18)
                .ownerId("user-a")
                .createdAt(now)
                .updatedAt(now)
                .build());

        products.add(MockProduct.builder()
                .id("prod_102")
                .name("Vitra S50 Wall-Hung Ceramic Basin")
                .barcode("8697221010421")
                .category("Sanitary Ceramics")
                .purchasePrice(new BigDecimal("110.00"))
                .salePrice(new BigDecimal("160.00"))
                .stockQuantity(9)
                .ownerId("user-a")
                .createdAt(now)
                .updatedAt(now)
                .build());

        products.add(MockProduct.builder()
                .id("prod_103")
                .name("Geberit Duofix Concealed Cistern")
                .barcode("4025416538103")
                .category("Installation Systems")
                .purchasePrice(new BigDecimal("190.00"))
                .salePrice(new BigDecimal("265.00"))
                .stockQuantity(12)
                .ownerId("user-b")
                .createdAt(now)
                .updatedAt(now)
                .build());

        products.add(MockProduct.builder()
                .id("prod_104")
                .name("PPR Hot Water Pipe 25mm (4m)")
                .barcode("8680123456789")
                .category("Pipes & Fittings")
                .purchasePrice(new BigDecimal("6.50"))
                .salePrice(new BigDecimal("10.00"))
                .stockQuantity(140)
                .ownerId("user-b")
                .createdAt(now)
                .updatedAt(now)
                .build());
    }

    @GetMapping(value = "/greeting", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> greeting() {
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <title>SWRMS — Sanitary Ware Retail Management System API</title>
                    <style>
                        * { box-sizing: border-box; margin: 0; padding: 0; }
                        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; background: #f4f7fb; color: #1e293b; line-height: 1.6; padding: 24px 16px; }
                        .container { max-width: 900px; margin: 0 auto; background: #ffffff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.06); padding: 32px; }
                        header { border-bottom: 2px solid #e2e8f0; padding-bottom: 20px; margin-bottom: 24px; display: flex; flex-wrap: wrap; justify-content: space-between; align-items: center; gap: 12px; }
                        h1 { font-size: 1.65rem; color: #0f172a; }
                        .badge { background: #dcfce7; color: #166534; font-weight: 600; padding: 6px 14px; border-radius: 999px; font-size: 0.875rem; }
                        p.desc { color: #475569; margin-bottom: 24px; font-size: 1rem; }
                        h2 { font-size: 1.2rem; color: #0f172a; margin: 24px 0 12px; }
                        .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 14px; }
                        .card { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 14px; }
                        .card strong { display: block; color: #0f172a; }
                        .card span { font-size: 0.85rem; color: #64748b; }
                        .card p { font-size: 0.9rem; margin-top: 6px; color: #334155; }
                        table { width: 100%; border-collapse: collapse; margin-top: 8px; font-size: 0.92rem; }
                        th, td { text-align: left; padding: 12px 10px; border-bottom: 1px solid #e2e8f0; }
                        th { background: #f1f5f9; color: #334155; font-weight: 600; }
                        .method { display: inline-block; padding: 3px 8px; border-radius: 5px; font-weight: 700; font-size: 0.78rem; color: #fff; }
                        .get { background: #0284c7; }
                        .post { background: #16a34a; }
                        .put { background: #d97706; }
                        .del { background: #dc2626; }
                        code { background: #f1f5f9; padding: 2px 6px; border-radius: 4px; font-family: monospace; }
                        @media (max-width: 600px) { .container { padding: 20px; } th, td { padding: 8px 6px; font-size: 0.84rem; } }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <header>
                            <h1>SWRMS — Sanitary Ware Retail Management System</h1>
                            <span class="badge">Status: Running &bull; Version: v1</span>
                        </header>
                        <p class="desc">
                            SWRMS is a RESTful backend API prototype designed for sanitary ware retail stores and branch managers to manage product catalogs, inventory levels, and branch-level stock ownership.
                        </p>

                        <h2>Team Members</h2>
                        <div class="grid">
                            <div class="card">
                                <strong>Yavar Jafarli</strong>
                                <span>Student ID: 20489</span>
                                <p>Resource-oriented CRUD endpoints, mock data initialization, Bearer token RBAC, and project architecture.</p>
                            </div>
                            <div class="card">
                                <strong>Ahmad Ahmadli</strong>
                                <span>Student ID: 20012</span>
                                <p>Input validation rules, structured JSON error envelopes, HTML greeting endpoint, and API documentation.</p>
                            </div>
                        </div>

                        <h2>Available API Endpoints</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Method</th>
                                    <th>Path</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><span class="method get">GET</span></td>
                                    <td><code>/api/v1/greeting</code></td>
                                    <td>Displays this HTML project overview, team details, and endpoint list.</td>
                                </tr>
                                <tr>
                                    <td><span class="method get">GET</span></td>
                                    <td><code>/api/v1/products</code></td>
                                    <td>Lists products accessible to the authenticated user with pagination metadata.</td>
                                </tr>
                                <tr>
                                    <td><span class="method get">GET</span></td>
                                    <td><code>/api/v1/products/{id}</code></td>
                                    <td>Retrieves details of a single product by ID if authorized.</td>
                                </tr>
                                <tr>
                                    <td><span class="method post">POST</span></td>
                                    <td><code>/api/v1/products</code></td>
                                    <td>Creates a new sanitary ware product owned by the authenticated user.</td>
                                </tr>
                                <tr>
                                    <td><span class="method put">PUT</span></td>
                                    <td><code>/api/v1/products/{id}</code></td>
                                    <td>Replaces/updates an existing product after ownership and validation checks.</td>
                                </tr>
                                <tr>
                                    <td><span class="method del">DELETE</span></td>
                                    <td><code>/api/v1/products/{id}</code></td>
                                    <td>Deletes an existing product if owned by the user or requested by admin.</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </body>
                </html>
                """;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/html; charset=utf-8"))
                .body(html);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiEnvelope<List<MockProduct>>> listProducts(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        String currentUser = resolveUser(authHeader);
        if (currentUser == null) {
            return unauthorized();
        }

        if (page < 1 || perPage < 1 || perPage > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiEnvelope.fail("VALIDATION_ERROR", "Invalid pagination parameters.",
                            List.of(new ApiEnvelope.FieldErrorDetail("page/perPage", "page must be >= 1 and perPage between 1 and 100."))));
        }

        List<MockProduct> visible = currentUser.equals("admin")
                ? new ArrayList<>(products)
                : products.stream().filter(p -> p.getOwnerId().equals(currentUser)).toList();

        int total = visible.size();
        int fromIndex = Math.min((page - 1) * perPage, total);
        int toIndex = Math.min(fromIndex + perPage, total);
        List<MockProduct> paged = visible.subList(fromIndex, toIndex);

        return ResponseEntity.ok(ApiEnvelope.okList("Products retrieved successfully.", paged, total, page, perPage));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiEnvelope<MockProduct>> getProduct(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String id
    ) {
        String currentUser = resolveUser(authHeader);
        if (currentUser == null) {
            return unauthorized();
        }

        MockProduct product = findById(id);
        if (product == null) {
            return notFound(id);
        }

        if (!canAccess(currentUser, product)) {
            return forbidden();
        }

        return ResponseEntity.ok(ApiEnvelope.ok("Product retrieved successfully.", product));
    }

    @PostMapping("/products")
    public ResponseEntity<ApiEnvelope<MockProduct>> createProduct(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, Object> rawBody
    ) {
        String currentUser = resolveUser(authHeader);
        if (currentUser == null) {
            return unauthorized();
        }

        List<ApiEnvelope.FieldErrorDetail> errors = validatePayload(rawBody);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiEnvelope.fail("VALIDATION_ERROR", "The request contains invalid or missing fields.", errors));
        }

        MockProductRequest req = objectMapper.convertValue(rawBody, MockProductRequest.class);

        boolean barcodeExists = products.stream()
                .anyMatch(p -> p.getBarcode().equalsIgnoreCase(req.getBarcode().trim()));
        if (barcodeExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiEnvelope.fail("CONFLICT", "A product with barcode '" + req.getBarcode().trim() + "' already exists.", null));
        }

        String now = Instant.now().toString();
        MockProduct created = MockProduct.builder()
                .id("prod_" + idCounter.getAndIncrement())
                .name(req.getName().trim())
                .barcode(req.getBarcode().trim())
                .category(req.getCategory().trim())
                .purchasePrice(req.getPurchasePrice())
                .salePrice(req.getSalePrice())
                .stockQuantity(req.getStockQuantity())
                .ownerId(currentUser)
                .createdAt(now)
                .updatedAt(now)
                .build();

        products.add(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiEnvelope.ok("Product created successfully.", created));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiEnvelope<MockProduct>> updateProduct(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String id,
            @RequestBody(required = false) Map<String, Object> rawBody
    ) {
        String currentUser = resolveUser(authHeader);
        if (currentUser == null) {
            return unauthorized();
        }

        MockProduct existing = findById(id);
        if (existing == null) {
            return notFound(id);
        }

        if (!canAccess(currentUser, existing)) {
            return forbidden();
        }

        List<ApiEnvelope.FieldErrorDetail> errors = validatePayload(rawBody);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiEnvelope.fail("VALIDATION_ERROR", "The request contains invalid or missing fields.", errors));
        }

        MockProductRequest req = objectMapper.convertValue(rawBody, MockProductRequest.class);

        boolean barcodeConflict = products.stream()
                .anyMatch(p -> !p.getId().equals(id) && p.getBarcode().equalsIgnoreCase(req.getBarcode().trim()));
        if (barcodeConflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiEnvelope.fail("CONFLICT", "Another product already uses barcode '" + req.getBarcode().trim() + "'.", null));
        }

        existing.setName(req.getName().trim());
        existing.setBarcode(req.getBarcode().trim());
        existing.setCategory(req.getCategory().trim());
        existing.setPurchasePrice(req.getPurchasePrice());
        existing.setSalePrice(req.getSalePrice());
        existing.setStockQuantity(req.getStockQuantity());
        existing.setUpdatedAt(Instant.now().toString());

        return ResponseEntity.ok(ApiEnvelope.ok("Product updated successfully.", existing));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiEnvelope<Object>> deleteProduct(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String id
    ) {
        String currentUser = resolveUser(authHeader);
        if (currentUser == null) {
            return unauthorized();
        }

        MockProduct existing = findById(id);
        if (existing == null) {
            return notFound(id);
        }

        if (!canAccess(currentUser, existing)) {
            return forbidden();
        }

        products.remove(existing);
        return ResponseEntity.ok(ApiEnvelope.ok("Product deleted successfully.", null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiEnvelope<Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiEnvelope.fail("METHOD_NOT_ALLOWED", "HTTP method " + ex.getMethod() + " is not supported for this endpoint.", null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiEnvelope<Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiEnvelope.fail("VALIDATION_ERROR", "Malformed JSON request body.", null));
    }

    private String resolveUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7).trim();
        return switch (token) {
            case "user-a-token" -> "user-a";
            case "user-b-token" -> "user-b";
            case "admin-token" -> "admin";
            default -> null;
        };
    }

    private boolean canAccess(String currentUser, MockProduct product) {
        return "admin".equals(currentUser) || product.getOwnerId().equals(currentUser);
    }

    private MockProduct findById(String id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    private List<ApiEnvelope.FieldErrorDetail> validatePayload(Map<String, Object> rawBody) {
        List<ApiEnvelope.FieldErrorDetail> details = new ArrayList<>();
        if (rawBody == null || rawBody.isEmpty()) {
            details.add(new ApiEnvelope.FieldErrorDetail("body", "Request body is required and must be valid JSON."));
            return details;
        }

        for (String key : rawBody.keySet()) {
            if (!ALLOWED_FIELDS.contains(key)) {
                details.add(new ApiEnvelope.FieldErrorDetail(key, "Unknown or unsupported field."));
            }
        }

        Object nameObj = rawBody.get("name");
        if (!(nameObj instanceof String nameStr) || nameStr.trim().length() < 3 || nameStr.trim().length() > 100) {
            details.add(new ApiEnvelope.FieldErrorDetail("name", "Name is required and must be between 3 and 100 characters."));
        }

        Object barcodeObj = rawBody.get("barcode");
        if (!(barcodeObj instanceof String barcodeStr) || barcodeStr.trim().length() < 5 || barcodeStr.trim().length() > 30) {
            details.add(new ApiEnvelope.FieldErrorDetail("barcode", "Barcode is required and must be between 5 and 30 characters."));
        }

        Object catObj = rawBody.get("category");
        if (!(catObj instanceof String catStr) || catStr.trim().length() < 2 || catStr.trim().length() > 50) {
            details.add(new ApiEnvelope.FieldErrorDetail("category", "Category is required and must be between 2 and 50 characters."));
        }

        Object buyObj = rawBody.get("purchasePrice");
        if (!(buyObj instanceof Number) || new BigDecimal(buyObj.toString()).compareTo(BigDecimal.ZERO) <= 0) {
            details.add(new ApiEnvelope.FieldErrorDetail("purchasePrice", "Purchase price is required and must be a positive number."));
        }

        Object saleObj = rawBody.get("salePrice");
        if (!(saleObj instanceof Number) || new BigDecimal(saleObj.toString()).compareTo(BigDecimal.ZERO) <= 0) {
            details.add(new ApiEnvelope.FieldErrorDetail("salePrice", "Sale price is required and must be a positive number."));
        }

        Object stockObj = rawBody.get("stockQuantity");
        if (!(stockObj instanceof Integer stockInt) || stockInt < 0) {
            details.add(new ApiEnvelope.FieldErrorDetail("stockQuantity", "Stock quantity is required and must be an integer >= 0."));
        }

        return details;
    }

    private <T> ResponseEntity<ApiEnvelope<T>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiEnvelope.fail("UNAUTHORIZED", "Missing or invalid Bearer token.", null));
    }

    private <T> ResponseEntity<ApiEnvelope<T>> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiEnvelope.fail("FORBIDDEN", "You do not have permission to access or modify this resource.", null));
    }

    private <T> ResponseEntity<ApiEnvelope<T>> notFound(String id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiEnvelope.fail("NOT_FOUND", "Product with ID '" + id + "' was not found.", null));
    }
}