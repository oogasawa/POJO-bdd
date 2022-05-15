package net.laddercode.pojobdd;


public class BddUtil {

    public static boolean assertTrue(String expectation, String answer) {

        if (expectation.equals(answer)) {
            System.out.println("````");
            System.out.println(answer);
            System.out.println("````");

            return true;
        }
        else {
            System.out.println("````");
            System.out.println("expectation:");
            System.out.println(expectation);
            System.out.println("");
            System.out.println("answer:");
            System.out.println(answer);
            System.out.println("````");

            return false;
        }
    }


    public static boolean assertTrue(String expectation, String answer, String message) {
        System.out.println(message);
        return assertTrue(expectation, answer);
    }


}
