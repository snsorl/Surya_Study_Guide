package com.cohort.vehicles;

public class GarageApp {
    public static void main(String[] args) {
        Car c = new Car();
        Truck t = new Truck();
        c.soundHorn();
        t.soundHorn();
        c.start();
        t.start();
        t.accelerate();
        c.accelerate();
        t.stop();
        c.stop();
    }
}
