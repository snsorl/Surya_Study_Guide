# Prompt Maintenance: Spring Data JPA Queries

## Learning Objectives
- Review and refine Spring-related prompts collected throughout the week.
- Construct standardized prompt templates for generating JPA entities and repository query methods.
- Evaluate AI performance in generating complex database relationships.
- Establish prompt maintenance workflows to prevent codebase deviation.

---

## Why This Matters
As you work with AI coding assistants on database projects, you will notice that the AI occasionally suggests outdated relationship patterns (like using `@ManyToMany` without mapping join columns) or writes query method names that violate Spring Data naming conventions. These errors slow down development. By maintaining a registry of **Standardized Prompt Templates**, you can steer AI assistants to generate clean, highly compliant database entities, repositories, and transactional services.

---

## The Concept

### The Need for Prompt Maintenance
Just as software code requires maintenance (refactoring, upgrading dependencies, cleaning up dead structures), your **AI Prompt Library** must also be maintained. 

Prompt maintenance involves:
-   **Reviewing Failures**: Analyzing why the AI produced compilation errors or database mapping bugs.
-   **Updating Templates**: Adjusting prompts to enforce new team conventions or library version upgrades.
-   **Locking Constraints**: Hardcoding rules into prompt headers to ensure security compliance.

---

## Standardized Prompt Templates

Use the templates below to generate Spring Data JPA components.

---

### Template 1: JPA Entity Generation

#### Context & Setup:
```text
System Context: Spring Boot 3.2.x, Java 17, PostgreSQL.
All database mapping classes must use jakarta.persistence.* imports.
```

#### The Prompt Template:
> "Generate a JPA Entity class named [EntityName] mapping to table '[TableName]'.
> Fields:
> - [PrimaryKeyField] (Configure as GenerationType.IDENTITY)
> - [Field1] (Type, Nullability, unique limits)
> - [Field2] (Type, Nullability)
> 
> **Relationship Mapping:**
> - The entity has a [RelationshipType: e.g., ManyToOne] relationship with [TargetEntity].
> - Define the foreign key column as '[ForeignKeyColumnName]' and set it to [Lazy/Eager] fetch loading.
> 
> **Constraints:**
> - Use Lombok `@Getter`, `@Setter`, `@NoArgsConstructor`, and `@AllArgsConstructor` annotations.
> - Do NOT use Lombok `@Data` annotation (due to JPA performance concerns with hashCode and toString).
> - Write only the Java class, no explanations."

---

### Template 2: Spring Data JpaRepository Query Methods

#### Context & Setup:
```text
System Context: Spring Data JPA.
The interface must extend JpaRepository.
```

#### The Prompt Template:
> "Based on the JPA Entity '[EntityName]' containing fields [[FieldList]]:
> Generate a JpaRepository interface named '[EntityName]Repository'.
> **Tasks:**
> - Add a derived query method to find records by [Field1] and [Field2].
> - Add a custom JPQL query using `@Query` to fetch records where [ComplexCondition].
> - Add a native PostgreSQL query to [NativeAction: e.g., query JSON column].
> 
> **Constraints:**
> - Do not write implementation classes.
> - Write only the interface code and imports."

---

## Prompt Evaluation Example: Many-to-One Mapping

#### Input Prompt:
> "Generate a Many-to-One association in the `Order` entity referencing the `Customer` class. Enforce lazy loading and set the join column name to `client_id`."

#### Initial AI Attempt (Buggy):
```java
// AI generated this field
@ManyToOne
private Customer customer;
```
*The Error:* The AI forgot the `@JoinColumn` configuration (so Hibernate default-mapped it to `customer_id` instead of `client_id`) and omitted lazy loading (defaulting to Eager fetch, which hurts performance).

#### Refined Maintenance Prompt (Corrected Template):
We update our prompt template to explicitly demand join column definitions and lazy fetching:
> "When mapping database relationships, you MUST explicitly specify the `@JoinColumn(name = "column_name")` attribute and declare `fetch = FetchType.LAZY` on all `@ManyToOne` configurations."

---

## Summary
-   **Prompt Maintenance** ensures AI-generated database code remains consistent and high-quality.
-   Use **structured templates** for JPA entities to enforce naming, PK strategies, and Lombok safety rules.
-   Explicitly demand **`FetchType.LAZY`** and **`@JoinColumn`** declarations inside prompt templates to avoid database fetch performance loops.

---

## Additional Resources
-   [Google Cloud: Prompt Engineering Best Practices](https://cloud.google.com/blog/products/application-development/prompt-engineering-for-developers)
-   [Baeldung: Guide to Spring Data JPA Query Creation](https://www.baeldung.com/spring-data-derived-queries)
-   [Hibernate Performance Tuning: Avoid Eager Fetching](https://www.baeldung.com/hibernate-performance-tuning)
