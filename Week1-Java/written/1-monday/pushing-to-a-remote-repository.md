# Pushing to a Remote Repository: Linking to GitHub

## Learning Objectives
- Define the role of remote hosting platforms (GitHub, GitLab, Bitbucket) in software development.
- Explain the difference between HTTPS and SSH authentication.
- Link a local Git repository to a remote server using `git remote add`.
- Rename local default branches using `git branch -M`.
- Upload local code commits to a remote server using `git push`.

---

## Why This Matters
So far, you have learned to track your project history on your own computer. However, local storage is not enough. If your laptop breaks or gets lost, your entire codebase and its history are gone. Furthermore, a single developer workspace cannot easily share files or collaborate with other engineers. 

To solve this, developers use **remote repositories** hosted in the cloud. Platforms like **GitHub** serve as centralized hubs where your code is securely backed up and shared. Connecting your local repository to a remote host is a mandatory skill that enables you to collaborate on team projects and deploy code to servers.

---

## The Concept

### 1. What is a Remote Repository?
A remote repository is a mirror of your project hosted on a server connected to the internet or a private network. 

While Git is fully distributed (meaning your local repo contains the complete history), teams use a shared remote repository as the **"Single Source of Truth."** Developers copy changes from their local machines up to the remote (pushing) and download changes from the remote down to their local machines (pulling).

---

### 2. HTTPS vs. SSH Authentication
Before you can push code to GitHub, GitHub must verify your identity to ensure you have write access to that project. There are two primary protocols for this:

#### A. HTTPS (Hypertext Transfer Protocol Secure)
- **How it works**: Uses a standard URL (e.g., `https://github.com/user/project.git`).
- **Authentication**: Modern GitHub requires a **Personal Access Token (PAT)** rather than your standard account password, as passwords are no longer accepted for command-line Git actions.
- **Best for**: Beginners who want a quick setup without configuring system keys.

#### B. SSH (Secure Shell)
- **How it works**: Uses a secure SSH URL (e.g., `git@github.com:user/project.git`).
- **Authentication**: You generate a pair of cryptographic keys (a public key and a private key) on your laptop. You upload the public key to your GitHub account settings. Git handles authentication automatically without prompting you for a password on every push.
- **Best for**: Professional workflows where you push changes frequently.

---

### 3. Linking a Local Repository to a Remote
To link your local repository to a remote server, use the `git remote add` command.

#### Syntax:
```bash
git remote add <shortname> <url>
```
*   **`<shortname>`**: A shortcut name you give to the remote URL. By industry convention, the primary remote repository is always named **`origin`**.
*   **`<url>`**: The HTTPS or SSH URL provided by GitHub.

```bash
git remote add origin https://github.com/infosys-trainee/hello-java.git
```

#### Verifying Remotes:
To list the remote servers currently configured for your project, run:
```bash
git remote -v
```
*Output showing the fetch and push links:*
```text
origin  https://github.com/infosys-trainee/hello-java.git (fetch)
origin  https://github.com/infosys-trainee/hello-java.git (push)
```

---

### 4. Pushing Code (`git push`)
To upload the commits from your local repository to the remote server, use the `git push` command.

```bash
git push -u origin main
```
- **`origin`**: The shortname of the remote host.
- **`main`**: The name of the branch you are pushing.
- **`-u` (or `--set-upstream`)**: This is a critical flag used on the **very first push**. It links your local `main` branch to the remote `main` branch. For all subsequent pushes, you can simply type **`git push`** without specifying the remote or branch name.

*(Note: Git's legacy default branch name was `master`. Modern industry standards prefer `main`. Before pushing, you can rename your default branch using `git branch -M main` to align with GitHub).*

---

## Command Line Walkthrough
Here is the sequence of commands to link and upload your first repository to a newly created empty GitHub project:

```bash
# 1. Rename default branch to main (if it was master)
git branch -M main

# 2. Add the remote link (replace with your repository's URL)
git remote add origin https://github.com/my-username/hello-java-app.git

# 3. Verify the remote is linked
git remote -v

# 4. Push the code up to GitHub (prompts for credential/token on first use)
git push -u origin main

# Terminal output confirms successful push:
# Counting objects: 3, done.
# Writing objects: 100% (3/3), 220 bytes | 220.00 KiB/s, done.
# To https://github.com/my-username/hello-java-app.git
#  * [new branch]      main -> main
# Branch 'main' set up to track remote branch 'main' from 'origin'.
```

---

## Summary
- A **remote repository** is a cloud-based backup of a codebase hosted on platforms like **GitHub**.
- **HTTPS** authentication uses Personal Access Tokens (PATs) for login, while **SSH** uses cryptographic key pairs.
- **`git remote add origin <url>`** links your local repository to the remote URL under the shortcut name `origin`.
- **`git branch -M main`** renames the local default branch to `main`.
- **`git push -u origin main`** uploads local commits to the remote branch and establishes upstream tracking for simple future pushes.

---

## Additional Resources
- [Adding a Remote Repository - Git Book](https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes)
- [Connecting to GitHub with SSH - GitHub Docs](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
- [git push Command Reference - Atlassian](https://www.atlassian.com/git/tutorials/syncing/git-push)
