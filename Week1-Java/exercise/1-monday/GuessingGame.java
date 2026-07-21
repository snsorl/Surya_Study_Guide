import java.util.Scanner;

public class GuessingGame {

    public static void main(String[] args){
        int targetNumber = (int) (Math.random() * 50) + 1;
        int targetGuess = 0 ;

        boolean hasGuessedCorrectly = false;
        int maxAttempts = 5;
        int attemptCount = 0;
        Scanner scanner = new Scanner(System.in);

        while(!hasGuessedCorrectly && attemptCount <= maxAttempts) {
            System.out.print("Enter a number between 1 and 50: ");
            targetGuess = scanner.nextInt();
            if(targetGuess < targetNumber){
                System.out.println("You've guessed too low, try again.");
            }
            else if(targetGuess > targetNumber){
                System.out.println("You've guessed too high, try again");
            }
            else {
                System.out.println("You've guessed correctly.");
                hasGuessedCorrectly = true;
                break;
            }
            attemptCount++;
        }
        if(!hasGuessedCorrectly) System.out.println("Sorry you ran out of attempts. The target number was "+targetNumber+" .");

        scanner.close();
    }
}
