package com.abc;

import java.util.Random;

public class Helper {

    //Max length of account number
    private static final int ACC_LEN = 8;

    //Acceptable characters
    private static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lower = upper.toLowerCase();
    private static String numbers = "0123456789";
    private static String alphaNum = upper + lower + numbers;

    private static Random random = new Random();

    /**
     * Generate a random string of characters from the acceptable character (alphaNum) field.
     *
     * @return random string
     */
    public static String generateAccountNum() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ACC_LEN; i++) {
            sb.append(alphaNum.charAt(random.nextInt(alphaNum.length())));
        }
//        System.out.println(sb.toString());
        return sb.toString();
    }
}
