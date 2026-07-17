# Exercise: The Guessing Game (Control Flow Challenge)

## Objective
Apply loops, conditional statements, logical operators, and scanner inputs to build an interactive, console-based game.

---

## Prerequisites
- Completed Monday's reading materials on loops (`while`, `for`, `do-while`), conditional expressions (`if-else`, `switch`), and logical/comparison operators.

---

## Game Design Requirements
You must construct a class named `GuessingGame` that behaves as follows:
1.  The program generates a secret target integer between **1 and 50** automatically.
2.  The program prompts the trainee to type their guess into the console using a `java.util.Scanner` object.
3.  The trainee has a maximum of **5 attempts** to guess the number.
4.  For each guess:
    *   If the guess is higher than the target, print: `"Too high! Try again."`
    *   If the guess is lower than the target, print: `"Too low! Try again."`
    *   If the guess is correct, print: `"Congratulations! You guessed the secret number!"` and terminate the loop immediately.
5.  If the attempts limit is reached without a correct guess, print: `"Game over! You've run out of attempts. The secret number was X."`

---

## Step-by-Step Instructions

### Step 1: Create the Class
1.  In IntelliJ, create a new Java class named `GuessingGame` in the `src` folder.
2.  Add a `main` method.
3.  Import the Scanner utility:
    ```java
    import java.util.Scanner;
    ```

---

### Step 2: Generate Target and Initialize Loop
1.  Generate a random integer between 1 and 50:
    ```java
    int targetNumber = (int) (Math.random() * 50) + 1;
    ```
2.  Declare helper tracking variables:
    *   `int maxAttempts = 5;`
    *   `int attemptCount = 0;`
    *   `boolean hasGuessedCorrectly = false;`
3.  Instantiate the Scanner:
    ```java
    Scanner scanner = new Scanner(System.in);
    ```

---

### Step 3: Implement Game Loop Logic
1.  Write a `while` loop that continues as long as `attemptCount < maxAttempts` and `hasGuessedCorrectly` is false.
2.  Inside the loop:
    *   Increment `attemptCount`.
    *   Prompt the user: `"Attempt [X/5] - Enter your guess: "`
    *   Validate input boundaries: If the user inputs a number outside the range `[1-50]`, print a warning, decrement the count (so the attempt isn't wasted), use a **`continue`** interrupt, and prompt again.
    *   Perform comparisons against `targetNumber` using `if / else if / else` logic.
    *   If the user guesses correctly, set `hasGuessedCorrectly = true` and use a **`break`** statement to terminate the loop.

---

### Step 4: Handle End-of-Game Outputs
1.  After the loop, close the scanner object to prevent resource leaks:
    ```java
    scanner.close();
    ```
2.  Write an conditional check checking if the user won or lost. Output the appropriate ending message.

---

## Definition of Done
- A compiled, running class named `GuessingGame` exists.
- The game validates inputs, rejecting numbers outside the 1–50 boundary without losing an attempt.
- The loop correctly tracks remaining attempts up to a maximum of 5.
- The system prints helpful feedback ("Too high" or "Too low") for incorrect inputs.
- The game terminates immediately upon a correct guess or displays a "Game Over" message once the 5th attempt is exhausted.
