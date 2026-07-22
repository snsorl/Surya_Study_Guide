package com.cohort.vehicles;

public class Truck extends Vehicle implements Driveable {
    public Truck(String model) {
        super(model);
    }

    public Truck() {

    }

    @Override
    public void start() {
        speed = 0.0;
        System.out.println("Truck speed is "+speed+".");
    }

    public void soundHorn(){
        System.out.println("[TRUCK HORN] HONK HONK!");
    }

    public void accelerate() {
        if(speed<155) {
            speed += 20;
            System.out.println("Truck speed is now: " + speed + " mph.");
        }
        else { System.out.println("You are at max speed");
        }
    }
}
