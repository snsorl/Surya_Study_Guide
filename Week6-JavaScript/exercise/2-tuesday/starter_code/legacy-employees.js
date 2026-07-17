/**
 * starter_code/legacy-employees.js
 * 
 * Trainee Exercise: ES6+ Refactoring Lab
 * Refactor this ES5 code file into modern ES6+ standards.
 * Follow the steps in es6-refactor-exercise.md.
 */

// 1. Refactor to block-scoped variables where applicable
var companyName = "Initech Corp";

// 2. Refactor Constructor Function to ES6 Class
function Employee(firstName, lastName, salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
}

// 3. Refactor method declaration using Prototype
Employee.prototype.getDetails = function() {
    // Refactor to template literals
    return this.firstName + " " + this.lastName + " earns $" + this.salary + " annually at " + companyName + ".";
};

// 4. Create sample employees
var emp1 = new Employee("Peter", "Gibbons", 85000);
var emp2 = new Employee("Samir", "Nagheenanajar", 90000);
var emp3 = new Employee("Michael", "Bolton", 92000);

var staff = [emp1, emp2, emp3];

// 5. Refactor to Arrow Functions
var calculateTotalPayroll = function(employeeList) {
    var total = 0;
    for (var i = 0; i < employeeList.length; i++) {
        total += employeeList[i].salary;
    }
    return total;
};

console.log("--- LEGACY PAYROLL SYSTEM ---");
console.log(emp1.getDetails());
console.log("Total payroll:", calculateTotalPayroll(staff));
