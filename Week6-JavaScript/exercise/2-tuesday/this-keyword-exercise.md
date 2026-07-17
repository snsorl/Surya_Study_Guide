# Exercise: The "this" Keyword Analysis

## Core Tasks
This exercise will test your understanding of dynamic and lexical `this` binding in JavaScript.

### Part 1: Predictions
For each snippet below, write down what `this` resolves to and what will be printed to the console:

```javascript
// Snippet 1
console.log(this);

// Snippet 2
function show() {
    console.log(this);
}
show();

// Snippet 3
const user = {
    username: "Alice",
    greet() {
        console.log("Hello " + this.username);
    }
};
user.greet();

// Snippet 4
const user2 = {
    username: "Bob",
    greetDelayed() {
        setTimeout(function() {
            console.log("Hello " + this.username);
        }, 100);
    }
};
user2.greetDelayed();

// Snippet 5
const user3 = {
    username: "Charlie",
    greetDelayedArrow() {
        setTimeout(() => {
            console.log("Hello " + this.username);
        }, 100);
    }
};
user3.greetDelayedArrow();
```

### Part 2: Implementation & Fix
1. Create a script file `this-exercise.js` and copy the snippets. Run it using Node or in a browser console.
2. Note the outputs. Explain why Snippet 4 fails (logs `Hello undefined` or crashes in strict mode).
3. Fix Snippet 4 using two different remediation options:
   - **Option A**: Refactor the callback to use an arrow function.
   - **Option B**: Use the `.bind()` method explicitly.

---

## Definition of Done
- A worksheet or text file contains the predicted vs actual outcomes for the 5 snippets.
- Explanations accurately describe the dynamic binding rules for standard functions inside timers.
- A working script executes both Option A and Option B successfully, printing `Hello Bob` correctly.
