# Virtual Environments and Dependency Management in Python

## Learning Objectives
- Articulate the need for virtual environments in Python dependency isolation.
- Create a virtual environment using the `venv` module.
- Activate and deactivate virtual environments across Windows and Unix-based shell environments.
- Install, update, and manage dependencies using `pip` inside an active virtual environment.
- Document and restore project environments using `requirements.txt` files.
- Configure version control (`.gitignore`) to exclude environment directories.

---

## Why This Matters
In Java development, dependency management is handled declaratively using build systems like **Maven** (`pom.xml`) or **Gradle** (`build.gradle`). These tools download dependencies and isolate them in project-specific or local user caches automatically.

Python, by default, installs packages globally. If multiple applications on your machine require different versions of the same library (for example, Project A requires Flask 2.x and Project B requires Flask 3.x), global installation causes immediate version conflicts. To solve this, Python uses **Virtual Environments (`venv`)**. A virtual environment is an isolated directory tree containing its own Python executable and package directories. Using `venv` ensures that your projects remain isolated, clean, and easily reproducible in Docker containers or staging servers.

---

## The Concept

### 1. What is `venv`?
The `venv` module provides support for creating lightweight, isolated virtual environments.
*   It creates a folder (conventionally named `venv` or `.venv` at the root of the project).
*   Inside this folder, it creates a copy (or symlink) of the Python interpreter and clean directories for third-party libraries (in `Lib/site-packages`).
*   When activated, it modifies your shell's environment variables (specifically `PATH`) so that commands like `python` and `pip` map to the virtual environment's executables instead of the system-wide global directories.

### 2. Creation and Activation
To create a virtual environment, run the built-in `venv` module:
```bash
python -m venv venv
```

To use it, you must **activate** it. The command depends on your Operating System and shell:

| Platform | Shell | Activation Command |
| :--- | :--- | :--- |
| **Windows** | PowerShell | `.\venv\Scripts\Activate.ps1` |
| **Windows** | CMD | `.\venv\Scripts\activate.bat` |
| **macOS/Linux** | Bash/Zsh | `source venv/bin/activate` |

You will know activation was successful because your terminal prompt will be prefixed with the environment name: `(venv) PS C:\Project>`.
To exit the environment, run: `deactivate`.

### 3. Package Tracking: `requirements.txt`
In Maven, dependencies are listed in `pom.xml`. In Python, the standard equivalent is a flat file named **`requirements.txt`**.
*   **`pip freeze`**: Inspects the active environment and prints a list of all installed packages and their exact versions.
*   **Exporting:** To save current dependencies, redirect the output:
    - `pip freeze > requirements.txt`
*   **Restoring:** To install all dependencies listed in a requirements file in a new setup:
    - `pip install -r requirements.txt`

### 4. Version Control: `.gitignore`
Virtual environments contain hundreds of dependency files and binaries. **Never commit the `venv` directory to source control.** 
Always add the environment folder name to your `.gitignore` file:
```text
# Exclude Python virtual environments
venv/
.venv/
env/
```

---

## Code Examples

### 1. Complete Workflow: Setup to Freeze
Here is the step-by-step terminal execution path for managing dependencies on Windows:

```powershell
# 1. Navigate to your project directory
# 2. Create the virtual environment
python -m venv venv

# 3. Activate the environment (PowerShell)
.\venv\Scripts\Activate.ps1
# (You should see '(venv)' prefix on your command line now)

# 4. Update pip to the latest version inside the venv
python -m pip install --upgrade pip

# 5. Install a dependency (e.g., Flask)
pip install Flask==3.0.3

# 6. Verify installation
pip list

# 7. Export the dependency schema
pip freeze > requirements.txt
```

### 2. Sample `requirements.txt` Content
When generated, `requirements.txt` will list the main library along with its recursive dependencies, locked to precise versions:
```text
click==8.1.7
Flask==3.0.3
itsdangerous==2.2.0
Jinja2==3.1.4
MarkupSafe==2.1.5
Werkzeug==3.0.3
```

---

## Summary
- **Virtual Environments (`venv`)** isolate project-specific dependencies to prevent system-wide package version conflicts.
- Create environments using `python -m venv venv`.
- Activate using OS-specific script paths (e.g., `.\venv\Scripts\Activate.ps1` on Windows).
- Use **`pip freeze > requirements.txt`** to document dependencies, and **`pip install -r requirements.txt`** to restore them.
- Always add `venv/` to **`.gitignore`** to keep binaries out of repository history.

---

## Additional Resources
- [Python Documentation: Virtual Environments and Packages](https://docs.python.org/3/tutorial/venv.html)
- [Real Python: Python Virtual Environments: A Primer](https://realpython.com/python-virtual-environments-a-primer/)
- [pip documentation: User Guide](https://pip.pypa.io/en/stable/user_guide/)
