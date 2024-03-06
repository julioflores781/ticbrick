package com.test.tic.brick.exception;

public class IllegalArgumentExceptionHandler extends RuntimeException {
    public IllegalArgumentExceptionHandler(String message) {
        super(message);
    }
}
