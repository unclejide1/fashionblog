package com.example.fashionblog.model.exception;


import org.springframework.http.HttpStatus;

public class CustomExceptionClass extends RuntimeException {

    private static  final long serialVersionUID = 1L;

    private  String message;

    private HttpStatus status;

    public CustomExceptionClass(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public HttpStatus getStatus() {
        return status;
    }
}
