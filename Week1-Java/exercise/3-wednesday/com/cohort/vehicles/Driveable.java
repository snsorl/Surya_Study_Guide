package com.cohort.vehicles;

public interface Driveable {
    public abstract void accelerate();
    public default void soundHorn() {
        System.out.println("[HORN] Beep beep!");
    }

}
