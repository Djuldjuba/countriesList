package com.example.country.controller;

import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.exception.CountryAlreadyExistsException;
import com.example.country.exception.CountryNotFoundException;
import com.example.country.service.CountryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PostMapping
    public ResponseEntity<CountryResponse> addCountry(@Valid @RequestBody CreateCountryRequest request) {
        CountryResponse response = countryService.addCountry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable @NotBlank @Size(min = 2, max = 3) String code,
            @Valid @RequestBody CountryUpdateRequest request) {
        CountryResponse response = countryService.updateCountryName(code, request);
        return ResponseEntity.ok(response);
    }

    // Простой обработчик ошибок прямо в контроллере
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(CountryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CountryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExists(CountryAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
