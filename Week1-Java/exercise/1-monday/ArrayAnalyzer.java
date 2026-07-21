public class ArrayAnalyzer {

    public static void main(String[] args){
        int[] numbers = new int[10];
        numbers[0] = (int) (Math.random() * 100) + 1;
        int sum = 0;
        int min = numbers[0];
        int max = numbers[0];
        for(int i=1; i<numbers.length; i++){
            numbers[i] = (int) (Math.random() * 100) + 1;
            System.out.print(numbers[i]+ " ");
            sum+=numbers[i];
            if(numbers[i]<min) min = numbers[i];
            if(numbers[i]>max) max = numbers[i];
        }
        System.out.println("\nSum: "+sum);
        System.out.println("Average: "+sum/numbers.length);

    }
}
