package com.lalwaniaditya4.shortner.link.exception;

public class InvalidUrlException extends RuntimeException{
    
    public InvalidUrlException(String message)
    {
        super(message);
    }

    public InvalidUrlException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
