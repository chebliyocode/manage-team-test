package com.manage.team.domain.error;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
