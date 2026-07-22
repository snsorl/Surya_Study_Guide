package com.cohort.vehicles;

public class Car extends Vehicle implements Driveable{
    public Car(String model) {
        super(model);
    }

    public Car() {
        super();
    }

    @Override
    public void start() {
        speed = 0.0;
        System.out.println("Car speed is "+speed+".");
    }

    public void accelerate() {
        if(speed<200) {
            speed += 20;
            System.out.println("Car speed is now: " + speed + " mph.");
        }
        else { System.out.println("You are at max speed.");
        }
    }
}
