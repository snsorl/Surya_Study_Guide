package com.revature.Wed;

import java.util.Arrays;
import java.util.Comparator;

public class RosterApp {
    public static void main(String[] args) {
        String[] studentNames = new String[5];
        studentNames[0] = "Alice";
        studentNames[1] = "Bob";
        studentNames[2] = "Alex";

        Arrays.sort(studentNames, Comparator.nullsLast(Comparator.naturalOrder()));

        for (int i = 0; i < studentNames.length; i++) {
            if (studentNames[i] != null) {
                char firstLetter = studentNames[i].charAt(0);
                System.out.println("Student First Initial (" + firstLetter + "): " + studentNames[i]);
            }
        }
    }
}