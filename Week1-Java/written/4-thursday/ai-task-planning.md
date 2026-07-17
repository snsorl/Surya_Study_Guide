# AI Task Planning

## Learning Objectives
- Use AI coding assistants to partition complex programming assignments into modular, sequential subtasks.
- Order tasks logically based on file dependencies (compilation order).
- Perform a critical audit of AI-generated task plans to ensure all requirements are covered.
- Execute feature implementations systematically to prevent compile-time gridlocks.

---

## Why This Matters
When faced with a large programming assignment (such as building a library management system or a customer registry), it is common to feel overwhelmed. 

Beginners often make the mistake of attempting "Big Bang Integration"—they write all the classes, fields, and runners at the same time, click compile, and face a wall of 50 conflicting syntax errors. Finding and fixing bugs in this state is chaotic. 

**AI Task Planning** solves this. You can leverage the LLM to analyze your assignment specifications and break them down into a step-by-step task checklist ordered by file dependencies. By executing this checklist systematically—creating one file at a time, compiling it, and verifying it before moving on—you keep your project in a compilable state at all times, making debugging trivial.

---

## The Concept

### The Task Planning Workflow
Follow this systematic workflow when utilizing AI to plan features:

```
[ Input Specs ] ---> [ AI dependency checklist ] ---> [ Human Audit ] ---> [ Step-by-Step Code ]
                             (Compilation order)        (Check details)          (Compile & verify each)
```

#### Step 1: Input Requirements
Paste the assignment description, class requirements, and styling guidelines directly into the AI.
*   *Prompt*: *"I need to build the following system: [insert requirements]. Break this project down into a step-by-step, file-by-file implementation plan."*

#### Step 2: Establish the Compilation Dependency Checklist
Instruct the AI to order tasks logically by **compilation dependencies**. You must build the base foundation files before the classes that reference them:
1.  Interfaces and Abstract parent classes.
2.  Value objects and helper utility classes.
3.  Concrete subclasses.
4.  Main execution runner classes.

#### Step 3: The Human Audit
LLMs often simplify project requirements to save tokens, skipping edge-case validations. Check the AI's task list against your official assignment documentation. If a step is missing (e.g. checking for negative values), update the list manually.

#### Step 4: Systematic Execution
Create the files in order. **Never write code for Step 3 until Step 1 and Step 2 compile and execute successfully.**

---

## Code Example: Roster System Task Plan
Let's see this workflow applied to a programming task.

### The Assignment Prompt:
> *"Build a student roster system. Define a Student class (name, score). Define a Course class containing an array of Students, a method to add students, and a method to calculate course averages."*

---

### Step 1: The AI-Generated Task Plan (Verified & Audited)
The AI analyzes the dependencies and structures the following plan:

- [ ] **Step 1: Declare the `Student` class.**
  - Needs private variables (`name`, `score`), getters/setters, validation checks for negative scores, and a parameterized constructor.
- [ ] **Step 2: Declare the `Course` class.**
  - Needs private array field `Student[] roster`, tracking variable `count`, addStudent method with array bounds guard check, and a calculateAverage method.
- [ ] **Step 3: Create the execution runner `RosterApp`.**
  - Instantiates `Course`, populates with `Student` objects, and prints status.

---

### Step 2: Systematic Code Execution

#### Step 2.1: Implement `Student.java` (Step 1 of the Plan)
We write and compile the base file first. It has no dependencies:

```java
// File: Student.java
public class Student {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        setScore(score); // Use setter to enforce boundary validation
    }

    public String getName() { return this.name; }
    public double getScore() { return this.score; }

    public void setScore(double score) {
        this.score = (score >= 0.0 && score <= 100.0) ? score : 0.0;
    }
}
```
*Action*: Compile `Student.java`. Verification successful.

---

#### Step 2.2: Implement `Course.java` (Step 2 of the Plan)
We write the class that references `Student`. Since `Student.java` is already verified, compile errors in this step can only belong to `Course` logic:

```java
// File: Course.java
public class Course {
    private String courseName;
    private Student[] roster;
    private int studentCount;

    public Course(String courseName, int capacity) {
        this.courseName = courseName;
        this.roster = new Student[capacity];
        this.studentCount = 0;
    }

    public void addStudent(Student student) {
        // Guard check: prevent array bounds crash
        if (studentCount < roster.length) {
            roster[studentCount] = student;
            studentCount++;
            System.out.println("[COURSE] Added " + student.getName() + " to " + courseName);
        } else {
            System.out.println("Error: Course roster is full!");
        }
    }

    public double calculateAverage() {
        if (studentCount == 0) return 0.0;
        double sum = 0.0;
        for (int i = 0; i < studentCount; i++) {
            sum += roster[i].getScore();
        }
        return sum / studentCount;
    }
}
```
*Action*: Compile `Course.java` alongside `Student.java`. Verification successful.

---

## Summary
- **AI Task Planning** avoids "Big Bang Integration" compilation errors by breaking assignments into sequential files.
- Organize your implementation checklist by **compilation dependencies** (parent classes/interfaces first, runners last).
- **Audit** all AI-generated checklists to verify no business logic or boundary conditions were skipped.
- Write, compile, and verify **one step at a time**.

---

## Additional Resources
- [Incremental Software Development Guide - Agile Alliance](https://www.agilealliance.org/glossary/incremental-development/)
- [Java Compilation Dependencies - JetBrains Help](https://www.jetbrains.com/)
- [Project Management for Developers - GeeksforGeeks](https://www.geeksforgeeks.org/)