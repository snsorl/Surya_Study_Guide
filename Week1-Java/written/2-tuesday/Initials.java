public class Initials {

    public static String getInitials(String name) {
        // Guard against a null reference so we don't throw an NPE below
        if (name == null) {
            return "";
        }

        // trim() removes leading/trailing whitespace,
        // and splitting on \\s+ treats any run of one-or-more spaces
        // as a single separator between name parts
        String[] parts = name.trim().split("\\s+");
        String initials = "";

        // Walk through each name part and grab its first character
        for (int i = 0; i < parts.length; i++) {
            // Skip any empty strings that might sneak through
            if (parts[i].isEmpty()) {
                continue;
            }
            initials += parts[i].charAt(0);
        }

        return initials;
    }

    public static void main(String[] args) {
        System.out.println(getInitials("John Smith"));
        System.out.println(getInitials("alice mary jones"));

        System.out.println(getInitials("jack"));

        System.out.println(getInitials("bob  bob"));
    }
}