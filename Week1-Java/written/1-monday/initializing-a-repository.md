# Initializing a Git Repository and Saving Changes

## Learning Objectives
- Initialize a brand new local repository using `git init`.
- Explain the role and contents of the hidden `.git` folder.
- Inspect the state of repository files using `git status`.
- Stage files using `git add` and record commit snapshots using `git commit`.
- View commit history logs using `git log`.

---

## Why This Matters
Before Git can track your Java application, you must explicitly tell it to watch your project folder. This process is called **initializing a repository**. 

Once initialized, Git starts monitoring every file addition, deletion, and modification. The basic loop of writing code, checking status (`git status`), staging changes (`git add`), and saving snapshots (`git commit`) is the fundamental work cycle of every developer. Commits serve as permanent save points—if you make a mistake or your code breaks later, you can instantly revert back to any previous commit in your project's history.

---

## The Concept

### 1. Initializing a Repository (`git init`)
To turn an ordinary folder on your computer into a Git repository, open your terminal/command prompt, navigate to your project directory, and run:

```bash
git init
```

#### What happens under the hood?
This command creates a hidden directory named **`.git/`** inside your folder. 
- **The `.git/` folder is your repository**. It contains all the internal configurations, object databases, logs, and branch pointers that Git uses to manage version history. 
- If you delete the `.git/` folder, you delete the entire version history of that project, returning the directory back to a standard untracked folder.

---

### 2. Checking Status (`git status`)
To see which files Git is currently watching and what changes have occurred since the last commit, run:

```bash
git status
```

Git categorizes files in your folder into two main states:
- **Untracked Files**: Files that exist in your working directory, but have never been staged or committed. Git will ignore these files until you tell it to track them.
- **Tracked Files**: Files that have been staged or committed previously. Git monitors them for modifications.

---

### 3. The Stage and Commit Cycle

```
[Untracked / Modified File] 
        |
        | (git add filename)
        v
[Staged File (Staging Area)]
        |
        | (git commit -m "commit message")
        v
[Committed Snapshot (Local Repository)]
```

#### A. Staging Changes (`git add`)
To include changes in your next commit snapshot, you must move them from the working directory into the Staging Area using the `git add` command.

*   To stage a single file:
    ```bash
    git add HelloWorld.java
    ```
*   To stage all modified and untracked files in the current folder:
    ```bash
    git add .
    ```

#### B. Recording a Snapshot (`git commit`)
Once your changes are staged, you write them permanently to the repository database using the `git commit` command. Every commit requires a **commit message** that explains what changes were made.

```bash
git commit -m "Initial commit: Add HelloWorld Java program"
```
*The `-m` flag allows you to write the message directly in the terminal inside quotation marks.*

---

### 4. Viewing Commit Logs (`git log`)
To see a history of the commits you have recorded, use the log command:

```bash
git log
```

This displays the commit history showing:
- The unique **commit hash** (SHA-1 checksum identifier).
- The author name and email.
- The date and time the commit was recorded.
- The commit message.

To see a cleaner, single-line summary of history:
```bash
git log --oneline
```

---

## Command Line Walkthrough
Let's trace a step-by-step example of initializing a folder, staging a file, and committing it.

```bash
# Step 1: Create a new directory and navigate inside
mkdir my-first-repo
cd my-first-repo

# Step 2: Initialize Git
git init
# Output: Initialized empty Git repository in /path/to/my-first-repo/.git/

# Step 3: Create a dummy text file
echo "Hello Git!" > welcome.txt

# Step 4: Check status
git status
# Output shows "welcome.txt" in RED under "Untracked files"

# Step 5: Stage the file
git add welcome.txt
git status
# Output shows "welcome.txt" in GREEN under "Changes to be committed"

# Step 6: Commit the staged file
git commit -m "Create welcome text file"
# Output shows: [master (root-commit) 4a7b3c2] Create welcome text file

# Step 7: View history
git log --oneline
# Output: 4a7b3c2 Create welcome text file
```

---

## Summary
- **`git init`** creates a hidden `.git` folder, transforming an ordinary directory into a tracked Git repository.
- **`git status`** displays untracked files, modified files, and staged changes.
- **`git add`** copies modifications from the working directory to the Staging Area.
- **`git commit -m "message"`** records the staged changes permanently in the local repository database.
- **`git log`** prints a chronological list of commits in the repository.

---

## Additional Resources
- [Git Basics - Recording Changes (Pro Git Book)](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository)
- [git init Reference Guide - Atlassian](https://www.atlassian.com/git/tutorials/setting-up-a-repository/git-init)
- [git status Reference Guide - Atlassian](https://www.atlassian.com/git/tutorials/inspecting-a-repository)
