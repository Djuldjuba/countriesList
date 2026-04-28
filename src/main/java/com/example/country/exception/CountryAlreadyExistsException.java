package com.example.country.exception;

public class CountryAlreadyExistsException extends RuntimeException {
    public CountryAlreadyExistsException(String code) {
        super("Country with code " + code + " already exists");
    }
}
