# Exercise: Manual vs. AI String Manipulation

## Objective
Write three core string-parsing algorithms manually without assistance to master loop bounds, character conversions, and index indexing, then use AI to generate the same logic and document code differences.

---

## Prerequisites
- Completed Wednesday's reading materials on String basics, immutability, comparisons, and pools.

---

## Step-by-Step Instructions

### Step 1: Implement the Algorithms Manually
Create a class named `StringProcessor` in your IntelliJ project. Write the following methods using only primitive loops, array indexing, and basic `charAt()` method calls. **Do NOT use external helper classes (like StringBuilder) or AI suggestions during this step.**

#### Method 1: String Reverser
- **Signature**: `public static String reverse(String input)`
- **Behavior**: Returns a new string containing the characters of the input in reverse order. Return empty string if input is null.

#### Method 2: Vowel Counter
- **Signature**: `public static int countVowels(String input)`
- **Behavior**: Returns the count of vowels (`a`, `e`, `i`, `o`, `u`, case-insensitive) inside the string.

#### Method 3: Palindrome Verifier
- **Signature**: `public static boolean isPalindrome(String input)`
- **Behavior**: Returns `true` if the string reads the same forwards and backwards (ignoring casing and spaces).

---

### Step 2: Generate AI Solutions
1.  Open your AI assistant panel.
2.  Prompt the AI to write the same three methods.
    *   *Example*: *"Write a Java class named AIStringProcessor containing methods to reverse a string, count vowels, and check if a string is a palindrome."*
3.  Analyze the AI's suggestions. Did it use different classes? (e.g. did it use `StringBuilder.reverse()`? Did it use streams or regular expressions?)

---

### Step 3: Compare and Log Findings
Create a Markdown file named **`string-comparison-log.md`** inside your project directory. Document:
1.  **Code Differences**: Contrast your manual code against the AI's code. Detail why the AI's code might be shorter (e.g., usage of `StringBuilder`) and why your manual code is important for learning loops.
2.  **Edge Case Evaluations**: Did either implementation fail when passed a `null` reference or an empty string?

---

## Definition of Done
- A compiled, running class named `StringProcessor` exists containing your manual algorithms.
- A comparison log named `string-comparison-log.md` is saved.
- Both your manual code and the AI's code have null guards implemented to prevent system runtime exceptions.
