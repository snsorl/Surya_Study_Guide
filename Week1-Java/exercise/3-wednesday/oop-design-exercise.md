# Exercise: OOP Design Challenge (Vehicle Hierarchy)

## Objective
Design and implement an object-oriented class hierarchy utilizing inheritance, abstract classes, constructor chaining (`super()`), and interface capability contracts.

---

## Prerequisites
- Completed Wednesday's reading materials on OOP pillars, classes vs objects, inheritance, and interfaces.

---

## Technical Specifications
You must write a system that models the following structure:

```
                  [ Driveable ] (Interface)
                        │
                        ├────────────────┐
                        ▼                ▼
  [ Vehicle ] (Abstract)              [ Car ] (Concrete)
        │                                - implements Driveable
        ├────────────────┐
        ▼                ▼
     [ Car ]          [ Truck ]
```

### 1. The `Driveable` Interface
- Declare an interface named `Driveable`.
- Methods:
  - `void accelerate();` (Abstract)
  - `default void soundHorn()` (Concrete): Prints a default horn sound: `"[HORN] Beep beep!"`.

### 2. The `Vehicle` Abstract Class
- Declare an abstract class named `Vehicle`.
- Fields:
  - `protected String model;`
  - `protected double speed;`
- Constructor:
  - Parameterized constructor accepting `String model`. Set `speed = 0.0`.
- Methods:
  - `public abstract void start();` (Abstract method forcing subclasses to start the engine).
  - `public void stop()` (Concrete method): Sets `speed = 0.0` and prints engine stop logs.

### 3. Subclasses: `Car` and `Truck`
- Both classes must extend `Vehicle` and implement `Driveable`.
- Implement parameterized constructors chaining parameters to `super(model)`.
- Implement `start()`, `accelerate()`, and override the default `soundHorn()` method to provide unique subclass sounds (e.g. `"[TRUCK HORN] HONK HONK!"`).

---

## Step-by-Step Instructions

### Step 1: Create Interface and Base Class
1.  In IntelliJ, create a new package named `com.cohort.vehicles`.
2.  Create the `Driveable` interface file.
3.  Create the abstract `Vehicle` class file containing the constructor and method stubs.

---

### Step 2: Implement Concrete Subclasses
1.  Create `Car` and `Truck` extending `Vehicle` and implementing `Driveable`.
2.  Implement constructor chaining:
    ```java
    public Car(String model) {
        super(model);
    }
    ```
3.  Fill in the abstract methods:
    *   Inside `Car.accelerate()`, increment `speed` by `20.0` and print status.
    *   Inside `Truck.accelerate()`, increment `speed` by `10.0` and print status.
4.  Override the default interface method `soundHorn()` in `Truck`.

---

### Step 3: Run the Polymorphism Test
1.  Create a class named `GarageApp` with a `main` method.
2.  Instantiate one `Car` and one `Truck`.
3.  Show dynamic behavior by calling `start()`, `accelerate()`, `soundHorn()`, and `stop()` on each instance.

---

## Definition of Done
- All files are organized inside the `com.cohort.vehicles` package.
- The abstract `Vehicle` class cannot be instantiated directly (attempts throw compile warnings).
- Constructor chaining is implemented, correctly configuring parent variables via `super()`.
- The `Car` and `Truck` subclasses compile and override the abstract/interface methods.
- Running `GarageApp` prints the different acceleration increments and custom horns correctly.
