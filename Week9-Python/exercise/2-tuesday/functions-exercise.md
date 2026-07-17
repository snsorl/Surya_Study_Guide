# Exercise: Functional Programming Data Pipeline

## Objectives
- Author functions using variable length arguments (`*args`).
- Write inline anonymous **lambda** functions.
- Combine higher-order functional utilities (`map()`, `filter()`, `sorted()`) to process data without loops.

---

## The Scenario
You are developing a HR automation task. You have a series of employee tuple records containing `(name, department, salary)`. You need to write a utility pipeline that filters out low-earning records, calculates a 10% bonus for high performers, and returns the list sorted by the final salary value.

---

## Instructions

1. Create a script named `employee_pipeline.py`.
2. Write a function named `process_bonuses(salary_threshold, *employee_records)`:
   - The parameter `*employee_records` accepts a variable list of tuples. E.g., `("Alice", "HR", 50000.0)`.
   
   - **Step 1: Filter High Earners:** Use the built-in **`filter()`** function with a lambda expression to isolate employee records where the `salary` (index 2 of the tuple) is **greater than or equal to** `salary_threshold`.
   
   - **Step 2: Calculate Bonuses:** Use the built-in **`map()`** function with a lambda expression to increase the salary of the filtered employees by **10%**. The returned maps should return a new tuple format: `(name, new_salary)`.
     - *Hint:* `lambda emp: (emp[0], emp[2] * 1.10)`
     
   - **Step 3: Sort by Salary:** Use the **`sorted()`** function to sort the mapped tuples by `new_salary` (index 1 of the new tuple) in **descending order**. Pass a lambda as the `key` parameter to specify sorting by salary.
   
   - Return the sorted list of tuples.

3. Test your function by calling it with a threshold of `60000.0` and passing the following sample tuples:
   - `("Alice", "Dev", 75000.0)`
   - `("Bob", "QA", 50000.0)`
   - `("Charlie", "Dev", 90000.0)`
   - `("David", "Ops", 62000.0)`

---

## Definition of Done
Your lab is complete when:
- Running `python employee_pipeline.py` executes successfully.
- The output prints the following sorted list of high performers with their updated bonus salaries:
  ```text
  [('Charlie', 99000.0), ('Alice', 82500.0), ('David', 68200.0)]
  ```
- Your implementation uses **zero traditional for/while loops** to filter, map, or sort the records.
