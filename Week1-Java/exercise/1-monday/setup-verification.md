# Exercise: Environment Setup Verification

## Objective
Verify that your local system has the Java Development Kit (JDK) installed, the paths configured, and IntelliJ IDEA set up correctly to compile and run a simple Java program.

---

## Prerequisites
- Completed Monday's reading materials on JVM architecture and environment setup.
- Downloaded and installed the JDK (Java 17 or higher) and IntelliJ IDEA Community/Ultimate Edition.

---

## Step-by-Step Instructions

### Step 1: Verify Command Line Paths
1.  Open your operating system's terminal (Command Prompt or PowerShell on Windows, Terminal on macOS/Linux).
2.  Run the following command to verify the runtime environment version:
    ```bash
    java -version
    ```
    *Expected output*: A version header indicating version 17 or higher (e.g. `java version "17.0.x"`).
3.  Run the following command to verify the compiler path:
    ```bash
    javac -version
    ```
    *Expected output*: A matching compiler version (e.g. `javac 17.0.x`).
    
> [!WARNING]
> If either command outputs "command not found" or "not recognized", review Monday's [setup-jdk.md](../../written/1-monday/setup-jdk.md) file to configure your system environment variables (`JAVA_HOME` and `PATH`).

---

### Step 2: Create a Project in IntelliJ IDEA
1.  Launch **IntelliJ IDEA**.
2.  Click **New Project**.
3.  Configure the project settings:
    *   **Name**: `MondaySetupVerification`
    *   **Location**: Choose a folder in your workspace directory.
    *   **Language**: `Java`
    *   **Build System**: `IntelliJ`
    *   **JDK**: Select your installed JDK 17 (if not detected, click *Add JDK* and point to your JDK installation folder).
4.  Click **Create**.

---

### Step 3: Write and Run Hello World
1.  In the Project tool window on the left, expand the project folders and locate the **`src`** directory.
2.  Right-click the `src` folder, select **New** -> **Java Class**.
3.  Name the class `VerifyApp` and press Enter.
4.  Replace the file contents with the following code:
    ```java
    public class VerifyApp {
        public static void main(String[] args) {
            System.out.println("=== Environment Verification successful! ===");
            System.out.println("JDK Version: " + System.getProperty("java.version"));
            System.out.println("OS Name:     " + System.getProperty("os.name"));
        }
    }
    ```
5.  Run the application by clicking the green **Run** (Play) icon next to the `main` method signature, or by pressing `Shift + F10` (Windows) / `Control + R` (macOS).

---

## Definition of Done
- Both `java -version` and `javac -version` compile successfully in the terminal.
- A new Java project is initialized in IntelliJ.
- The `VerifyApp` class compiles and runs successfully inside the IDE.
- The console prints "Environment Verification successful!" alongside your system's JDK version and OS name.
