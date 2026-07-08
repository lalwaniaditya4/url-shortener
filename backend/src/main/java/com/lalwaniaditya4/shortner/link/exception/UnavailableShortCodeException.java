package com.lalwaniaditya4.shortner.link.exception;

public class UnavailableShortCodeException extends RuntimeException{
    public UnavailableShortCodeException(String message)
    {
        super(message);
    }
}
