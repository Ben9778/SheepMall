package com.blackfiresoft.sheepmall.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3833766595996470836L;
    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

}
