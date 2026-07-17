# Python Modules and Packages

## Learning Objectives
- Formulate import statements using `import`, `from ... import`, and `import as` aliasing.
- Assemble namespaces into packages using **`__init__.py`** files.
- Trace how the Python interpreter locates imports using the **`sys.path`** search list.
- Navigate and apply core tools from the Python Standard Library (`os`, `sys`, `json`, `datetime`, `pathlib`, `collections`, `itertools`).
- Package and organize custom modules for project-wide reuse.

---

## Why This Matters
As codebases grow, storing all logic in a single file becomes impossible. Modular design breaks systems down into separate files (modules) and folders (packages) containing related features.

In Java, import paths match package folder pathways mapped from the source root (e.g., `import com.infosys.service.UserService;`). In Python, importing is highly dynamic. Python uses a simple search path system (`sys.path`) to locate modules and packages. Understanding how Python imports modules, the role of initialization files (`__init__.py`), and the rich set of pre-packaged utilities in the **Python Standard Library** prevents namespace pollution and import errors in production systems.

---

## The Concept

### 1. Modules vs. Packages
*   **Module:** A single Python file (ending in `.py`) containing executable code, functions, classes, or variables.
*   **Package:** A directory containing multiple modules. By organizing modules into directories, you establish a hierarchical folder structure.

### 2. Syntax for Importing
Python provides several ways to import names from modules:
1.  **`import math`:** Imports the entire module. Access attributes using namespace prefixing: `math.sqrt(16)`. (Preferred because it keeps namespaces clean).
2.  **`from math import sqrt`:** Imports only the specific function into the local namespace. Call directly: `sqrt(16)`.
3.  **`import numpy as np`:** Aliases the module to prevent collisions or shorten commands.
4.  **`from math import *` (Wildcard):** Imports all names. **Avoid this practice** as it causes namespace collisions.

### 3. The Package Initialization: `__init__.py`
Historically, Python required every package directory to contain a file named `__init__.py` to be recognized as a package.
*   Since Python 3.3 (Namespace Packages), this file is technically optional.
*   *Best Practice:* Include `__init__.py` anyway. It runs automatically when the package is imported, allowing you to expose clean public API interfaces, set initialization constants, and define `__all__` configurations.

### 4. How Python Finds Imports: `sys.path`
When you write `import my_module`, Python searches for the module sequentially inside the directories listed in **`sys.path`**:
1.  The directory containing the input script (active execution folder).
2.  The standard library directory.
3.  The `site-packages` directory (where third-party libraries installed via `pip` are stored).

If a module is not in any of these paths, Python throws an **`ImportError`** or **`ModuleNotFoundError`**. You can append paths to `sys.path` dynamically, but modifying execution paths in production is considered an anti-pattern.

### 5. Essential Python Standard Libraries
Python follows a "batteries-included" philosophy, shipping with a massive collection of built-in libraries:
*   **`os`:** Interface for OS-specific file operations, environment variables (`os.environ`).
*   **`sys`:** System-specific parameters and command line arguments (`sys.argv`).
*   **`pathlib`:** Object-oriented filesystem path manipulation (preferred over `os.path`).
*   **`json`:** JSON encoder and decoder.
*   **`datetime`:** Date and time manipulation.
*   **`collections`:** High-performance container data types (`namedtuple`, `Counter`, `defaultdict`).
*   **`itertools`:** Functions creating iterators for efficient looping.

---

## Code Examples

### 1. Package Structure Setup
Consider the following project directory layout:

```text
my_project/
│
├── main.py
└── database/
    ├── __init__.py
    └── connector.py
```

#### The Connector Module (`database/connector.py`):
```python
def get_db_connection():
    return "Established mock database connection."
```

#### The Initialization File (`database/__init__.py`):
```python
# Simplify imports for package consumers
from .connector import get_db_connection
```

#### Consuming the Package (`main.py`):
```python
# Import from the database package directly
from database import get_db_connection

connection = get_db_connection()
print(connection)
```

---

### 2. Exploring Core Standard Libraries
```python
import os
import sys
from pathlib import Path
from collections import Counter

# 1. Using pathlib to locate directories
current_dir = Path.cwd()
print("Current Working Directory Object:", current_dir)
print("Parent Directory Path:", current_dir.parent)

# 2. Reading environment variables using os
env_path = os.environ.get("PATH")
print("Path environment variable exists:", env_path is not None)

# 3. High performance container: Counter
votes = ["yes", "no", "yes", "yes", "no", "abstain"]
vote_counts = Counter(votes)
print("Vote analysis:", vote_counts) # Output: Counter({'yes': 3, 'no': 2, 'abstain': 1})
```

---

## Summary
- A **module** is a `.py` file; a **package** is a directory of modules.
- Use **`import`** for full namespace tracking, **`from ... import`** for specific names, and **`as`** for aliasing.
- The **`__init__.py`** file marks directories as packages and runs initialization code.
- **`sys.path`** contains the list of folders Python searches when importing.
- The **Standard Library** contains robust, pre-installed modules (`pathlib`, `os`, `sys`, `collections`) to handle common development tasks.

---

## Additional Resources
- [Python Documentation: Modules and Packages](https://docs.python.org/3/tutorial/modules.html)
- [Real Python: Python Modules and Packages: An Introduction](https://realpython.com/python-modules-packages/)
- [Real Python: Python pathlib: Object-Oriented File Paths](https://realpython.com/python-pathlib/)
