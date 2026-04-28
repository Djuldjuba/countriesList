package com.example.country.service;

import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;

import java.util.List;

public interface CountryService {

    List<CountryResponse> getAllCountries();

    CountryResponse addCountry(CreateCountryRequest request);

    CountryResponse updateCountryName(String code, CountryUpdateRequest request);
}
