# Exercise: Python Environment Verification and REPL Exploration

## Objectives
- Confirm that Python 3.12+ is installed and configured on your local system path.
- Explore Python variables, data types, and operators interactively using the REPL.
- Write and execute your first standalone Python script verifying arithmetic, identity, and membership operators.

---

## Part 1: Interactive REPL Verification
1. Open your terminal or PowerShell and run the following command:
   ```bash
   python --version
   ```
   *Verify:* The terminal must print `Python 3.12.x` or higher.
   
2. Start the interactive REPL:
   ```bash
   python
   ```
   *Note:* Your command line should change to show the triple-greater-than prompt `>>>`.

3. Introspect types using the `type()` function. Execute the following commands line-by-line and observe the output classes:
   ```python
   type(100)
   type(42.5)
   type("Infosys Training")
   type(True)
   type([1, 2, 3])
   ```

4. Exit the REPL session:
   ```python
   exit()
   ```

---

## Part 2: Coding Task - The Basics Script
1. Create a folder named `exercise-1` inside your local workspace.
2. Inside that folder, create a file named `basics_demo.py`.
3. In this file, write a Python script that completes the following steps:
   - Define a variable representing a transaction total: `total_cents = 5425`.
   - Calculate the dollar amount using floor division (`//`) and the remaining cents using the modulo operator (`%`).
   - Store these in variables: `dollars` and `cents`.
   - Format the results into a string using an **f-string** print statement showing: `Transaction: $54.25`.
   - Define a list containing allowed roles: `allowed = ["admin", "editor"]`.
   - Take a user role variable `current_role = "guest"` and write a conditional statement (`if`/`else`) that checks membership (`in` or `not in`) to print whether the user is authorized.
   - Verify reference identity vs value equality:
     - Declare `list_a = [10, 20]`
     - Declare `list_b = [10, 20]`
     - Run equality (`==`) and identity (`is`) comparisons on them and print the boolean results.

---

## Definition of Done
Your exercise is complete when:
- Running `python basics_demo.py` outputs the transaction dollars/cents conversion, role verification status, and equality/identity comparison booleans.
- The script runs end-to-end without throwing any syntax or indentation errors.
