# Exercise: Array Methods Challenge

## Core Tasks
In this exercise, you will implement data analysis routines on a raw student grades array using functional array methods.

1. Open a new file in your workspace named `array-methods-challenge.js` (or run in browser console).
2. Use this starting dataset:
   ```javascript
   const classRecords = [
       { name: "John", grade: "A", score: 92 },
       { name: "Sarah", grade: "B", score: 88 },
       { name: "Mike", grade: "C", score: 72 },
       { name: "Emily", grade: "F", score: 45 },
       { name: "Ashley", grade: "B", score: 81 }
   ];
   ```
3. Implement the following functions using **only array methods** (no manual loop counters):
   - **`filterPassing(records)`**: Returns an array of students with a score of `60` or above.
   - **`sortByScore(records)`**: Returns a new array containing students sorted by their score in descending order (highest score first). *Hint: `.sort()` mutates arrays; make sure to copy first.*
   - **`computeClassAverage(records)`**: Returns the numerical average score of the entire class records array.

---

## Technical Guidelines
- Avoid manual loops (`for`, `while`). Use `.filter()`, `.map()`, `.reduce()`, and `.sort()`.
- Use template strings to format logged outputs.

---

## Definition of Done
- `filterPassing` returns 4 students (filtering out Emily).
- `sortByScore` returns students in order: John (92), Sarah (88), Ashley (81), Mike (72), Emily (45).
- `computeClassAverage` returns `75.8`.
