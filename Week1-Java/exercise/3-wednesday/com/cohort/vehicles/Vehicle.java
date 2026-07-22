package com.cohort.vehicles;

public abstract class Vehicle {
    protected String model;
    protected double speed = 0.0;
    public Vehicle(String model) {
    }

    public Vehicle() {

    }
    public abstract void start();
    public void stop(){
        speed = 0.0;
        System.out.println("Speed is "+speed+".");
    };
}
