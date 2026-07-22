# AI Prompts for three classes of Exercise 1 Wednesday
## First class
### Prompt  1 for first class:
Using @RosterApp, solve fix the if condition so it will not create an exception if the name of the Student does not start with an 'a'.

This is the output with the error:
Student: Alice

```Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.startsWith(String)" because "studentNames[i]" is null at com.revature.Wed.RosterApp.main(RosterApp.java:11)```

### Prompt 2 for first class:
There is Bob right so fix it so it goes to B and so on.

### Prompt 3 for first class:
Now alphabetize the list in case a student Alex comes after Bob.

### Resolution for first class:
Instead of printing all students with A, it goes through the list and prints all of the students in alphabetized order with their first initial listed first.

## Second class
### Prompt for second class:
Using @AirportApp, solve the issue that comes when an Airplane is downcasted as a car.

This is the outputted error:
```Exception in thread "main" java.lang.ClassCastException: class com.revature.Wed.Airplane cannot be cast to class com.revature.Wed.Car (com.revature.Wed.Airplane and com.revature.Wed.Car are in unnamed module of loader 'app') at com.revature.Wed.AirportApp.main(AirportApp.java:12)```


### Second class resolution:
Checked if airplane is a car using the instanceOf keyword and if it wasn't added an else condition to print what an airplane actually is.

## Third class
### Prompt for third class:

Using @FinancialApp, solve the arithmetic exception issue if divisor is zero:
This is the output with the error:
`Processing report...`

```Exception in thread "main" java.lang.ArithmeticException: / by zero at com.revature.Wed.FinancialApp.computeScore(FinancialApp.java:5) at com.revature.Wed.FinancialApp.main(FinancialApp.java:10)```

### Third class resolution:
Added a check to account for dividing by zero.
