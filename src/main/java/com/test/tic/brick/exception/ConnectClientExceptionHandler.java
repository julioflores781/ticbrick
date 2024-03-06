package com.test.tic.brick.exception;

public class ConnectClientExceptionHandler extends RuntimeException {

    public ConnectClientExceptionHandler(String message, String exMessage) {
        super(message);
    }

}
