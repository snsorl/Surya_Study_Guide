# Code Freeze Procedures and Git Release Tagging

## Learning Objectives
- Define what a code freeze is and identify its operational boundaries.
- Formulate a branching strategy to handle hotfixes during a code freeze.
- Apply semantic versioning and Git tagging procedures to lock release candidates.
- Execute a final QA cycle including regression and end-to-end testing.
- Review a deployment checklist for the final production release.

---

## Why This Matters
In agile software development, teams work in continuous sprint cycles. As you approach a major delivery deadline—such as the Capstone project presentation—continuously adding new features introduces high operational risk. A last-minute feature addition could break existing core services, leaving the team with a broken product at presentation time.

A **Code Freeze** mitigates this risk by halting all feature development. During a freeze, the codebase is stabilized: only critical bug fixes are accepted, and the team pivots to testing and documentation. Understanding the mechanics of a code freeze—including branching strategies, version tagging with Git, and final deployment checklists—ensures a smooth, risk-free release cycle.

---

## The Concept

### 1. What is a Code Freeze?
A code freeze is a stage in the software release lifecycle where the code is locked against new feature implementation.
*   **Allowed:** Critical bug fixes, security patches, documentation updates, and test suite improvements.
*   **Prohibited:** UI enhancements, new API endpoints, refactoring unrelated systems, and performance tuning (unless targeting a critical bug).

### 2. Branching Strategy during Code Freeze
To allow testing to proceed on stable code while keeping feature branches isolated, teams use a release-candidate branch structure:

```
                  (Freeze Starts)
main ------------------*----------------------------*---------> [Release v1.0.0]
                        \                          / (Merge Fixes)
release-v1.0.0           *----------------*-------*
                                           \     / (Bug Fix)
bugfix/login-error                          *---*
```

1.  **Cut the Release Branch:** At freeze start, a branch named `release-v1.0.0` is branched off `main`.
2.  **Isolate Bug Fixes:** If a bug is found during testing, a branch is cut from `release-v1.0.0` (e.g., `bugfix/login-error`).
3.  **Merge & Verify:** The fix is merged back into `release-v1.0.0` and verified.
4.  **Merge to Main:** At release day, the stabilized `release-v1.0.0` is merged back into `main` and tagged.

### 3. Git Release Tagging (Semantic Versioning)
A Git tag is a reference pointer that locks a specific commit in history, indicating it represents a stable version (like a release).
*   **Semantic Versioning (SemVer):** Version numbers use the format `MAJOR.MINOR.PATCH` (e.g., `1.0.0`):
    - **MAJOR:** Incompatible API changes.
    - **MINOR:** Backwards-compatible functionality additions.
    - **PATCH:** Backwards-compatible bug fixes.
*   **Tagging:** Creating a tag marks the release commit permanently:
    - `git tag -a v1.0.0 -m "Release version 1.0.0"`
    - `git push origin v1.0.0`

### 4. Final QA Cycle and Deployment Checklist
Before the final capstone presentation, teams must complete a checklist to ensure the application is ready for production.

---

## Code Examples

### 1. Command Line Git Tagging Workflow
Once your code is stabilized and merged into the main branch, run the following Git commands to freeze and publish the release version:

```bash
# 1. Ensure you are on the main branch
git checkout main

# 2. Pull the latest stabilized commits
git pull origin main

# 3. Create an annotated tag for version 1.0.0
git tag -a v1.0.0 -m "Final Capstone Release Version 1.0.0"

# 4. Push the tag to the remote repository
git push origin v1.0.0

# Verification: List all tags in the repository
git tag -l
```

### 2. Capstone Release Deployment Checklist

| Category | Item | Status | Verified By |
| :--- | :--- | :--- | :--- |
| **DevOps** | All Docker containers build successfully from clean states (no cached files). | `[ ]` | |
| **DevOps** | GitHub Actions CI/CD pipeline completes with all tests passing. | `[ ]` | |
| **Backend** | No hardcoded credentials (database passwords, API keys) in code; all injected via environments. | `[ ]` | |
| **Database**| Database schema migrates cleanly on startup, and volume mounts persist data correctly. | `[ ]` | |
| **Frontend** | Angular application builds in production mode (`ng build --configuration production`). | `[ ]` | |
| **Python** | Python requirements.txt is complete, and `venv` isolates dependencies properly. | `[ ]` | |

---

## Summary
- A **Code Freeze** halts all feature development to stabilize code and focus purely on testing and critical bug fixes.
- A **Release Branch** is cut to isolate release-candidate code from active feature branches.
- Use **Git Tags** (`git tag -a`) to assign permanent version markers (such as semantic version `v1.0.0`) to specific commits.
- A **Deployment Checklist** enforces quality checks across database persistence, secret hiding, front-end optimization, and container readiness before final presentations.

---

## Additional Resources
- [Git Documentation: Tagging](https://git-scm.com/book/en/v2/Git-Basics-Tagging)
- [Semantic Versioning 2.0.0 Specification](https://semver.org/)
- [Atlassian: Git Branching Workflows for Release Management](https://www.atlassian.com/git/tutorials/comparing-workflows)
