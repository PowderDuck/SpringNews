package com.management.news.exception;

public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(String message)
    {
        super(message);
    }
}
