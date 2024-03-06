package com.test.tic.brick.exception;

public class JdbcSQLIntegrityConstraintViolationExceptionHandler extends RuntimeException {

    public JdbcSQLIntegrityConstraintViolationExceptionHandler(String message) {
        super(message);
    }
}
