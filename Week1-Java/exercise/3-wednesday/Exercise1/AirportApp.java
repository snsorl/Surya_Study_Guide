package com.revature.Wed;

class Vehicle {}
class Car extends Vehicle {}
class Airplane extends Vehicle {}

public class AirportApp {
    public static void main(String[] args) {
        Vehicle myVehicle = new Airplane();

        if (myVehicle instanceof Car) {
            Car myCar = (Car) myVehicle;
            System.out.println("Vehicle downcasted successfully.");
        } else {
            System.out.println("myVehicle is not a Car — it's a " + myVehicle.getClass().getSimpleName());
        }
    }
}




