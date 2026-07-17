# Setting Up the JDK

## Learning Objectives
- Download the correct Java Development Kit (JDK) distribution for your system.
- Install the JDK on Windows, macOS, or Linux.
- Configure critical environment variables: `JAVA_HOME` and `PATH`.
- Verify the installation using command-line commands.

---

## Why This Matters
Installing software packages is usually as simple as running an installer, but for development tools, there is a crucial extra step: configuration. The OS and the IDEs you use need to know where to find the compiler (`javac`) and runtime launcher (`java`). 

By setting the **`JAVA_HOME`** variable and updating the system **`PATH`**, you tell your operating system exactly where your development kit lives. This allows you to compile and run programs from any directory in the command-line, and ensures that tools like IntelliJ IDEA, Maven, Gradle, and Docker can interface with Java automatically. Proper JDK setup is the very first checkpoint in your journey to writing Java code.

---

## The Concept

### Step 1: Downloading the JDK
There are many providers of JDK binaries (called "distributions"). They compile the open-source **OpenJDK** source code into installer files. Some of the most popular, free, production-ready distributions are:
- **Eclipse Temurin** (by the Adoptium working group - highly recommended)
- **Amazon Corretto** (AWS production-ready OpenJDK)
- **Microsoft Build of OpenJDK**
- **Oracle OpenJDK**

Choose the installer matches your system architecture (e.g., Windows x64, macOS Apple Silicon/AArch64, or Linux).

---

### Step 2: Installation and Environment Configuration

#### A. Windows Setup
1. **Run the Installer**: Double-click the downloaded `.msi` or `.exe` installer. Accept the default installation directory (typically `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\` or similar).
2. **Open Environment Variables**:
   - Press the Windows Key and search for `"Edit the system environment variables"`.
   - Click the **Environment Variables** button at the bottom of the System Properties window.
3. **Configure `JAVA_HOME`**:
   - Under **System variables**, click **New...**.
   - Set **Variable name** to `JAVA_HOME`.
   - Set **Variable value** to the path of your installed JDK (e.g., `C:\Program Files\Eclipse Adoptium\jdk-17.0.8.101-hotspot\`). Do *not* include the `bin` directory in this path. Click **OK**.
4. **Update the `PATH` Variable**:
   - Under **System variables**, find and select the variable named `Path` (or `PATH`), then click **Edit...**.
   - Click **New** and type: `%JAVA_HOME%\bin`.
   - Click **OK** to close all windows.

---

#### B. macOS Setup
1. **Run the Installer**: Download the `.pkg` or `.dmg` installer and run it. By default, macOS installs JDKs to `/Library/Java/JavaVirtualMachines/`.
2. **Open Terminal**: You need to update your shell configuration. Most modern Macs use the Zsh shell, which reads `.zshrc`.
3. **Configure Environment Variables**:
   - Open your profile in a text editor: `nano ~/.zshrc` (or `~/.bash_profile` if using Bash).
   - Add the following lines to the end of the file:
     ```bash
     # Set JAVA_HOME using the macOS java_home tool
     export JAVA_HOME=$(/usr/libexec/java_home -v 17)
     
     # Append Java binaries directory to PATH
     export PATH=$JAVA_HOME/bin:$PATH
     ```
4. **Apply Changes**: Save and exit (in Nano, press `Ctrl+O`, `Enter`, then `Ctrl+X`). Apply the changes to your current terminal session:
   ```bash
   source ~/.zshrc
   ```

---

#### C. Linux Setup (Ubuntu/Debian Example)
1. **Install OpenJDK**: Use the Advanced Package Tool (`apt`) to install:
   ```bash
   sudo apt update
   sudo apt install openjdk-17-jdk
   ```
2. **Find the Installation Path**: By default, apt installs JDKs to `/usr/lib/jvm/java-17-openjdk-amd64/`.
3. **Configure Environment Variables**:
   - Open your bash profile in a text editor: `nano ~/.bashrc`.
   - Scroll to the bottom of the file and add:
     ```bash
     export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
     export PATH=$JAVA_HOME/bin:$PATH
     ```
4. **Apply Changes**: Save and exit. Apply the changes:
   ```bash
   source ~/.bashrc
   ```

---

## Verification
To ensure everything has been set up correctly, open a fresh terminal or command prompt (do not use a terminal window that was open *before* you saved your environment variable changes).

Run the following verification commands:

### Verify JDK Compiler:
```bash
javac -version
```
**Expected Output**: `javac 17.0.x` (or the version number of the JDK you installed).

### Verify Java Runtime Launcher:
```bash
java -version
```
**Expected Output**:
```text
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment Temurin-17.0.8+7 (build 17.0.8+7)
OpenJDK 64-Bit Server VM Temurin-17.0.8+7 (build 17.0.8+7, mixed mode, sharing)
```

> [!WARNING]
> If you receive an error like `"javac is not recognized as an internal or external command"` or `"command not found: javac"`, it means either:
> 1. You did not close and reopen your terminal after saving environment changes.
> 2. The path to the JDK inside your variables is incorrect.
> 3. You typed `%JAVA_HOME%\bin` or `$JAVA_HOME/bin` incorrectly in the `PATH` variable.

---

## Summary
- Setting up the JDK involves copying the files (installer) and configuring how the operating system runs them (variables).
- **`JAVA_HOME`** points to the root directory where the JDK is installed (e.g., `C:\Program Files\...\jdk-17\`).
- **`PATH`** lists directories where the OS searches for command-line tools. Adding `%JAVA_HOME%\bin` (Windows) or `$JAVA_HOME/bin` (Mac/Linux) allows you to use commands like `java` and `javac` from any directory.
- Verify the configuration in a **new** terminal session using `java -version` and `javac -version`.

---

## Additional Resources
- [Eclipse Temurin Installation Guide](https://adoptium.net/installation/)
- [Oracle Environment Variable Configuration Docs](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html#GUID-E1A616DB-3C27-4638-9442-9905A692EA45)
- [How to Set JAVA_HOME on Windows, macOS, Linux - Baeldung](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
