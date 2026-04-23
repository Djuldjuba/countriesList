package com.example.country.controller;

import com.example.country.domain.CountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.service.CountryServiceDb;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryServiceDb countryService;

    @Autowired
    public CountryController(CountryServiceDb countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PostMapping
    public ResponseEntity<CountryResponse> addCountry(@Valid @RequestBody CountryRequest request) {
        CountryResponse response = countryService.addCountry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable String code,
            @Valid @RequestBody CountryUpdateRequest request) {
        CountryResponse response = countryService.updateCountryName(code, request);
        return ResponseEntity.ok(response);
    }

}
