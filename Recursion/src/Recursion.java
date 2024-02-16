/**
 * This project uses recursion to do isolated tasks.
 * <p>
 * This project has the author solve different types of problems.
 * In the solutions, recursion is used to complete the task at hand.
 *
 * @author Nate Lilla
 * @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 * @edu.uwp.cs.242.section 001
 * @edu.uwp.cs.242.assignment 4
 * @bugs none
 */
public class Recursion {

    /**
     * This is a practice main method. It will be commented out for the program.
     */
//    public static void main(String [] args) {
//        // String cleaner
//        // First: Should return 'testing'.
//        // Second: Should return 'newspaper'.
//        String a1 = stringClean("teestinng");
//        String a2 = stringClean("newwwwsppaaapeer");
//        System.out.println(a1);
//        System.out.println(a2);
//        System.out.println();
//
//        // Count digit
//        // First: Should return 2.
//        // Second: Should return 4.
//        int b1 = countDigit(122333, 2);
//        int b2 = countDigit(4444321, 4);
//        System.out.println(b1);
//        System.out.println(b2);
//        System.out.println();
//
//        // Balanced brackets
//        // First: Should return true.
//        // Second: Should return false.
//        boolean c1 = isBalanced("[{}()]");
//        boolean c2 = isBalanced("{]}{])(");
//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println();
//
//        // Split array
//        // First: Should return true.
//        // Second: Should return false.
//        int [] d1 = {2, 3, 5};
//        int [] d2 = {2, 3};
//        System.out.println(splitArray(d1));
//        System.out.println(splitArray(d2));
//        System.out.println();
//
//        // Tricky Hanoi
//        trickyHanoi(1);
//        System.out.println();
//        trickyHanoi(2);
//    }

    /**
     * This method will "clean" an inputted string down to one letter of each character.
     * <p>
     * This method takes in a parameter of type String. Using recursion,it will
     * remove an letter that is repeated more than one time.
     *
     * @param str - the inputted String that is being reduced.
     * @return a String that is the reduced form of the inputted version.
     */
    public static String stringClean (String str) {
        // If string is less than 2, it is not possible to have repeating letters.
        if (str.length() <2)
            return str;

        // Checks each letter to see which ones are repeated.
        if (str.charAt(0) == str.charAt(1))
            return stringClean(str.substring(1));
        else
            return str.charAt(0) + stringClean(str.substring(1));
    }

    /**
     * This method will check the occurrence of two inputted numbers.
     * <p>
     * This method takes two parameters of type int. Using recursion, the method
     * will then check to see how many times each individual number is repeated.
     *
     * @param num - number to be checked by the method.
     * @param digit - the number that is to be counted.
     * @return an integer that is the amount of times a number is repeated.
     */
    public static int countDigit (int num, int digit) {
        // Checks to see if num actually exists
        if (num == 0)
            return 0;

        // Goes through each number to see how many times one may appear.
        int newNum = num % 10;
        if (newNum == digit)
            return 1 + countDigit(num / 10, digit);

        return countDigit(num / 10, digit);
    }

    /**
     * This method will check to see if different symbols are balanced.
     * <p>
     * This method takes one parameter of type String. Using recursion, the method
     * will check to see if bracketed objects are "balanced" or not.
     *
     * @param str - the inputted String of bracketed characters.
     * @return a boolean that is true or false depending on whether or not the String is balanced.
     */
    public static boolean isBalanced(String str) {
        // Checks to see if the String is empty, if so, it will return true seeing it is balanced.
        if (str.length() == 0)
            return true;

        // Checks to see if there are "()" in the String.
        if (str.contains("()"))
            return isBalanced(str.replaceFirst("\\(\\)", ""));

        // Checks to see if there are "[]" in the String.
        if (str.contains("[]"))
            return isBalanced(str.replaceFirst("\\[\\]", ""));

        // Checks to see if there are "{}" in the String.
        if (str.contains("{}"))
            return isBalanced(str.replaceFirst("\\{\\}", ""));

        // Else statement to return false if there are none of these.
        else
            return false;
    }

    /**
     * This method will split different numbers in an array to make their sum even.
     * <p>
     * This method takes in an array of numbers of type int. Using recursion, the method
     * will then split the numbers into different categories, which all have the same sum.
     * If they have the same sum, the method returns true, if not, it returns false.
     *
     * @param array - an array of ints that will be processed and split up.
     * @return a boolean, true if the sums match, false if they do not.
     */
    public static boolean splitArray(int [] array) {
        return splitHelper(0, array, 0, 0);
    }

    /**
     * Helper method to assist splitArray().
     * @return a boolean.
     */
    public static boolean splitHelper(int a, int [] numbers, int b, int c) {
        // Checks to ensure a is longer than the array.
        if (a >= numbers.length)
            return b == c;

        if (splitHelper(a+1, numbers, b+numbers[a], c))
            return true;

        if (splitHelper(a+1, numbers, b, c+numbers[a]))
            return true;

        // Default return statement to return false if none of the if statements are triggered.
        return false;
    }

    /**
     * This method will attempt to move all disks to peg C.
     * <p>
     * This method will take on parameter of type int. Using recursion,
     * the method will then attempt to move all of the "disks" to the C peg.
     *
     * @param disks - an int that will "stack" the ints.
     */
    public static void trickyHanoi (int disks) {
        trickyHelper(disks, 'A', 'B', 'C');
    }

    /**
     * This is a helper method for trickyHanoi()
     * @param disks - an int for the disk amount.
     * @param pegFrom - a char for the peg.
     * @param pegTo - a char for the peg.
     * @param pegUsing - a char for the peg.
     */
    public static void trickyHelper(int disks, char pegFrom, char pegTo, char pegUsing) {
        // If the disks are equal to one, only two moves need to be made.
        if (disks == 1) {
            System.out.println("Move disk from peg " + pegFrom + " to peg " + pegTo);
            System.out.println("Move disk from peg " + pegTo + " to peg " + pegUsing);
        }

        // Else statement for any more than two disks
        else {
            trickyHelper(disks-1, pegFrom, pegTo, pegUsing);
            System.out.println("Move disk from peg " + pegFrom + " to peg " + pegTo);
            System.out.println("Move disk from peg " + pegUsing + " to peg " + pegTo);
            System.out.println("Move disk from peg " + pegTo + " to peg " + pegFrom);
            System.out.println("Move disk from peg " + pegTo + " to peg " + pegUsing);
            trickyHelper(disks-1, pegFrom, pegTo, pegUsing);
        }
    }
}
