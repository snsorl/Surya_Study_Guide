# Exercise: Initializing Your Git Repository

## Objective
Initialize a Git repository for your Java project, configure ignore rules to prevent committing compiler binaries or IDE system files, write your first commit, and push it to a remote GitHub repository.

---

## Prerequisites
- Completed Monday's reading materials on Git SCM, repos, and pushes.
- Git CLI installed and configured locally (`git config --global user.name` / `user.email`).

---

## Step-by-Step Instructions

### Step 1: Initialize the Local Repository
1.  Open your command line terminal.
2.  Navigate to your IntelliJ project root folder (e.g. `cd C:\Learning\INF-JFSD\MondaySetupVerification`).
3.  Initialize Git version tracking:
    ```bash
    git init
    ```
    *Expected output*: `Initialized empty Git repository in /path/to/project/.git/`

---

### Step 2: Configure the .gitignore File
Before staging any files, you must ensure you do not commit temporary system outputs or compiler binaries:
1.  In your project root directory, create a new text file named **`.gitignore`** (note the leading dot, no file extension).
2.  Add the following lines to the file to block unwanted builds tracking:
    ```text
    # IntelliJ System Files
    .idea/
    *.iml
    out/
    
    # Compiled Bytecodes
    *.class
    bin/
    build/
    
    # System metadata
    .DS_Store
    Thumbs.db
    ```
3.  Save the file.

---

### Step 3: Stage and Commit the Code
1.  Check what files are ready for tracking:
    ```bash
    git status
    ```
    Verify that `.idea/` and `.class` files do **not** appear in the untracked files list. Only `.gitignore` and `src/` should be visible.
2.  Stage all files:
    ```bash
    git add .
    ```
3.  Commit your staged assets to local history:
    ```bash
    git commit -m "feat: setup project environment and write primitives labs"
    ```

---

### Step 4: Configure Remote GitHub Push
1.  Log in to **GitHub** in your web browser.
2.  Click **New** to create a new repository.
    *   **Repository Name**: `MondaySetupVerification`
    *   **Public/Private**: Private (recommended)
    *   *Do NOT initialize the repository with a README, .gitignore, or License.*
3.  Copy your remote repo URL (e.g. `https://github.com/username/repo-name.git`).
4.  Bind the remote URL and push:
    ```bash
    git branch -M main
    git remote add origin YOUR_COPIED_URL
    git push -u origin main
    ```

---

## Definition of Done
- A hidden `.git/` folder exists inside your local project root.
- A `.gitignore` file correctly blocks IDE and compiled `.class` binaries.
- Running `git log` displays your first commit details.
- The project files are uploaded and viewable inside your GitHub account profile under the `main` branch.
