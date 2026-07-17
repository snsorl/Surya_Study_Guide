# Exercise: TaskManager (Collections Challenge)

## Objective
Apply the Java Collections Framework (JCF) to manage dynamic data models using lists for ordered storage, sets to filter duplicate values, and maps for fast key-value lookups.

---

## Prerequisites
- Completed Friday's reading materials on Java Collections Framework interfaces, lists, sets, and maps.

---

## Step-by-Step Instructions

### Step 1: Design the Task Class
1.  In IntelliJ, create a new package named `com.cohort.tasks`.
2.  Create a class named `Task` with fields:
    *   `private String taskId;`
    *   `private String title;`
    *   `private boolean isCompleted;`
3.  Implement getters/setters, a constructor, and override `toString()`.

---

### Step 2: Implement the TaskManager Engine
Create a class named `TaskManager` that maintains the following collection states:
1.  **Ordered List**: `List<Task> allTasks = new ArrayList<>();` to preserve the exact entry sequence of all tasks.
2.  **Unique Titles Set**: `Set<String> uniqueTitles = new HashSet<>();` to verify and block tasks with duplicate names.
3.  **Search Map**: `Map<String, Task> taskLookup = new HashMap<>();` to enable immediate O(1) searches using the unique `taskId`.

Implement the following methods inside `TaskManager`:

#### Add Task
- **Signature**: `public boolean addTask(Task task)`
- **Behavior**:
  - Check if `task.getTitle()` already exists in the `uniqueTitles` set. If it does, print: `"Duplicate task blocked!"` and return `false`.
  - Otherwise, add the task to the `allTasks` list, add the title to the `uniqueTitles` set, and put the mapping entry `(task.taskId -> task)` into the `taskLookup` map. Return `true`.

#### Retrieve Task
- **Signature**: `public Task getTaskById(String id)`
- **Behavior**: Returns the task directly from the `taskLookup` map without traversing list indexes.

#### Iterate Roster
- **Signature**: `public void printRoster()`
- **Behavior**: Loops through the `allTasks` list and prints each element's details.

---

### Step 3: Write Runner Testing
1.  Add a `main` method to `TaskManager`.
2.  Instantiate the manager and create 3 tasks:
    *   Task 1: `("T1", "Write Primitives Lab")`
    *   Task 2: `("T2", "Configure GitIgnore")`
    *   Task 3: `("T3", "Write Primitives Lab")` (Duplicate Title!)
3.  Add all tasks and print the return boolean statuses.
4.  Print the total task list using `printRoster()`. Verify that the duplicate title is absent.
5.  Perform a direct ID lookup for `"T2"`.

---

## Definition of Done
- A compiled, running class named `TaskManager` exists.
- The manager utilizes an `ArrayList`, `HashSet`, and `HashMap`.
- The duplicate task title check successfully filters out Task 3.
- The lookup method resolves the correct `Task` object using only the lookup key map.
