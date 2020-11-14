package com.msh.WorkoutGameClient.exceptions;

public class NegativeNumberException extends Exception {
    public NegativeNumberException() {
        super("Only positive numbers allowed!");
    }
}
