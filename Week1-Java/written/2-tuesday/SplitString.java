import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitString {
    public static int[] splitToIntArray(String input) {
        if (input == null) {
            return new int[0];
        }
        String[] parts = input.split(",");
        List<Integer> validNumbers = new ArrayList<>();

        for (String part : parts) {
            String trimmed = part.trim();
            try {
                validNumbers.add(Integer.parseInt(trimmed));
            } catch (NumberFormatException e) {
                // Not a valid integer (e.g. "twenty") — skip it
                //System.out.println("Skipping non-numeric value: " + trimmed);
            }
        }

        int[] result = new int[validNumbers.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = validNumbers.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(splitToIntArray("22,11,43,54,33")));
        System.out.println(Arrays.toString(splitToIntArray("22,twenty,43,fifty-four,33")));
        System.out.println(Arrays.toString(splitToIntArray(null)));
    }
}