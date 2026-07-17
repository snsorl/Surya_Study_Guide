# Package Management with PyPI and pip

## Learning Objectives
- Define PyPI (Python Package Index) as Python's package repository.
- Use `pip` commands to install, upgrade, list, inspect, and uninstall third-party packages.
- Install recursive dependencies using locked version schema files (`requirements.txt`).
- Interpret semantic versioning requirements in dependencies (e.g., `==`, `>=`, `~=`).
- Identify and mitigate security vulnerabilities in third-party libraries using `pip-audit`.

---

## Why This Matters
Modern application development relies on third-party libraries. If you need to encrypt passwords, call HTTP endpoints, or connect to databases, you shouldn't write these libraries from scratch.

In the Node/JavaScript ecosystem, packages are hosted on npm and managed via the `npm` client. In Python, third-party libraries are hosted on the **Python Package Index (PyPI)** and managed using **`pip` (Python Package Installer)**. Knowing how to search PyPI, lock dependency versions for production deployments, and run vulnerability security scans using toolsets like **`pip-audit`** ensures that your applications remain stable, secure, and reproducible.

---

## The Concept

### 1. PyPI: The Python Package Index
PyPI is a public repository containing hundreds of thousands of open-source Python packages. Anyone can publish a library to PyPI, meaning you must evaluate libraries carefully before introducing them to enterprise environments.

### 2. Common `pip` Commands
*   **`pip install package_name`:** Fetches the library from PyPI and installs it in the active environment.
*   **`pip install package_name==1.5.0`:** Installs a specific version.
*   **`pip install --upgrade package_name`:** Upgrades the library to the latest version.
*   **`pip list`:** Displays all currently installed packages and their versions.
*   **`pip show package_name`:** Displays detailed metadata (summary, homepage, author, dependencies) of an installed package.
*   **`pip uninstall package_name`:** Removes the library.

### 3. Understanding Version Syntax
When locking dependency versions in `requirements.txt`, you specify match bounds:
*   **`Flask == 3.0.3` (Strict):** Installs *only* version 3.0.3.
*   **`Flask >= 3.0.0` (Minimum):** Installs 3.0.0 or any newer version.
*   **`Flask ~= 3.0.0` (Compatible):** Installs any version matching the same major release line (e.g., `3.0.x`, but not `3.1.0` or `4.0.0`). Prevents unexpected breaking API changes.

### 4. Dependency Security Scanning with `pip-audit`
Open-source packages are common targets for supply-chain attacks (such as Trojan packages or packages containing known CVEs).
*   **`pip-audit`** is a tool that scans your Python environment or `requirements.txt` file for known security vulnerabilities. It queries the PyPI vulnerability database and flags packages containing CVEs, reporting whether fixes are available.

---

## Code Examples

### 1. Daily Package Management CLI Operations
Run the following commands inside your active virtual environment (`venv`):

```bash
# 1. Inspect package details before importing
pip show requests

# 2. Upgrade pip to prevent installation issues
python -m pip install --upgrade pip

# 3. Install specific version of requests library
pip install requests==2.31.0

# 4. Uninstall a package
pip uninstall -y requests
```

### 2. Scanning for Vulnerabilities using `pip-audit`
To check if your project has security vulnerabilities, install and execute `pip-audit`:

```bash
# 1. Install pip-audit inside your virtual environment
pip install pip-audit

# 2. Scan the current virtual environment packages
pip-audit

# 3. Scan a specific requirements file (useful in CI/CD pipelines)
pip-audit -r requirements.txt
```

**Sample Output from a Vulnerable Scan:**
If a package has a known CVE, `pip-audit` fails with a non-zero exit code and outputs a report:
```text
Found 1 known vulnerability in 1 package
Name     Version ID             Fix Version
-------- ------- -------------- -----------
jinja2   3.1.2   GHSA-h75v-3vv6-2244 3.1.3
```
*Action:* Update `requirements.txt` to lock `jinja2==3.1.3` or newer to resolve the vulnerability.

---

## Summary
- **PyPI** is the public repository of Python packages.
- **`pip`** is the CLI tool used to download and manage PyPI packages.
- Specify compatible package scopes using comparison operators like `==` and `~=`.
- Use **`pip-audit`** in developer workflows and CI/CD pipelines to scan dependencies for known CVE security vulnerabilities.

---

## Additional Resources
- [Python Package Index (PyPI) Official Site](https://pypi.org/)
- [pip Documentation: Installing Packages](https://pip.pypa.io/en/stable/user_guide/#installing-packages)
- [PyPI Project page for `pip-audit`](https://pypi.org/project/pip-audit/)
