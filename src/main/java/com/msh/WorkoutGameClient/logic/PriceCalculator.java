package com.msh.WorkoutGameClient.logic;

public class PriceCalculator {

    /*public static double exponent = 1;

    public static int calculate(int ownedNumber) {
        return (int) Math.round(Math.pow(ownedNumber, exponent));
    }

    public static int calculateNext(int ownedNumber) {
        return (int) Math.round(Math.pow(ownedNumber + 1, exponent));
    }*/

    public static double calculateNextN(int ownedNumber, double base, int amount) {
        int n = ownedNumber + 1;
        return Math.round(Math.pow(base, n) * ((Math.pow(base, amount) - 1) / (base - 1)) * 100.0) / 100.0;
    }
}
