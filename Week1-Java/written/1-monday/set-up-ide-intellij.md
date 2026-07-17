# Setting Up Your IDE: IntelliJ IDEA

## Learning Objectives
- Differentiate between IntelliJ IDEA Community and Ultimate editions.
- Install and perform the initial configuration of IntelliJ IDEA.
- Configure the Project SDK (JDK) within the IDE.
- Create, write, and execute a basic Java application in IntelliJ.
- Navigate the standard project directory layout created by the IDE.
- Utilize productivity shortcuts (like code templates) to write Java code faster.

---

## Why This Matters
While you can write Java code in a basic text editor (like Notepad or TextEdit) and compile it via the command line, this is not how professional developers work. Modern enterprise projects contain thousands of files, external dependencies, and build pipelines. 

An **Integrated Development Environment (IDE)** is your command center. JetBrains **IntelliJ IDEA** is the industry-standard IDE for Java development. It does not just act as a text editor; it compiles your code in the background, checks for syntax errors in real-time, suggests code completions, integrates with version control (Git), and provides visual debugging tools. Getting comfortable with your IDE is one of the most valuable investments you can make in your development workflow.

---

## The Concept

### Community vs. Ultimate Edition
JetBrains offers IntelliJ IDEA in two editions:
1. **Community Edition**: Completely free and open-source. It provides full support for core Java, Kotlin, Groovy, Maven, Gradle, Git, and basic debugging. This is excellent for learning the language and building desktop or standard application backends.
2. **Ultimate Edition**: A paid commercial edition (with free trials and student licenses). It adds advanced features for enterprise frameworks (like Spring, Jakarta EE), databases, profiling, and web development (HTML, CSS, JS, TypeScript).

For this program, the **Community Edition** contains everything you need to learn Java, and the workflows below apply to both editions.

---

### Step-by-Step Setup and Project Creation

#### Step 1: Download and Install
1. Go to the [JetBrains IntelliJ IDEA download page](https://www.jetbrains.com/idea/download/).
2. Download the installer for your OS under the **Community Edition** section.
3. Run the installer and follow the prompt wizard. On Windows, check the box to "Add launchers to the PATH" and "Add open folder as project".

#### Step 2: Launch and Configure the JDK
When you launch IntelliJ for the first time, you must tell the IDE where your JDK is installed.

1. On the Welcome Screen, click **New Project**.
2. In the New Project dialog, configure the settings:
   - **Name**: Give your project a name (e.g., `HelloJava`).
   - **Location**: Choose a folder on your system where your workspace projects will live.
   - **Language**: Select **Java**.
   - **Build System**: Select **IntelliJ** (we will explore other build systems like Maven later).
   - **JDK**: Click the dropdown menu.
     - If IntelliJ automatically detects the JDK you installed in the previous step, select it (e.g., `corretto-17` or `temurin-17`).
     - If it is not listed, click **Add JDK...**, navigate to your JDK installation directory (e.g., `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\`), and select it.
     - *Alternative*: You can also click **Download JDK** directly from this menu to let IntelliJ download and configure a version of OpenJDK for you.
3. Click **Create**.

```
+-----------------------------------------------------------------+
| New Project                                                     |
+-----------------------------------------------------------------+
| Name: [ HelloJava ]                                             |
| Location: [ C:\Learning\HelloJava ]                             |
| Language: (o) Java   ( ) Kotlin   ( ) Groovy                    |
| Build System: (o) IntelliJ   ( ) Maven   ( ) Gradle             |
| JDK: [ Eclipse Temurin 17.0.8 ] (Detected)                 [v]  |
+-----------------------------------------------------------------+
```

---

### Understanding the Project Structure
Once the project loads, you will see the workspace window. On the left side is the **Project Tool Window** showing your directory tree:

- **`.idea/`**: A folder containing IntelliJ's internal configuration files. You do *not* need to edit anything inside here. By convention, this is excluded from source control.
- **`src/`**: The **Source** directory. This is where all your Java source code files (`.java`) will live.
- **`HelloJava.iml`**: A module file used by IntelliJ to track project dependencies.
- **`out/`**: Appears after you compile or run your program. This is where IntelliJ puts the compiled `.class` bytecode files.

---

### Creating and Running Your First Program

1. Right-click the **`src`** directory on the left pane.
2. Select **New** -> **Java Class**.
3. Type `Main` and press **Enter**.
4. In the editor window, enter the following code:

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from IntelliJ IDEA!");
    }
}
```

5. **Run the program**:
   - You will see a small green **Play arrow** (`▶`) next to the line `public class Main` and next to the line `public static void main`.
   - Click either play button and select **Run 'Main.main()'**.
   - Alternatively, you can use the keyboard shortcut `Shift + F10` (Windows) or `Ctrl + R` (macOS).
6. **Observe the Console**: The **Run tool window** will slide up at the bottom of the IDE, showing compilation progress, followed by the program output:
   `Hello from IntelliJ IDEA!`

---

### Pro-Tip: IntelliJ Code Templates (Live Templates)
To speed up your coding, IntelliJ has built-in short abbreviations that expand into full code structures. Try typing these inside your editor and pressing `Tab` or `Enter`:

*   **`psvm`** (or **`main`**): Type this inside a class, press Enter, and it expands to the complete main method signature:
    `public static void main(String[] args) {}`
*   **`sout`**: Type this inside a method, press Enter, and it expands to:
    `System.out.println();`
*   **`souf`**: Expands to a formatted print statement:
    `System.out.printf();`

---

## Summary
- **IntelliJ IDEA** is the primary IDE used for professional Java development.
- The **Community Edition** is free and sufficient for building and running standard Java programs.
- Before coding, you must associate your project with an installed **SDK (JDK)** under the Project Structure settings.
- The **`src/`** directory contains your source files (`.java`), and the **`out/`** directory contains compiled bytecode (`.class`).
- IntelliJ provides **Live Templates** like `psvm` and `sout` to generate boilerplate code instantly.

---

## Additional Resources
- [IntelliJ IDEA Official Installation Guide](https://www.jetbrains.com/help/idea/installation-guide.html)
- [Create your first Java application in IntelliJ - Tutorial](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)
- [IntelliJ IDEA Keyboard Shortcuts Cheat Sheet](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
