package com.revature.Wed;

public class FinancialApp {

    public static double computeScore(int totalScore, int divisor) {
        if (divisor == 0) {
            return 0.0;
        }
        return (double) totalScore / divisor;
    }

    public static void main(String[] args) {
        System.out.println("Processing report...");
        double value = computeScore(100, 0);
        System.out.println("Calculated Value: " + value);
    }
}