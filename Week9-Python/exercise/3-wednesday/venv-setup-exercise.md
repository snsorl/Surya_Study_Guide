# Exercise: Virtual Environment and Pip Package Management

## Objectives
- Create and activate isolated local Python virtual environments (`venv`).
- Manage external third-party package dependencies using `pip`.
- Create reproducible installation records (`requirements.txt`).
- Exclude compiled directories from Git version control.

---

## Instructions

### Step 1: Initialize the Environment
1. Open your terminal and navigate to your `exercise-1` workspace folder.
2. Run the command to initialize a new virtual environment directory:
   ```bash
   python -m venv venv
   ```
3. Verify that a directory named `venv` was created in your folder.

### Step 2: Activate and Install
1. Activate the environment:
   - **Windows PowerShell:** `.\venv\Scripts\Activate.ps1`
   - **Windows CMD:** `.\venv\Scripts\activate.bat`
   - **macOS/Linux:** `source venv/bin/activate`
   *Verify:* Your terminal prompt prefix must display `(venv)`.
2. Update the default package manager:
   ```bash
   python -m pip install --upgrade pip
   ```
3. Install the `Flask` and `requests` libraries:
   ```bash
   pip install Flask requests
   ```

### Step 3: Freeze Dependencies
1. Check your installed libraries list:
   ```bash
   pip list
   ```
2. Lock these versions by exporting to a standard requirements file:
   ```bash
   pip freeze > requirements.txt
   ```
3. Open `requirements.txt` and verify it contains version listings for `Flask`, `requests`, and their dependencies (like `urllib3` or `MarkupSafe`).

### Step 4: Validate Replicability
To verify that your installation is reproducible:
1. Deactivate the environment:
   ```bash
   deactivate
   ```
2. Delete the `venv` folder completely from your filesystem.
3. Re-create the environment:
   ```bash
   python -m venv venv
   ```
4. Activate the new environment.
5. Restore all dependencies in a single step using the locked requirements file:
   ```bash
   pip install -r requirements.txt
   ```
6. Run `pip list` to confirm that all libraries are back.

### Step 5: Gitignore Setup
1. Create a file named `.gitignore` in your project root folder.
2. Add the following line to prevent commiting the environment folder:
   ```text
   venv/
   ```

---

## Definition of Done
Your lab is complete when:
- `requirements.txt` is created containing the locked dependency libraries.
- The environment was successfully deleted, restored from the requirements file, and verified.
- `.gitignore` is created and correctly lists the `venv/` path.
