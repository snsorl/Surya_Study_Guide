public class PrimitivePractice {
    public static void main(String[] args) {
        byte smallVal = 51;
        short myShort = Short.MAX_VALUE;
        int largeVal = Integer.MAX_VALUE;
        long myLong = Long.MAX_VALUE;
        float myFloat = Float.MAX_VALUE;
        double myDouble = Double.MAX_VALUE;
        char myChar = '0';
        boolean myBoolean = false;

        System.out.println("Byte: " + smallVal);
        System.out.println("Short: " + myShort);
        System.out.println("Integer: "+ largeVal);
        System.out.println("Long: "+myLong);
        System.out.println("Float: "+myFloat);
        System.out.println("Double: "+myDouble);
        System.out.println("Character: "+myChar);
        System.out.println("Boolean: "+myBoolean);

        smallVal = 50;
        largeVal = smallVal;

        System.out.println("Byte: " + smallVal);
        System.out.println("Integer (Casting Byte): "+ largeVal);

        myDouble =  99.99;
        int truncatedVal = (int) myDouble;

        System.out.println("Double: " + myDouble);
        System.out.println("Integer (Casting Double): "+ truncatedVal);
        // Due to cast, extra numbers were commented out.

        int a = 10;
        int b = a;
        b = 20;
        System.out.println("Integer: "+ a);
        System.out.println("Integer: "+ b);

        int[] firstArray = new int[]{10,20,30};
        int[] secondArray = firstArray;
        secondArray[0]=999;
        System.out.println("firstArray[0]: " +firstArray[0]);
        System.out.println("secondArray[0]: " +secondArray[0]);
        // Second Array is a reference to firstArray so changing any element of Second Array will affect first array

    }
}
