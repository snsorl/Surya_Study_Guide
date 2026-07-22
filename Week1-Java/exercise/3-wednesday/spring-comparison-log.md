# Differences Between My Code and AI's code for three classes of Exercise 3 Wednesday
## Reverse a String
### AI's code
```public String reverseString(String input) {
        String reversed = "";
        for (int i = 0; i < input.length(); i++) {
            reversed = input.charAt(i) + reversed;
        }
        return reversed; } 
```
### Differences between our reverse string code
My code was longer because I used a character array to reverse the string whereas AI used charAt method and simply concatenated one letter at a time.     

### Vowels
```int count = 0;
        String vowels = "aeiou";
        for (int i = 0; i < input.length(); i++) {
            if (vowels.indexOf(input.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }
```        
### Differences between our vowel string code
My code and the AI's code are the same length. I used a character array with all of the vowels and checked if each one was in the word. The AI used a string with all of the vowels in it and checked if any one of the vowels was in the word. I didn't handle null values at first. The AI didn't handle null at all. 

### Palindrome
```public boolean isPalindrome(String input) {
        String reversed = reverseString(input);
        return input.equals(reversed);
    }
```

### Differences between our palindrome code 
My code and the AI's code are the same length. I didn't handle null values at first. The AI didn't handle null values. The assignment of reusing the reverse method I did it in a single line whereas AI did it in two lines.

