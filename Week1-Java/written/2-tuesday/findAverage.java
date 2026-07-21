public class findAverage {
    public static double findAverage(int[] array) {
        if (array == null) {
            return 0.0;
        }

        if (array.length == 0) {
            return 0.0;
        }

        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        double average = sum / array.length;

        return average;
    }

    public static void main(String[] args) {
        int[] nums = {10, 20, 30, 40, 50};
        System.out.println(findAverage(nums));

        int[] empty = {};
        System.out.println(findAverage(empty));

        int[] big = {2000000000, 2000000000, 2000000000};
        System.out.println(findAverage(big));

        int[] nullArray = null;
        System.out.println(findAverage(nullArray));
    }
}