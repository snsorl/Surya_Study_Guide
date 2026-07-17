/**
 * starter_code/app.js
 * 
 * Trainee Exercise: JavaScript Fundamentals
 * Follow the directions inside js-fundamentals-exercise.md to complete this lab.
 */

// ==========================================
// DATASET: Student Records
// ==========================================
const students = [
    { id: 101, name: "Alice", score: 92, active: true },
    { id: 102, name: "Bob", score: 68, active: false },
    { id: 103, name: "Charlie", score: 85, active: true },
    { id: 104, name: "David", score: 55, active: true },
    { id: 105, name: "Eva", score: 79, active: false }
];

// ==========================================
// TASK 1: Functional Array Transformations
// ==========================================

// 1. Write a function that returns an array of names of active students
function getActiveStudentNames(studentList) {
    // TODO: Use filter and map array methods
    return [];
}

// 2. Write a function that checks if there is any student with a score below 60
function hasFailingStudents(studentList) {
    // TODO: Use find or another array method
    return false;
}

// 3. Write a function to calculate the average score of all active students
function getActiveAverageScore(studentList) {
    // TODO: Filter active students, then use reduce to sum scores and compute average
    return 0;
}

// ==========================================
// TASK 2: Coercion & Comparison Verification
// ==========================================

function verifyComparisons() {
    console.log("--- TASK 2 COMPARISONS ---");
    
    // TODO: Predict and log the outputs of these loose vs strict equality checks:
    // a) 100 == "100"
    // b) 100 === "100"
    // c) null == undefined
    // d) null === undefined
    
    // Write console.logs matching your predictions
}

// ==========================================
// TASK 3: Variable Scope Audits
// ==========================================

function runScopeAudit() {
    console.log("--- TASK 3 SCOPE AUDIT ---");
    
    // TODO: Explain what happens when you run this code.
    // Predict whether variable outputs leak or throw errors.
    
    if (true) {
        var leakedVar = "var-value";
        let blockedVar = "let-value";
        const constantVar = "const-value";
    }

    try {
        console.log("leakedVar:", leakedVar);
    } catch(e) {
        console.log("leakedVar failed:", e.message);
    }

    try {
        console.log("blockedVar:", blockedVar);
    } catch(e) {
        console.log("blockedVar failed:", e.message);
    }
}

// ==========================================
// Verification Execution Block
// ==========================================
console.log("Active Names:", getActiveStudentNames(students));
console.log("Has Failing Students:", hasFailingStudents(students));
console.log("Active Average:", getActiveAverageScore(students));
verifyComparisons();
runScopeAudit();
