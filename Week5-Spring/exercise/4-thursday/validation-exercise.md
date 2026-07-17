# Exercise: Input Validation & Global Error Handling

## Objective
Implement bean-level constraints using Java Validation API (JSR-380) annotations. You will secure REST endpoints by validating request payloads, intercepting validation failures, and implementing a centralized Global Exception Handler to return clean, client-friendly error structures with HTTP `400 Bad Request` statuses.

---

## Scenario
If API endpoints do not validate input structures, bad data enters the database, leading to database schema errors or functional bugs. Furthermore, returning raw Java stack traces to clients is a major security risk (OWASP Information Disclosure). You will set up automatic validation checks on incoming product data and write a global controller advice handler to structure the errors cleanly.

---

## References
- [Bean Validation (JSR-380) in Spring](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/validation.md)
- [JPA Annotations](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/annotations.md)

---

## Core Tasks

### 1. Add Validation Starter
- Open your `pom.xml`.
- Ensure the Spring Boot validation starter is declared:
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  ```

### 2. Configure Constraints on the Product Class
- Open or create the `Product` entity/DTO class.
- Decorate fields with the following validation rules:
  - `name`: Annotated with `@NotBlank(message = "Product name cannot be empty")` and `@Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")`.
  - `price`: Annotated with `@NotNull(message = "Price is required")` and `@Min(value = 0, message = "Price must be greater than or equal to 0")`.
  - `sku`: Annotated with `@Pattern(regexp = "^PROD-[A-Z]{3}-[0-9]{4}$", message = "SKU must match the pattern 'PROD-XXX-0000'")`.

### 3. Apply Validation Check to REST Endpoints
- Open `ProductController`.
- In the POST `/products` route method (handling create requests), add `@Valid` immediately before the `@RequestBody` parameter:
  ```java
  @PostMapping
  public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
      Product saved = productService.save(product);
      return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }
  ```

### 4. Create a Centralized Exception Handler
- Create a class named `GlobalExceptionHandler` annotated with `@RestControllerAdvice`.
- Implement a method to handle `MethodArgumentNotValidException`:
  ```java
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.validation.FieldError;
  import org.springframework.web.bind.MethodArgumentNotValidException;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  import org.springframework.web.bind.annotation.RestControllerAdvice;
  import java.util.HashMap;
  import java.util.Map;

  @RestControllerAdvice
  public class GlobalExceptionHandler {

      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<Map<String, String>> handleValidationExceptions(
              MethodArgumentNotValidException ex) {
          Map<String, String> errors = new HashMap<>();
          ex.getBindingResult().getAllErrors().forEach((error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
          });
          return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
      }
  }
  ```

### 5. Verify Validation Behavior
- Boot the application.
- Send a POST request to `/products` with an invalid payload (e.g. price = -10, name = "", sku = "BAD-SKU"):
  ```json
  {
    "name": "",
    "price": -10.00,
    "sku": "BAD-SKU"
  }
  ```
- Confirm the server rejects the request and returns HTTP Status `400 Bad Request` with the field-level error messages mapped in JSON.

---

## Definition of Done
- Invalid HTTP payloads yield HTTP status code `400 Bad Request`.
- The returned error body maps field names directly to validation error messages:
  ```json
  {
    "name": "Product name cannot be empty",
    "price": "Price must be greater than or equal to 0",
    "sku": "SKU must match the pattern 'PROD-XXX-0000'"
  }
  ```
- No server-side stack trace leaks to the client console or response body.
