# Bean Validation (JSR-380) in Spring Boot

## Learning Objectives
- Apply standard JSR-380 validation annotations to Java classes (e.g. `@NotNull`, `@NotBlank`, `@Size`, `@Min`, `@Email`).
- Trigger payload validation in Spring MVC controllers using the `@Valid` annotation.
- Handle validation failures using `BindingResult` and throw appropriate HTTP status codes (e.g., `400 Bad Request`).
- Implement a custom validation annotation and companion validator class.

---

## Why This Matters
If your API endpoints accept user input without validation, your application will quickly crash due to database errors or logical issues (such as negative purchase prices or malformed emails). Additionally, checking for validation errors manually with long lists of `if-else` blocks clutter your controllers and services. Spring Boot supports **Bean Validation (JSR-380)**, allowing you to declare validation rules directly on your data transfer classes using simple annotations. This keeps validation logic declarative and centralized, ensuring your API only processes clean, structured data.

---

## The Concept

### What is JSR-380?
**JSR-380** (also known as Bean Validation 2.0) is the Java standard for declaring validation constraints on Java beans. The default reference implementation used by Spring Boot is **Hibernate Validator**.

#### Core Validation Annotations:
-   **`@NotNull`**: Ensures the field is not null (accepts empty strings/spaces).
-   **`@NotEmpty`**: Ensures the field is not null and has a length greater than 0.
-   **`@NotBlank`**: Ensures the field is not null, not empty, and contains at least one non-whitespace character. (Best for string fields).
-   **`@Size(min=X, max=Y)`**: Restricts string lengths or collection sizes.
-   **`@Min(Value)` / `@Max(Value)`**: Sets bounds on numeric fields.
-   **`@Email`**: Verifies the string matches standard email formatting rules.

---

### Validating Requests in Controllers: `@Valid`
To validate JSON request payloads, place the **`@Valid`** (or `@Validated`) annotation on the `@RequestBody` parameter inside your controller method.

```java
@PostMapping
public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
    return ResponseEntity.ok("User registered.");
}
```

#### Under the Hood: The Validation Lifecycle
1.  **JSON Deserialization**: Jackson converts the incoming JSON string into a `UserDto` object.
2.  **Constraint Check**: Spring's validation engine parses the `@Valid` annotation and scans the DTO properties for constraint violations.
3.  **Action on Failure**:
    -   If an error is found, Spring throws a `MethodArgumentNotValidException`, which maps to an HTTP **`400 Bad Request`** status code automatically.
    -   Alternatively, you can intercept validation details programmatically by adding a **`BindingResult`** parameter immediately after the validated DTO parameter.

---

## Code Example: Validation Flow & Custom Validators

Here is a complete validation flow, including a custom annotation to check phone numbers.

### Step 1: Create a Custom Annotation (`validation/ContactNumber.java`)
```java
package com.example.validation;

import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = ContactNumberValidator.class) // Links to validator class
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumber {
    String message() default "Invalid contact phone number format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### Step 2: Implement the Validator Class (`validation/ContactNumberValidator.java`)
```java
package com.example.validation;

import jakarta.validation.*;

public class ContactNumberValidator implements ConstraintValidator<ContactNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        // Regex: Checks if phone number is exactly 10 digits
        return value.matches("\\d{10}");
    }
}
```

### Step 3: Decorate the DTO Class (`dto/CustomerDto.java`)
```java
package com.example.dto;

import com.example.validation.ContactNumber;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @Email(message = "Email must be a valid format.")
    private String email;

    @Min(value = 18, message = "Customer must be at least 18 years old.")
    private int age;

    @ContactNumber // Applies our custom validation rule
    private String phoneNumber;
}
```

### Step 4: Validate in REST Controller (`controller/CustomerController.java`)
```java
package com.example.controller;

import com.example.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerDto dto, BindingResult bindingResult) {
        // Intercept validation results programmatically
        if (bindingResult.hasErrors()) {
            // Extract the first error message and return a 400 response
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body("Validation error: " + errorMsg);
        }

        // Proceed to service layer
        return ResponseEntity.ok("Customer registered successfully.");
    }
}
```

---

## Summary
-   **JSR-380** (Bean Validation) uses declarative annotations like `@NotBlank` and `@Email` to define constraint boundaries.
-   Place **`@Valid`** on controller parameters to trigger request payload validation.
-   Validation failures throw `MethodArgumentNotValidException`, translating to HTTP **`400 Bad Request`**.
-   Implement the **`ConstraintValidator<Annotation, Type>`** interface to build custom, complex validation rules.

---

## Additional Resources
-   [Spring Boot Documentation: Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.validation)
-   [Baeldung: Validation in Spring Boot](https://www.baeldung.com/spring-boot-bean-validation)
-   [Hibernate Validator Reference Guide](https://hibernate.org/validator/documentation/)
