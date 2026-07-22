package com.cohort.Wed;

public class StringProcessor {

    public static boolean checkNullInput(String input){
        return input == null;
    }

    public static String reverse(String input){
        String reverse = "";
        if(checkNullInput(input)) return reverse;
        char[] myCharArray = new char[input.length()];
        myCharArray = input.toCharArray();
        for(int i = myCharArray.length-1; i>=0; i--)
            reverse+=myCharArray[i];
        return reverse;
    }

    public static int countVowels(String input){
        int vowelCount = 0;
        if(checkNullInput(input)) return vowelCount;
        for(char c :  input.toCharArray())
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                vowelCount++;
        return vowelCount;
    }

    public static boolean isPalindrome(String input){
        if(checkNullInput(input)) return false;
        return input.equals(reverse(input));
    }

    public static void main(String[] args){
        System.out.println(reverse("hello"));
        System.out.println(countVowels(null));
        System.out.println(isPalindrome("racecar"));
    }
}
