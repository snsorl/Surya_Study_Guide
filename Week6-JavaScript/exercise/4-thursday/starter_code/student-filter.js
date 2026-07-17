/**
 * starter_code/student-filter.js
 * 
 * Collaborative Project: TypeScript Migration Lab (JavaScript Starting Point)
 * Follow the pair-programming steps in typescript-migration-exercise.md to convert this code to TypeScript.
 */

// Legacy JavaScript Array logic containing students
const students = [
    { id: 101, name: "Alice", score: 92, active: true },
    { id: 102, name: "Bob", score: 68, active: false },
    { id: 103, name: "Charlie", score: 85, active: true },
    { id: 104, name: "David", score: 55, active: true }
];

function getActiveStudentNames(studentList) {
    return studentList
        .filter(function(student) {
            return student.active;
        })
        .map(function(student) {
            return student.name;
        });
}

function hasFailingStudents(studentList) {
    return studentList.some(function(student) {
        return student.score < 60;
    });
}

function getActiveAverageScore(studentList) {
    const active = studentList.filter(function(student) {
        return student.active;
    });
    if (active.length === 0) return 0;
    
    const sum = active.reduce(function(total, student) {
        return total + student.score;
    }, 0);
    
    return sum / active.length;
}

console.log("Active Names:", getActiveStudentNames(students));
console.log("Has Failing Students:", hasFailingStudents(students));
console.log("Active Average:", getActiveAverageScore(students));
