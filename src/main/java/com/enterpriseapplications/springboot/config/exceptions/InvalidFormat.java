package com.enterpriseapplications.springboot.config.exceptions;

public class InvalidFormat extends RuntimeException {
    public InvalidFormat(String message) {
        super(message);
    }
}
