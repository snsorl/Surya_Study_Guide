# Exercise: Collaborative Pair Programming (Library System)

## Objective
Collaborate in pairs to build a fully encapsulated, polymorphic library system utilizing SCM branch merges, driver-navigator roles, and structured AI prompt engineering logs.

---

## Collaboration Protocol (Thursday Protocol)
- **Format**: Pair Programming (Groups of 2).
- **Roles**:
  *   **Driver**: Writes the code in the IDE, handles compiler errors, commits code.
  *   **Navigator**: Reviews code structure, suggests logic fixes, monitors AI rulesets, and manages the prompt logs.
- **Rotation Timer**: Alternate roles every **15 minutes**.
- **Deliverable**: A shared Git repository containing a clean commit history and a `prompt-log-library.md` record.

---

## Technical Specifications
Design a library inventory coordinator containing the following elements:

### 1. The `Borrowable` Interface
- Methods:
  - `void borrowItem();` - Sets status of item to borrowed.
  - `void returnItem();` - Sets status of item to available.

### 2. The `Book` Base Class
- Fields:
  - `private String title;` (Encapsulated)
  - `private String author;` (Encapsulated)
  - `protected boolean isBorrowed;` (Defaults to false)
- Constructor:
  - Parameterized constructor initializing title and author.
- Methods:
  - Standard getters/setters.
  - Implement custom `toString()` representing the book’s details (e.g. `[BOOK] Title: X | Author: Y`).

### 3. The `EBook` Subclass
- Extends `Book` and implements `Borrowable`.
- Fields:
  - `private double fileSizeMB;`
- Constructor:
  - Parameterized constructor chaining title/author via `super(title, author)`.
- Methods:
  - Overridden `toString()` appending file size information.
  - Overridden implementation of `borrowItem()` and `returnItem()`, printing status change logs.

---

## Step-by-Step Instructions

### Step 1: Design and Split Tasks
1.  Discuss the class design with your partner. Draw a whiteboard sequence diagram of the borrow/return lifecycle.
2.  Partner A initializes a local Git repository, creates a `.gitignore`, commits, and pushes it to a shared GitHub repository. Partner B clones it.

---

### Step 2: Live Code - Round 1 (Partner A Driving, Partner B Navigating)
1.  **Task**: Implement the `Borrowable` interface and `Book` base class with getters, setters, and `toString()` overrides.
2.  **AI Usage**: Use the AI to generate JavaBean boilerplate code. Partner B (Navigator) audits the AI output to verify no naming typos exist.
3.  **Rotation**: Commit the files to Git and push. Switch seats.

---

### Step 3: Live Code - Round 2 (Partner B Driving, Partner A Navigating)
1.  **Task**: Implement the `EBook` subclass, constructor chaining, and overridden borrow/return methods.
2.  **AI Usage**: Prompt the AI to write a method inside `EBook` that simulates a file download before borrowing:
    *   *AI Prompt Constraint*: *"Write a method downloadBook that simulates a 2-second delay using Thread.sleep. Constrain the input size to be positive. Use standard try-catch blocks for interruption."*
3.  **Audit**: Partner A audits the download code to verify the thread sleep is handled safely without compiling warnings.
4.  **Rotation**: Commit, push, switch.

---

### Step 4: Run the Library Manager
1.  Create `LibraryManager` runner class containing a `Book[]` array with EBooks.
2.  Trigger polymorphic borrow/return loops.
3.  Verify that all AI interactions (prompts, critiques, revisions) are logged inside a shared `prompt-log-library.md` file.

---

## Definition of Done
- Both partners have active commit entries in the repository log.
- All classes are fully encapsulated (private variables, public accessors).
- The `EBook` subclass overrides `toString()` and implements `Borrowable`.
- Polymorphic arrays iterate and resolve overridden methods dynamically.
- A complete `prompt-log-library.md` journal is committed inside the repository root.
