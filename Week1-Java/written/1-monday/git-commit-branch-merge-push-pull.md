# Git Workflow: Commits, Branches, Merging, and Syncing

## Learning Objectives
- Write professional, descriptive commit messages.
- Create, switch, list, and delete branches using `git branch` and `git checkout`/`switch`.
- Merge feature branch changes back into the main branch.
- Differentiate between fast-forward and three-way merges.
- Synchronize your local work with remote changes using `git fetch` and `git pull`.
- Understand the cause of merge conflicts at a high level.

---

## Why This Matters
If every developer committed directly to the `main` branch of a project, the codebase would constantly be broken. Feature code that is halfway done would mix with stable production code, making it impossible to deploy hotfixes or test features in isolation.

To solve this, Git utilizes **branches**. A branch is an isolated workspace within your repository. You can create a branch, write and commit code to test a new feature, and discard it if it fails—all without touching the stable master code. Once the feature works, you merge it back. Understanding this workflow is the key to collaborative, enterprise-ready software development.

---

## The Concept

### 1. Writing Good Commit Messages
A commit message is a note explaining *why* a change was made. Professional commit messages follow standard conventions:
- **Imperative Mood**: Write the message header as if giving a command (e.g., "Add user validation logic" instead of "Added user validation logic" or "Adds validation logic").
- **Line Length**: Keep the summary line under 50 characters. Capitalize the first word and do not end with a period.
- **Detailed Body (Optional)**: If the change is complex, leave a blank line after the summary and write a paragraph detailing *what* was changed and *why*.

---

### 2. Branching
In Git, a branch is simply a lightweight, movable pointer to a specific commit. By default, your repository starts with one default branch (typically `main`).

```
main:    [Commit 1] <--- [Commit 2] (main pointer)
                            \
feature:                     [Commit 3] (feature pointer)
```

#### Key Branching Commands:
*   **Create a Branch**:
    ```bash
    git branch feature-login
    ```
*   **Switch to a Branch**:
    - Traditional: `git checkout feature-login`
    - Modern (recommended): `git switch feature-login`
*   **Create and Switch in one command**:
    ```bash
    git checkout -b feature-login   # Traditional shortcut
    git switch -c feature-login     # Modern shortcut
    ```
*   **List Branches**:
    ```bash
    git branch
    ```
*   **Delete a Branch** (after it has been merged):
    ```bash
    git branch -d feature-login
    ```

---

### 3. Merging
Merging is the process of combining the commit history of two branches.

#### How to Merge:
1. Switch to the branch you want to merge *into* (usually `main`):
   ```bash
   git switch main
   ```
2. Merge the feature branch:
   ```bash
   git merge feature-login
   ```

#### Types of Merges:
- **Fast-Forward Merge**: Occurs if the target branch has no new commits since you branched off. Git simply moves the target pointer forward to match your feature branch pointer.
- **Three-Way Merge (Merge Commit)**: Occurs if both the target branch and the feature branch have advanced independently. Git merges the files and automatically creates a new "merge commit" that ties the histories together.

```
Fast-Forward: [Commit 1] -> [Commit 2 (main)] ============> [Commit 2] -> [Commit 3 (main/feature)]
                               \
                                [Commit 3 (feature)]
```

#### Merge Conflicts
If you and another developer edit the **exact same line of the same file** in different branches and try to merge them, Git cannot automatically decide which version is correct. It will pause the merge and flag a **Merge Conflict**. You must manually open the file, select which code to keep, delete Git's conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`), stage the fix (`git add`), and run `git commit` to complete the merge.

---

### 4. Syncing: `git fetch` vs. `git pull`
When collaborating on a remote server (like GitHub), you need to sync your local repository with commits made by other team members:

#### `git fetch` (The Safe Check)
Downloads the latest commit metadata and history from the remote repository to your local database, but **does not merge any changes** into your active working directory files. It allows you to see what others have done before integrating it.
```bash
git fetch origin
```

#### `git pull` (Download + Merge)
Downloads remote changes and **immediately attempts to merge them** into your active local branch. `git pull` is effectively a shortcut for running `git fetch` followed immediately by `git merge`.
```bash
git pull origin main
```

---

## Command Line Walkthrough
Here is a complete workflow cycle of creating a branch, committing a change, merging it to main, and pushing it:

```bash
# 1. Start on main and verify sync
git switch main
git pull origin main

# 2. Create and switch to a feature branch
git switch -c feature-add-readme

# 3. Create/Modify files and commit them
echo "# My Java Project" > README.md
git add README.md
git commit -m "Add README file describing project"

# 4. Switch back to main
git switch main

# 5. Merge the feature branch into main
git merge feature-add-readme
# Output shows: Fast-forward (README.md was added)

# 6. Push the updated main branch to GitHub
git push

# 7. Safe cleanup: Delete the local feature branch
git branch -d feature-add-readme
```

---

## Summary
- A **commit message** should use the imperative mood (e.g. "Fix calculation bug") and be descriptive.
- A **branch** is an isolated pointer to a commit, allowing developers to build features without breaking the stable master branch.
- **`git merge`** integrates changes from a source branch into the active target branch.
- **`git fetch`** downloads history updates from the remote host without changing your local working files.
- **`git pull`** downloads updates and merges them immediately into your working branch.
- **Merge conflicts** happen when identical code lines are modified on two branches simultaneously, requiring manual resolution before completing the merge.

---

## Additional Resources
- [Git Branching Basics - Git Book](https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging)
- [git branch Command Reference - Atlassian](https://www.atlassian.com/git/tutorials/using-branches)
- [git merge Command Reference - Atlassian](https://www.atlassian.com/git/tutorials/using-branches/git-merge)
