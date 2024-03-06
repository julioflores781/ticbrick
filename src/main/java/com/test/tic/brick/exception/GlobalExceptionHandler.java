package com.test.tic.brick.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentExceptionHandler.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConnectClientExceptionHandler.class)
    public ResponseEntity<String> handleConnectClientExceptionHandler(ConnectClientExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationExceptionHandler.class)
    public ResponseEntity<String> jdbcSQLIntegrityConstraintViolationExceptionHandler(JdbcSQLIntegrityConstraintViolationExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
