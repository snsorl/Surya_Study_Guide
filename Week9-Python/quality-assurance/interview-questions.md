# Technical Interview Question Bank: Week 9 - Python

This document contains 75 standard technical interview questions categorized by daily topic areas, mapped to the **70-25-5 Difficulty Rule** (70% Beginner, 25% Intermediate, 5% Advanced). Use this resource for self-quizzing and interview preparation.

---

## Monday: Python Fundamentals (Execution, Types, Scopes, Operators)

### Beginner (Recall/Definition)

#### Q1: What is the primary difference between a compiled language like Java and an interpreted language like Python?
**Keywords:** Compilation, JVM, Bytecode, CPython Virtual Machine, Line-by-line
<details>
<summary>Click to Reveal Answer</summary>

Java is compiled ahead-of-time (using `javac`) into platform-independent bytecode (`.class` files) which is executed by the JVM. Python is compiled transparently at runtime by the CPython interpreter into bytecode and executed line-by-line via the CPython Virtual Machine, requiring no explicit compiler step.
</details>

---

#### Q2: What is the purpose of Python's REPL?
**Keywords:** Read-Eval-Print-Loop, Interactive, Prototyping, Console
<details>
<summary>Click to Reveal Answer</summary>

The REPL (Read-Eval-Print-Loop) is an interactive shell environment that reads user commands, evaluates them in the active namespace, prints the output results, and loops back to wait for the next command. It is ideal for rapid prototyping and exploring syntax.
</details>

---

#### Q3: Explain Python's dynamic typing.
**Keywords:** Dynamic Typing, Labels, Run-time binding, Object Type
<details>
<summary>Click to Reveal Answer</summary>

In Python, variables are merely name labels pointing to objects in memory. The objects themselves possess types, but the variable name does not. Type checking is deferred to runtime, allowing variables to be bound to different data types during execution.
</details>

---

#### Q4: What is the difference between standard division (`/`) and floor division (`//`) in Python?
**Keywords:** Float Division, Floor Division, Rounding Down, Decimal
<details>
<summary>Click to Reveal Answer</summary>

Standard division (`/`) always returns a floating-point number (e.g., `4 / 2` is `2.0`). Floor division (`//`) divides the operands and truncates the result to the next lowest integer, returning an integer if both inputs are integers (e.g., `5 // 2` is `2`).
</details>

---

#### Q5: What is the difference between `==` and `is` in Python?
**Keywords:** Value Equality, Identity, Memory Address, reference
<details>
<summary>Click to Reveal Answer</summary>

The `==` operator compares the *values* of two objects (value equality). The `is` operator compares the *identity* of two objects, checking if both variables point to the exact same memory address (equivalent to checking `id(a) == id(b)`).
</details>

---

#### Q6: What does the LEGB rule stand for?
**Keywords:** Local, Enclosing, Global, Built-in, Search hierarchy
<details>
<summary>Click to Reveal Answer</summary>

The LEGB rule defines Python's scope lookup resolution hierarchy:
1. **Local:** Inside the current function.
2. **Enclosing:** Inside any enclosing nested functions.
3. **Global:** Top-level module variables.
4. **Built-in:** Preassigned names (like `len` or `print`).
</details>

---

#### Q7: How does Python's `isinstance()` function differ from using `type()` for type comparisons?
**Keywords:** Polymorphism, Subclass, Exact match, Inheritance
<details>
<summary>Click to Reveal Answer</summary>

`isinstance()` checks if an object is an instance of a class or any of its subclasses, supporting OOP polymorphism. `type()` returns the exact type of the object, ignoring inheritance hierarchies.
</details>

---

#### Q8: What are PEP 484 Type Annotations?
**Keywords:** Type hints, Static Analysis, Linting, MyPy, Documentation
<details>
<summary>Click to Reveal Answer</summary>

PEP 484 Type Annotations are type hints added to function signatures and variables (e.g. `def add(x: int) -> int`). Python does not enforce these types at runtime; they are used by static analysis tools (like MyPy) and IDEs to flag type errors.
</details>

---

#### Q9: What happens when you attempt to use a Python keyword as a variable identifier?
**Keywords:** SyntaxError, Reserved, Parse failure
<details>
<summary>Click to Reveal Answer</summary>

Attempting to assign a value to a reserved Python keyword (such as `def`, `class`, or `if`) results in a compilation `SyntaxError` because keywords are part of the language grammar.
</details>

---

#### Q10: What is the purpose of `.pyc` files and where are they stored?
**Keywords:** Bytecode, Caching, `__pycache__`, Import speed
<details>
<summary>Click to Reveal Answer</summary>

`.pyc` files are compiled bytecode cache files generated when a module is imported. They are stored inside a folder named `__pycache__` and loaded on future runs to bypass compiling unchanged code, improving script startup speeds.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: You try to modify a global variable from inside a function, but the modification does not persist outside. Why does this happen, and how do you fix it?
**Hint:** Think about local binding.
<details>
<summary>Click to Reveal Answer</summary>

By default, when you assign a value to a variable inside a function, Python assumes you want to create a new local variable, shadowing the global one. To modify the module-level global variable, declare it using the `global` keyword at the start of the function.
</details>

---

#### Q12: How do you modify a variable defined in the outer scope of a nested function?
**Keywords:** Enclosing, `nonlocal`, Closure
<details>
<summary>Click to Reveal Answer</summary>

To modify a variable inside an outer (enclosing) function from within a nested function, declare the variable using the `nonlocal` keyword inside the inner function. This tells Python to resolve the variable in the closest enclosing scope rather than creating a local one.
</details>

---

#### Q13: What does this code snippet print and why?
```python
for i in range(3):
    pass
print(i)
```
**Keywords:** Loop scope, leakage, block boundary
<details>
<summary>Click to Reveal Answer</summary>

It prints `2`. In Python, standard loop structures (like `for` and `while` loops) and conditionals do not create their own scope. The loop counter variable `i` leaks out and remains accessible within the enclosing function/module scope after loop completion.
</details>

---

#### Q14: Explain the difference between Python's division operators using a negative floor division example: `-5 // 2`.
**Keywords:** Floor Division, Rounding Down, Modulo, Fraction
<details>
<summary>Click to Reveal Answer</summary>

Float division `-5 / 2` returns `-2.5`. Floor division `-5 // 2` rounds *down* to the nearest integer, which is `-3`. Python rounds toward negative infinity during floor division, which differs from C or Java where truncation rounds toward zero.
</details>

---

### Advanced (Deep Dive)

#### Q15: Explain how JIT compilation in PyPy differs from standard CPython execution, and why JIT can achieve significant execution speedups.
**Keywords:** Hot loops, Machine code, Translation, Profiling, Interpreter loop bypass
<details>
<summary>Click to Reveal Answer</summary>

Standard CPython executes bytecode line-by-line through a loop in the interpreter, creating overhead for CPU-bound tasks. PyPy utilizes a Just-In-Time (JIT) compiler to profile the running program. When it identifies "hot code paths" (such as loops run frequently), it compiles that bytecode directly into native machine code, bypassing interpreter execution on subsequent runs.
</details>

---

## Tuesday: Strings, Tuples, and Functions (Formatting, Sequences, Arguments, Functional)

### Beginner (Recall/Definition)

#### Q1: What is the purpose of triple quotes (`"""` or `'''`) in Python string definitions?
**Keywords:** Multi-line, Docstrings, Whitespace preservation
<details>
<summary>Click to Reveal Answer</summary>

Triple quotes are used to define multi-line strings. They preserve newlines and whitespaces exactly as typed and are also used to write docstrings (documentation headers) for modules, classes, and functions.
</details>

---

#### Q2: What are f-strings (PEP 498) and why are they preferred over the `.format()` method?
**Keywords:** Formatted string literals, Interpolation, Expressions, Performance
<details>
<summary>Click to Reveal Answer</summary>

F-strings are formatted string literals prefixed with `f` (e.g. `f"{name}"`). They are preferred because they allow inline evaluation of Python expressions directly inside curly braces and are faster since they are evaluated at bytecode compilation time rather than running function calls.
</details>

---

#### Q3: What does the term "string immutability" mean in Python?
**Keywords:** Read-only, Memory allocation, New object, Modification block
<details>
<summary>Click to Reveal Answer</summary>

String immutability means that once a string object is created in memory, its characters cannot be modified in-place. Any operation that appears to modify a string (like concatenation or calling `.upper()`) actually allocates and returns a completely new string object.
</details>

---

#### Q4: Why is it efficient to use `"".join()` rather than looping with `+` for string concatenation?
**Keywords:** Memory allocation, Immutable overhead, Intermediate objects
<details>
<summary>Click to Reveal Answer</summary>

Since strings are immutable, looping and concatenating with `+` constantly creates and discards intermediate string objects in memory, resulting in O(N^2) complexity. `"".join()` measures the total size of all strings in the collection first and allocates memory for the final string in a single operation.
</details>

---

#### Q5: What is a Namespace in Python?
**Keywords:** Name-to-object mapping, Dictionary backend, Scope binding
<details>
<summary>Click to Reveal Answer</summary>

A namespace is an internal mapping that associates identifiers (variable and function names) to their respective objects in memory. Python implements namespaces as standard dictionaries (`dict`) under the hood.
</details>

---

#### Q6: Explain the difference between Python lists and tuples.
**Keywords:** Mutability, Syntax, Performance, Heterogeneous vs Homogeneous
<details>
<summary>Click to Reveal Answer</summary>

Lists are mutable, ordered sequences declared with square brackets `[]`. Tuples are immutable, ordered sequences declared with parentheses `()`. Tuples are faster, occupy less memory, and are typically used for heterogeneous records, while lists are used for homogeneous, dynamic collections.
</details>

---

#### Q7: What does the `collections.namedtuple` class do?
**Keywords:** Subclass, Dot notation, Readable records, Immutable indices
<details>
<summary>Click to Reveal Answer</summary>

`namedtuple` returns a subclass of tuple where elements can be accessed both by index and by descriptive attribute names using dot notation (e.g., `point.x`), improving code readability while maintaining tuple immutability.
</details>

---

#### Q8: What are Lambda functions in Python?
**Keywords:** Anonymous, Inline expression, One-line limit
<details>
<summary>Click to Reveal Answer</summary>

Lambda functions are small, anonymous, one-line functions defined using the syntax `lambda arguments: expression`. They are restricted to evaluating a single expression and implicitly return the result.
</details>

---

#### Q9: What are first-class functions?
**Keywords:** Assign to variables, Arguments, Returns, First-class citizen
<details>
<summary>Click to Reveal Answer</summary>

A programming language has first-class functions if it treats functions as first-class citizens. In Python, functions can be assigned to variables, passed as arguments to other functions, and returned from functions just like any other object.
</details>

---

#### Q10: What do the functions `map()` and `filter()` do?
**Keywords:** Higher-order, Transformation, Condition filtering, Iterable
<details>
<summary>Click to Reveal Answer</summary>

`map(func, iterable)` applies a function to every item in the iterable, returning a transformed sequence. `filter(func, iterable)` tests each element in the iterable with a boolean function, retaining only elements that evaluate to True.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how to restrict a Python function so that specific parameters can *only* be passed positionally.
**Keywords:** Positional-only, Slash parameter `/`, Enforce syntax
<details>
<summary>Click to Reveal Answer</summary>

Use the slash parameter `/` in the function signature. Any parameters declared *before* the `/` must be passed strictly as positional arguments. Any attempt to pass them as keyword arguments (e.g., `name="Alice"`) will raise a `TypeError`.
</details>

---

#### Q12: Explain how to enforce that parameters inside a function can *only* be passed as keyword arguments.
**Keywords:** Keyword-only, Asterisk parameter `*`, Named arguments
<details>
<summary>Click to Reveal Answer</summary>

Place an asterisk `*` (or a variable-length parameter like `*args`) in the function signature. All parameters declared *after* the `*` must be passed strictly as keyword arguments. Passing them positionally will raise a `TypeError`.
</details>

---

#### Q13: You are building a composite dictionary lookup system and want to use a tuple as a key. Under what conditions is this allowed, and when will it fail?
**Hint:** Think about hashability.
<details>
<summary>Click to Reveal Answer</summary>

A tuple can be used as a dictionary key only if all elements inside the tuple are **immutable (hashable)** themselves (e.g., numbers, strings, or other tuples). If the tuple contains any mutable elements (such as a list: `(1, 2, [3, 4])`), it is unhashable and will throw a `TypeError` if used as a dictionary key.
</details>

---

#### Q14: How does wildcard unpacking (`*` operator) work when extracting elements from a tuple?
**Keywords:** Asterisk, Aggregation, List conversion
<details>
<summary>Click to Reveal Answer</summary>

Wildcard unpacking uses the `*` operator to capture any remaining unassigned elements from a sequence into a list. For example, in `first, *middle, last = (1, 2, 3, 4, 5)`, `first` gets `1`, `last` gets `5`, and `middle` aggregates the remaining elements into the list `[2, 3, 4]`.
</details>

---

### Advanced (Deep Dive)

#### Q15: Describe what happens in the Python namespaces dictionary stack when resolving an imported function call. How does namespaces isolation work?
**Keywords:** Local mapping, Global module dict, Built-in scope, Import boundary
<details>
<summary>Click to Reveal Answer</summary>

When a module is imported, Python creates a new global namespace dictionary specifically for that module. If the module calls a function within itself, the runtime executes the function in its own local namespace stack, resolving outer calls in that module's global namespace dict, keeping it isolated from the main executing module's namespaces.
</details>

---

## Wednesday: OOP and Data Structures (OOP, virtual env, lists/dicts/sets, code freeze)

### Beginner (Recall/Definition)

#### Q1: What is the purpose of the `__init__` method in a Python class?
**Keywords:** Initializer, Instance attributes, Constructor execution
<details>
<summary>Click to Reveal Answer</summary>

`__init__` acts as the object initializer method. It executes automatically when a new instance of the class is created, allowing you to bind initial instance attributes to the object using `self`.
</details>

---

#### Q2: What is a Virtual Environment in Python and why is it needed?
**Keywords:** Isolation, Dependency conflict, `venv`, site-packages
<details>
<summary>Click to Reveal Answer</summary>

A virtual environment is an isolated directory containing its own copy of the Python interpreter and clean library paths. It is needed to isolate project-specific dependencies, preventing conflicts between different versions of the same library installed globally on a machine.
</details>

---

#### Q3: What is a list comprehension in Python?
**Keywords:** Inline loop, Mapping, Filtering, List generation
<details>
<summary>Click to Reveal Answer</summary>

A list comprehension is a concise syntax to create a new list from an existing iterable (e.g. `[x**2 for x in range(5)]`). It supports inline mapping and conditional filtering in a single line, replacing standard verbose loop blocks.
</details>

---

#### Q4: Why is a set more efficient than a list for membership testing (`item in collection`)?
**Keywords:** Hash table, Constant time O(1), Linear scan O(N)
<details>
<summary>Click to Reveal Answer</summary>

Sets are implemented using hash tables. When checking membership, Python hashes the lookup value to find its bucket in O(1) constant time. Lists require scanning every element sequentially from index 0 to N-1, resulting in O(N) linear time complexity.
</details>

---

#### Q5: What is a `frozenset` in Python?
**Keywords:** Immutable set, Hashable, Key constraints
<details>
<summary>Click to Reveal Answer</summary>

A `frozenset` is an immutable version of a standard set. Because it cannot be modified after creation, it is hashable and can be used as a dictionary key or stored as an element in another set.
</details>

---

#### Q6: How does `.discard()` differ from `.remove()` on a set?
**Keywords:** KeyError, Deletion safety, Exception suppression
<details>
<summary>Click to Reveal Answer</summary>

Both delete an element from a set. If the element is missing, `.remove()` raises a `KeyError`, whereas `.discard()` fails silently without throwing an exception.
</details>

---

#### Q7: What is the purpose of a Code Freeze in the software development lifecycle?
**Keywords:** Stability, Bug fixing, Risk mitigation, Feature lock
<details>
<summary>Click to Reveal Answer</summary>

A code freeze halts new feature implementation to stabilize the codebase. During a freeze, the engineering team focuses exclusively on testing, security audits, and bug fixes to reduce deployment risks.
</details>

---

#### Q8: What is Semantic Versioning (SemVer)?
**Keywords:** Major, Minor, Patch, Version string formatting
<details>
<summary>Click to Reveal Answer</summary>

Semantic Versioning is a versioning standard using the format `MAJOR.MINOR.PATCH` (e.g. `1.0.0`):
- **MAJOR:** Incompatible API changes.
- **MINOR:** Backwards-compatible features.
- **PATCH:** Backwards-compatible bug fixes.
</details>

---

#### Q9: How do you create an empty dictionary versus an empty set using literal syntax?
**Keywords:** Dict literal, set constructor, syntax ambiguity
<details>
<summary>Click to Reveal Answer</summary>

Writing `my_var = {}` creates an empty **dictionary**. To create an empty **set**, you must use the constructor: `my_var = set()`.
</details>

---

#### Q10: What does the `id()` function do in Python?
**Keywords:** Memory address, Object reference, Identity
<details>
<summary>Click to Reveal Answer</summary>

`id(obj)` returns the unique integer value representing the memory address of the object reference in CPython. Two variables share identity `a is b` if and only if `id(a) == id(b)`.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how you would implement a rollback branching strategy during a code freeze if a critical bug is discovered in your release candidate.
**Keywords:** Release branch, Hotfix branch, Merge verification
<details>
<summary>Click to Reveal Answer</summary>

When the code freeze starts, cut a release branch (e.g. `release-v1.0.0`) from `main`. If a bug is found during final testing, cut a hotfix branch from the release branch, implement the fix, merge it back into the release branch for validation, and finally merge the release branch into `main` for tagging.
</details>

---

#### Q12: Write a list comprehension that extracts the uppercase names of products from a list of tuples `("name", price, qty)` but only if the price is greater than 100.
**Keywords:** Tuple index, Filter condition, Transformation
<details>
<summary>Click to Reveal Answer</summary>

```python
high_value_names = [item[0].upper() for item in products if item[1] > 100]
```
This iterates over each product tuple, validates the price at index 1, and projects the uppercase name at index 0.
</details>

---

#### Q13: What is the difference between a class attribute and an instance attribute in Python OOP?
**Keywords:** Shared state, constructor binding, namespace resolution
<details>
<summary>Click to Reveal Answer</summary>

A class attribute is declared inside the class body but outside any methods; it is shared by all instances of the class. An instance attribute is bound inside a method (typically `__init__`) using `self.attribute`; it is unique to each object instance.
</details>

---

#### Q14: You deleted your `venv` folder to clean up disk space, but now your Python application won't start. How do you restore it quickly?
**Keywords:** `requirements.txt`, Recreate, Reinstall dependencies
<details>
<summary>Click to Reveal Answer</summary>

Recreate the virtual environment using `python -m venv venv`, activate it, and restore all project dependencies using the locked requirements file: `pip install -r requirements.txt`.
</details>

---

### Advanced (Deep Dive)

#### Q15: Explain how Python resolves attributes using the Method Resolution Order (MRO) when class inheritance is involved. How does `super()` locate parent methods?
**Keywords:** C3 Linearization, MRO, Polymorphism, Multiple Inheritance
<details>
<summary>Click to Reveal Answer</summary>

Python resolves attribute and method names using the **C3 Linearization** algorithm, which constructs a Method Resolution Order (MRO) list for each class. When you call a method or invoke `super()`, Python searches this linear MRO list sequentially. In multiple inheritance scenarios, `super()` does not just call the immediate parent in the code; it calls the next class in the computed MRO list, preventing the "diamond inheritance" problem.
</details>

---

## Thursday: Advanced Libraries & APIs (Dunders, Exceptions, Flask, NumPy/Pandas, GenAI Security)

### Beginner (Recall/Definition)

#### Q1: What are dunder methods in Python?
**Keywords:** Double underscore, Operator overloading, Magic methods, Implicit execution
<details>
<summary>Click to Reveal Answer</summary>

Dunder (Double Underscore) methods are special methods (like `__add__` or `__str__`) used to implement operator overloading and integrate custom classes directly into the Python grammar. They are called implicitly by Python in response to standard operations.
</details>

---

#### Q2: What is the difference between `__str__` and `__repr__`?
**Keywords:** Informal user-friendly, Official debugging, String representation
<details>
<summary>Click to Reveal Answer</summary>

`__str__` returns an informal, user-friendly string representation of an object (called by `print()`). `__repr__` returns an official, developer-oriented debugging representation that ideally looks like the Python code used to construct the object.
</details>

---

#### Q3: What is Flask?
**Keywords:** Micro-framework, routing, minimal dependencies, lightweight
<details>
<summary>Click to Reveal Answer</summary>

Flask is a lightweight Python micro-web framework. It provides the bare essentials for web routing and request handling, leaving database ORMs, form validations, and authentication to separate third-party integrations.
</details>

---

#### Q4: How does Flask map HTTP endpoints to handler functions?
**Keywords:** Decorator syntax, `@app.route()`, Route binding
<details>
<summary>Click to Reveal Answer</summary>

Flask maps endpoints to handler functions using Python decorators. Prefixing a function with `@app.route("/path", methods=["GET"])` binds that URL path and HTTP method to the function.
</details>

---

#### Q5: What is the purpose of the `else` block in a `try-except` structure?
**Keywords:** Success path, Separation of concerns, Safe execution
<details>
<summary>Click to Reveal Answer</summary>

The `else` block executes *only* if the code inside the `try` block ran successfully without raising any exceptions. This helps separate the main code path from error handling logic.
</details>

---

#### Q6: Explain what `pip-audit` does.
**Keywords:** Vulnerability scanning, Dependency CVEs, Supply chain security
<details>
<summary>Click to Reveal Answer</summary>

`pip-audit` is a command-line tool that scans a Python virtual environment or `requirements.txt` file for known security vulnerabilities by querying the PyPI vulnerability database.
</details>

---

#### Q7: In Pandas, what is a DataFrame?
**Keywords:** 2D table, Labeled rows and columns, Tabular data
<details>
<summary>Click to Reveal Answer</summary>

A Pandas DataFrame is a 2D, size-mutable, tabular data structure with labeled axes (rows and columns), similar to a spreadsheet or SQL table.
</details>

---

#### Q8: What is Vectorization in NumPy?
**Keywords:** Compiled C execution, Loop elimination, `ndarray`, Speed
<details>
<summary>Click to Reveal Answer</summary>

Vectorization refers to applying mathematical operations to an entire NumPy `ndarray` simultaneously. This bypasses slow Python interpreter loops by running calculations in highly optimized, compiled C code.
</details>

---

#### Q9: What is the difference between `.loc` and `.iloc` in Pandas?
**Keywords:** Label-based, Position-based, Slicing indices
<details>
<summary>Click to Reveal Answer</summary>

`.loc` is a label-based index selector (e.g., selecting rows by row names or column labels). `.iloc` is an integer position-based selector (e.g., selecting rows by their numeric index positions `0` to `N-1`).
</details>

---

#### Q10: What is Prompt Injection in GenAI applications?
**Keywords:** Hijack instructions, Untrusted input, Context execution bypass
<details>
<summary>Click to Reveal Answer</summary>

Prompt injection occurs when user-supplied input is embedded into an LLM prompt without sanitization, allowing the user to inject instructions that override the system prompt rules and hijack the AI's behavior.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how you would implement a custom context manager in Python to ensure a resource is cleaned up safely.
**Keywords:** Dunder `__enter__`, `__exit__`, `with` statement
<details>
<summary>Click to Reveal Answer</summary>

Implement a class containing the dunder methods `__enter__` and `__exit__`. `__enter__` handles resource setup (e.g., opening a connection) and returns the resource object. `__exit__` handles cleanup (e.g., closing the connection), executing even if an exception was raised inside the `with` block.
</details>

---

#### Q12: Explain exception chaining in Python and when to use it.
**Keywords:** `raise ... from`, Preserve stack trace, Context mapping
<details>
<summary>Click to Reveal Answer</summary>

Exception chaining links a caught exception to a new, higher-level exception using the syntax `raise CustomError("message") from original_exception`. This preserves the original traceback, making it easier to debug the root cause of failures across different layers of an application.
</details>

---

#### Q13: You are fetching task durations from a database, loading them into a Pandas DataFrame, and want to calculate statistics using NumPy. Write a snippet demonstrating this calculation.
**Keywords:** `to_numpy()`, `np.mean()`, Series conversion
<details>
<summary>Click to Reveal Answer</summary>

```python
import pandas as pd
import numpy as np

# Load data into DataFrame
df = pd.DataFrame({"task_id": [1, 2], "duration_min": [45, 120]})

# Convert Pandas Series to NumPy array
durations = df["duration_min"].to_numpy()

# Calculate statistics
avg_duration = np.mean(durations)
print(f"Average: {avg_duration}")
```
</details>

---

#### Q14: How do you format a Flask JSON response with a specific HTTP status code, such as returning a 201 Created status?
**Keywords:** `jsonify()`, Tuple response, Content-Type header
<details>
<summary>Click to Reveal Answer</summary>

Use the `jsonify()` function to serialize a dictionary or list, and return it along with the numeric status code as a tuple:
```python
@app.route("/items", methods=["POST"])
def add_item():
    new_item = {"id": 1, "name": "Tablet"}
    return jsonify(new_item), 201
```
</details>

---

### Advanced (Deep Dive)

#### Q15: In a custom context manager, how does the `__exit__` method handle exceptions? How do you instruct Python to suppress or propagate the caught exception?
**Keywords:** Exception parameters, Suppression boolean return, Error propagation
<details>
<summary>Click to Reveal Answer</summary>

The `__exit__` method receives three arguments if an exception occurs: `exc_type` (exception class), `exc_val` (exception instance), and `exc_tb` (traceback object). If `__exit__` returns `True`, Python suppresses the exception and execution continues normally after the `with` block. If it returns `False` (or `None`), the exception propagates up the call stack.
</details>

---

## Friday: Security & Capstone Delivery (SSRF, cloud IMDS, capstone presenting)

### Beginner (Recall/Definition)

#### Q1: What is Server-Side Request Forgery (SSRF)?
**Keywords:** Server proxy, Unvalidated input, Loopback access, Internal network
<details>
<summary>Click to Reveal Answer</summary>

SSRF is a vulnerability where a web application accepts a user-provided URL and fetches it using the server's backend network space. This allows an attacker to bypass firewalls and make requests to internal resources.
</details>

---

#### Q2: What is the Cloud Instance Metadata Service (IMDS)?
**Keywords:** Link-local IP, `169.254.169.254`, IAM credentials, Instance metadata
<details>
<summary>Click to Reveal Answer</summary>

The Instance Metadata Service (IMDS) is an on-instance endpoint exposed by cloud providers (like AWS) at the link-local IP `169.254.169.254`. It provides information about the running virtual machine, including temporary IAM role security credentials.
</details>

---

#### Q3: Why is the link-local IP `169.254.169.254` a primary target in cloud SSRF attacks?
**Keywords:** IAM Credentials theft, Privilege escalation, AWS metadata access
<details>
<summary>Click to Reveal Answer</summary>

If an application is vulnerable to SSRF, an attacker can force the server to query `http://169.254.169.254/latest/meta-data/iam/security-credentials/`. This returns temporary AWS security credentials, allowing the attacker to hijack the instance's IAM role permissions.
</details>

---

#### Q4: How does AWS IMDSv2 mitigate SSRF attacks?
**Keywords:** Session tokens, POST request, Token header validation
<details>
<summary>Click to Reveal Answer</summary>

AWS IMDSv2 requires a session-oriented token exchange. The client must first send a `POST` request to retrieve a session token, and then include that token in a `GET` request header to access metadata. Since most simple SSRF exploits can only send basic `GET` requests, the attack is blocked.
</details>

---

#### Q5: Explain how disabling redirects in your HTTP client mitigates SSRF bypass attempts.
**Keywords:** `allow_redirects=False`, Redirect redirection, Validate loopback bypass
<details>
<summary>Click to Reveal Answer</summary>

Attackers can bypass domain validation by pointing the initial request to a public domain they control, and then returning an HTTP `302 Redirect` to `http://127.0.0.1`. Disabling redirects (`allow_redirects=False`) ensures the client stops at the first redirect response instead of following it to internal services.
</details>

---

#### Q6: What is a domain allowlist in the context of SSRF prevention?
**Keywords:** Trusted domains, Strict matches, Reject IP addresses
<details>
<summary>Click to Reveal Answer</summary>

An allowlist is a list of trusted domain names that the application is permitted to make requests to. Any user-supplied URL targeting a domain not on the allowlist is rejected immediately, preventing attacks on arbitrary internal or external endpoints.
</details>

---

#### Q7: In a capstone presentation demo, why is it recommended to prepare "seed data" beforehand?
**Keywords:** Visual dashboards, Populate lists, Save time, Professional appearance
<details>
<summary>Click to Reveal Answer</summary>

Seed data populates the database with realistic sample records before the presentation. This ensures that dashboards, charts, and lists display complete data immediately, avoiding a blank user interface and saving time during the demo.
</details>

---

#### Q8: What are the three core parts of the 15-minute Capstone presentation timeline?
**Keywords:** 8 mins demo, 4 mins architecture, 3 mins Q&A
<details>
<summary>Click to Reveal Answer</summary>

The 15-minute presentation timeline consists of:
1. **8 Minutes:** Functional product demo.
2. **4 Minutes:** Architecture and DevOps pipeline walkthrough.
3. **3 Minutes:** Technical Q&A session.
</details>

---

#### Q9: What is DNS Rebinding in SSRF attacks?
**Keywords:** DNS change, Short TTL, Loopback swap
<details>
<summary>Click to Reveal Answer</summary>

DNS Rebinding is an exploit where a domain name resolves to a safe IP during validation, but immediately resolves to a restricted loopback IP (like `127.0.0.1`) during the actual fetch, bypassing domain checks.
</details>

---

#### Q10: How does checking the resolved IP address of a domain mitigate DNS Rebinding?
**Keywords:** Resolve host, Socket check, Block loops
<details>
<summary>Click to Reveal Answer</summary>

By resolving the domain to its IP address immediately before making the HTTP request, the application can verify that the destination IP does not belong to loopback (`127.x.x.x`) or private subnets (`10.x.x.x`, `192.168.x.x`), blocking DNS Rebinding bypasses.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how you would secure a Flask endpoint that processes user-supplied URLs to fetch image previews.
**Keywords:** HTTPS scheme, Allowlist verify, Resolve IP validation, Disable redirects
<details>
<summary>Click to Reveal Answer</summary>

First, parse the URL and verify the scheme is strictly `https`. Second, check that the domain matches an allowlist of trusted hosts. Third, resolve the domain to its IP using `socket.gethostbyname()` and verify it does not belong to private or loopback ranges. Finally, execute the request with `allow_redirects=False` and a strict timeout (e.g. 3 seconds).
</details>

---

#### Q12: You are presenting your containerized full-stack application and the live login fails. How do you handle this?
**Keywords:** Fail gracefully, Explanatory fallback, Maintain schedule
<details>
<summary>Click to Reveal Answer</summary>

Avoid attempting to debug the live code on the spot, which wastes presentation time. Fail gracefully: explain what the feature is intended to do, state the expected behavior, and transition immediately to the next topic to stay on schedule.
</details>

---

#### Q13: In your capstone architecture, how does the frontend Angular client resolve the URL of the backend service dynamically inside a Docker environment?
**Keywords:** Environment files, Bridge networks, Docker Compose host proxy
<details>
<summary>Click to Reveal Answer</summary>

Angular builds environment files containing API endpoint variables. When containerized, the frontend container requests resources via the host browser, meaning the URL must map to the port exposed on the Docker host machine (e.g., `http://localhost:8080`), which proxies requests to the backend container over the bridge network.
</details>

---

#### Q14: Explain why database ports (such as PostgreSQL port 5432) should not be exposed to the host machine in your `docker-compose.yml` file.
**Keywords:** Network isolation, Ports boundary, Bridge network only
<details>
<summary>Click to Reveal Answer</summary>

Exposing port 5432 exposes the database to the host machine and external network, creating security risks. Instead, keep the database isolated within the Docker bridge network. Dependent containers (like Spring Boot) can communicate with the database internally over the bridge network, without exposing the database port to the host.
</details>

---

### Advanced (Deep Dive)

#### Q15: Describe the mechanism of a Time-of-Check to Time-of-Use (TOCTOU) vulnerability in SSRF prevention. How does executing the DNS resolution and the HTTP fetch separately introduce a race condition, and how is it resolved?
**Keywords:** DNS Cache pinning, Resolved socket fetch, Re-resolution race
<details>
<summary>Click to Reveal Answer</summary>

A TOCTOU vulnerability occurs if the application checks the DNS resolution of a domain (e.g., resolving `attacker.com` to a safe IP), but then calls `requests.get("http://attacker.com")`, prompting the HTTP client to resolve the DNS query *again*. During the split-second between the check and use, the DNS mapping can change to point to a loopback IP (`127.0.0.1`). To resolve this race condition, resolve the IP first, perform the safety checks, and then make the HTTP request directly to the resolved IP address, passing the original domain name in the Host header for virtual host routing.
</details>
