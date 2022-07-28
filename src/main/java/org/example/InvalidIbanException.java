package org.example;

public class InvalidIbanException extends Exception{
    public InvalidIbanException(String message){
        super(message);
    }
    public InvalidIbanException(String message, Throwable cause){
        super(message, cause);
    }
}
