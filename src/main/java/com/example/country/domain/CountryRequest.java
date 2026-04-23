package com.example.country.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CountryRequest(
        @NotBlank(message = "Code is required")
        @Pattern(regexp = "^[A-Z]{2}$", message = "Code must be 2 uppercase letters")
        String code,

        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name
) {}
