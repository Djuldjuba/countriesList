package com.example.country.controller;

import com.example.country.domain.CountryResponse;
import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQlCountryController {

    private final CountryService countryService;

    @Autowired
    public GraphQlCountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public List<CountryResponse> countries() {
        return countryService.getAllCountries();
    }

    @QueryMapping
    public CountryResponse country(@Argument String code) {
        return countryService.getCountryByCode(code);
    }

    @MutationMapping
    public CountryResponse addCountry(@Argument @Valid CreateCountryRequest input) {
        return countryService.addCountry(input);
    }

    @MutationMapping
    public CountryResponse updateCountry(@Argument String code,
                                         @Argument @Valid CountryUpdateRequest input) {
        return countryService.updateCountryName(code, input);
    }
}