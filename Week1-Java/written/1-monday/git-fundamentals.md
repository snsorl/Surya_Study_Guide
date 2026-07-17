# Git Fundamentals: Version Control Concepts

## Learning Objectives
- Explain the origin and core design philosophy of Git.
- Contrast Git's "Snapshot" model with traditional file "Delta" tracking.
- Detail the purpose and interaction of Git's **Three States (Trees)**: Working Directory, Staging Area, and Local Repository.
- Outline the standard lifecycle of files under Git tracking.

---

## Why This Matters
Git is the absolute standard for version control in modern software engineering. It is used by over 90% of developers worldwide. Whether you are working at a global enterprise, contributing to open-source software, or writing code for personal practice, Git is the tool you will use daily to track and sync your progress.

However, many developers treat Git as a set of magic terminal commands without understanding its underlying architecture. This leads to accidents like accidentally committing private database keys, losing work during merges, or corrupting a repository structure. By understanding Git's three-state model and snapshot architecture, you build the foundation needed to manage codebases safely and confidently.

---

## The Concept

### The Origin of Git
In 2005, the development of the Linux operating system kernel faced a crisis. The team lost their free license for the commercial version control system they were using. **Linus Torvalds** (the creator of Linux) decided to write his own tool from scratch.

His goals for the new system were:
- **Speed**: Operations should happen locally and instantly.
- **Simple Design**: The tool should have a simple internal architecture.
- **Strong Support for Non-linear Development**: Support for thousands of parallel branches.
- **Fully Distributed**: Every developer has a complete backup.
- **Data Integrity**: Everything is checksummed via cryptographic hashes (SHA-1) to prevent silent data corruption.

The result of this effort was **Git**.

---

### Snapshots vs. Deltas
The fundamental difference between Git and older version control systems (like SVN, CVS, or Perforce) is how they store history:

#### 1. The Delta Model (Traditional VCS)
Older systems store information as a list of file changes over time. They look at a file and record the specific differences (deltas) between Version A and Version B.
*   *Analogy*: Storing a video by saving only the moving pixels between frames.

```
File A: [Base File] ---> [Changes 1] ---> [Changes 2] (Delta Tracking)
```

#### 2. The Snapshot Model (Git)
Git does not store diffs. Instead, every time you commit (save) your progress, Git takes a picture of what **all your files look like at that exact moment** and stores a reference to that snapshot. 
- To save space, if a file has not changed in a commit, Git does not copy the file again. It simply stores a link to the previous identical file it already has.
*   *Analogy*: Storing a video as a sequence of complete photos.

```
Commit 1: [File A (v1)]  [File B (v1)]
Commit 2: [File A (v2)]  [File B (v1)]  <-- File B is linked back to v1
```

---

### The Three States (The Three Trees)
Git manages your project files using three distinct virtual directories:

```
+-------------------+      git add      +-------------------+
| WORKING DIRECTORY | =================> |   STAGING AREA    |
| (Files on Disk)   |                   |  (Proposed changes|
+-------------------+                   +-------------------+
                                                  |
                                                  | git commit
                                                  v
                                        +-------------------+
                                        | LOCAL REPOSITORY  |
                                        |   (.git folder)   |
                                        +-------------------+
```

#### 1. The Working Directory (Working Tree)
This is the directory on your computer containing the physical files you are editing. These files reside on your hard drive, and you modify them using your IDE (like IntelliJ) or text editor.

#### 2. The Staging Area (Index)
This is a single, hidden file located inside your Git directory. It acts as a "pre-commit" preparation zone. It tracks which files and specific lines of code you want to include in your next repository snapshot. 
- *Why is it useful?* It allows you to select only a subset of your changes to commit, keeping your save history clean and topic-focused.

#### 3. The Local Repository (`.git` directory)
This is the database where Git stores all metadata, compressed files, and the complete commit history for your project. This is the heart of Git. When you clone a repository, this is what is mirrored onto your machine.

---

### The Basic Git Workflow
The standard lifecycle of recording changes follows these steps:

1. **Modify**: You edit files in your **Working Directory**.
2. **Stage**: You run `git add` to copy a snapshot of those modifications into the **Staging Area**.
3. **Commit**: You run `git commit`, which takes the staged snapshot and saves it permanently as a new commit entry inside your **Local Repository**.

---

## Summary
- Git was created by Linus Torvalds in 2005 to handle the massive, distributed collaboration of the Linux kernel.
- Git tracks history as a stream of **Snapshots** (complete file states) rather than lists of line differences (deltas).
- The **Working Directory** holds the files you are actively editing.
- The **Staging Area** is the sandbox where you select and package changes for the next commit.
- The **Local Repository** (stored in the `.git` folder) holds the complete, compressed history of commits on your computer.
- The core workflow is: **Modify** -> **Stage (`git add`)** -> **Commit (`git commit`)**.

---

## Additional Resources
- [Git Basics - Getting Started (Pro Git Book)](https://git-scm.com/book/en/v2/Getting-Started-Git-Basics)
- [What is Git? - Atlassian Tutorial](https://www.atlassian.com/git/tutorials/what-is-git)
- [Interactive Git Tutorial - Git Immersion](https://gitimmersion.com/)
