# Cohort AI Tooling Configuration

## Learning Objectives
- Locate, install, and configure coding assistant plugins (e.g., GitHub Copilot or Google Gemini Code Assist) in IntelliJ IDEA on Windows.
- Manage secure OAuth developer authentication within the IDE.
- Adjust autocomplete settings (automatic vs. hotkey triggers) to optimize focus.
- Configure context exclusions and telemetry settings to protect corporate intellectual property (IP).

---

## Why This Matters
Learning prompt engineering theories is valuable, but to write code productively in a modern business workspace, you must integrate these assistants directly into your development workflow. This means configuring plugins in your Integrated Development Environment (IDE), which for this training is IntelliJ IDEA.

Furthermore, you must do this responsibly. Corporate developers have access to private database endpoints, proprietary algorithms, and customer data. If you leave your plugin configured to "share code snippets with public servers" (telemetry), you can leak company secrets, leading to compliance violations. Knowing how to set scope boundaries, disable code-sharing, and customize completion triggers ensures you code productively without creating security risks.

---

## The Concept

### Typical IDE Integration Flow
Most enterprise coding assistants function through a lightweight IDE plugin that connects to a cloud-based LLM service:

```
[ IntelliJ IDEA (Windows) ] =====(Secure OAuth API Key)=====> [ Cloud LLM Engine ]
  ├── Current Open File context                                  (Generates completion)
  └── Cursor Location state
```

To initialize this in your workspace, you must complete three distinct phases:
1.  **Plugin Installation**: Downloading the certified assistant plugin.
2.  **OAuth Authentication**: Log in through your browser using your enterprise credentials to generate a secure local access token.
3.  **Local Indexing Scoping**: Defining which folders and files the assistant is allowed to read.

---

### Step-by-Step Configuration in IntelliJ IDEA (Windows)

#### Step 1: Install the Plugin
1. Open IntelliJ IDEA.
2. Go to **File** -> **Settings** (or press `Ctrl + Alt + S` on Windows).
3. In the left panel, click on **Plugins**.
4. Select the **Marketplace** tab at the top.
5. Search for your cohort's designated plugin (e.g., `GitHub Copilot` or `Gemini Code Assist`).
6. Click **Install**, then restart the IDE.

#### Step 2: Authentication
1. Upon restart, a prompt will appear in the bottom right corner asking you to sign in.
2. Click the sign-in button. A pop-up will display a **Verification Code** and open your web browser.
3. Paste the code into the browser authorization page, log in with your corporate account, and click **Authorize**.
4. The IDE plugin will automatically download your access token and activate.

#### Step 3: Optimizing Autocomplete Triggers
By default, the assistant will suggest code inline as you type (shown as gray ghost text). This can be highly distracting when you are trying to think through a logic problem.
To disable automatic triggers and use manual triggers:
1. Go to **File** -> **Settings** -> **Tools**.
2. Click on the settings page for your assistant (e.g. **GitHub Copilot**).
3. Uncheck the option: **Enable Auto-completion / Enable Automatic completions**.
4. To trigger suggestions manually, use the hotkey: **`Alt + \`** (Windows) or **`Option + \`** (macOS). Press **`Tab`** to accept the suggestion.

---

### Exclusions and IP Protection
To ensure private variables, credentials, or proprietary files are never sent to external AI servers, you must set up exclusion rules.

#### Creating a `.copilotignore` file
If you are using GitHub Copilot, you can exclude sensitive paths by creating a file named `.copilotignore` in the root of your project:

```text
# Exclude database config files containing local usernames/passwords
src/main/resources/application.properties
src/com/infosys/config/db.config

# Exclude private data keys
keys/
*.pem
```

#### Disabling Telemetry (Data Sharing)
Most cloud assistants ask to collect your code snippets to "improve the model." **In a corporate setting, you must disable this immediately:**
1. Log into your assistant's web dashboard (e.g. github.com settings).
2. Navigate to the Copilot settings panel.
3. Locate the setting: *"Allow GitHub to use my code snippets for product improvements"* and set it to **Block / Do Not Allow**.

---

## Summary
- Developer efficiency is maximized by integrating AI assistants directly into **IntelliJ IDEA**.
- Plugins are downloaded via the **Marketplace** and authorized securely using **OAuth browser validations**.
- Set autocomplete to **manual hotkey (`Alt + \`)** to keep your mind focused on logic rather than accepting ghost completions blindly.
- Use **exclusions (.copilotignore)** and disable **telemetry code sharing** to protect corporate IP and secrets.

---

## Additional Resources
- [IntelliJ IDEA Plugin Management Guide - JetBrains Docs](https://www.jetbrains.com/help/idea/managing-plugins.html)
- [GitHub Copilot JetBrains Integration - GitHub Docs](https://docs.github.com/en/copilot/using-github-copilot/getting-started-with-github-copilot-in-jetbrains-ides)
- [Excluding Content from GitHub Copilot - GitHub Docs](https://docs.github.com/en/copilot/configuring-github-copilot/excluding-files-from-github-copilot)