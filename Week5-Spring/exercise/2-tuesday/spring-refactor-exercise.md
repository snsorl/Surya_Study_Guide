# Exercise: Refactoring Legacy JDBC to Spring Annotation-Config

## Objective
Refactor a traditional, manually-wired JDBC database application (similar to the Library DAO from Week 2) to use Spring Core stereotype annotations (`@Repository`, `@Service`, `@Autowired`) and automatic Component Scanning. You will remove manual `new` instantiation statements and verify the refactored code using existing JUnit test cases.

---

## Scenario
You are inherited a library management application where components are manually instantiated. The class `LibraryController` directly instantiates `LibraryService`, which in turn instantiates a raw JDBC `BookRepository`. This tight coupling prevents mock testing and violates dependency inversion. You will refactor this structure into a Spring-managed ecosystem while keeping the core JDBC database code intact.

---

## References
- [Spring ORM Module](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/2-tuesday/spring-orm.md)
- [Guided Coding: Spring Beans](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/2-tuesday/guided-coding-spring-beans.md)
- [Stereotype Annotations](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/stereotype-annotations.md)
- [Component Scanning](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/component-scanning.md)

---

## Core Tasks

### 1. Refactor the Repository Layer
- Locate the repository class `BookRepositoryImpl` (or create a basic database handler that uses JDBC connection configurations).
- Annotate this class with `@Repository` to mark it as a Spring DAO component.

### 2. Refactor the Service Layer
- Open the service class `LibraryService`.
- Annotate the service class with `@Service`.
- Remove any direct instantiation code of the repository (e.g., `this.bookRepository = new BookRepositoryImpl();`).
- Declare a final repository dependency and implement a constructor annotated with `@Autowired` for constructor injection:
  ```java
  @Service
  public class LibraryService {
      private final BookRepository bookRepository;

      @Autowired
      public LibraryService(BookRepository bookRepository) {
          this.bookRepository = bookRepository;
      }
      // service methods...
  }
  ```

### 3. Configure Component Scanning
- Create a Java configuration class named `AppConfig` annotated with `@Configuration`.
- Add the `@ComponentScan` annotation and configure the base package scan targeting your project's root package (e.g. `@ComponentScan(basePackages = "com.library")`).

### 4. Refactor the Application Entry Point & Tests
- Update the main application runner to bootstrap the context via `AnnotationConfigApplicationContext(AppConfig.class)`.
- Update your JUnit test runner. Instead of manually instantiating classes in the `@BeforeEach` setup methods:
  - Bootstrap the Spring container inside your test setup.
  - Retrieve the `LibraryService` bean from the container context.
  - Run the tests (creating, reading, and listing books) to verify they pass successfully without database regressions.

---

## Definition of Done
- No occurrences of `new BookRepositoryImpl()` or `new LibraryService()` remain in the project code (excluding test mock contexts if applicable).
- The application context boots and scans all stereotypes correctly.
- All unit and integration database tests execute successfully and pass.
