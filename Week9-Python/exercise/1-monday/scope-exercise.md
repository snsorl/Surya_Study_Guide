# Exercise: Scopes and the LEGB Lookup Rule

## Objectives
- Apply the LEGB scope resolution rule to trace variable lookups.
- Implement modifications to variables in enclosing and global scopes using the `nonlocal` and `global` keywords.
- Prevent variable shadowing and namespace collision defects.

---

## The Scenario
You are developing a modular calculation engine. You need to keep track of a global calculation count, update intermediate values inside a nested process (closure), and print values verifying which scope is active at each stage.

---

## Instructions

1. Create a file named `scope_exercise.py`.
2. Define a global variable: `calculation_count = 0`.
3. Write a function named `run_calculator()` that does the following:
   - Define a local variable inside `run_calculator` named `accumulator = 10`. (This represents the **Enclosing** scope for nested functions).
   
   - Define a nested function named `add_values(a, b)`:
     - Inside `add_values`, calculate the sum of `a` and `b`.
     - Update the global `calculation_count` to increment it by 1. (Use the **`global`** keyword).
     - Update the `accumulator` inside `run_calculator` by adding the sum to it. (Use the **`nonlocal`** keyword).
     - Return the calculated sum.
     
   - Call `add_values(5, 7)` and print the returned sum.
   - Print the value of `accumulator` from inside `run_calculator()` to verify it was successfully updated to `22`.
   
4. Execute `run_calculator()`.
5. Print the global `calculation_count` outside the function scope to verify it was successfully updated to `1`.

---

## Code Trace Challenge (Comments Requirement)
Add comments above each variable print statement in your script explaining which layer of the **LEGB** rule (Local, Enclosing, Global, Built-in) Python resolves when fetching the variable value.

---

## Definition of Done
Your exercise is complete when:
- Running `python scope_exercise.py` executes successfully.
- The output displays:
  - The sum of 5 and 7 (12).
  - The modified accumulator value (22).
  - The updated global calculation count (1).
- Your code contains comments explicitly labeling the LEGB scope level accessed during execution.
