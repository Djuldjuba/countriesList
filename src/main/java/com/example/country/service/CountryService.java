package com.example.country.service;

import com.example.country.domain.CountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;

import java.util.List;

public interface CountryService {

    List<CountryResponse> getAllCountries();

    CountryResponse addCountry(CountryRequest request);

    CountryResponse updateCountryName(String code, CountryUpdateRequest request);
}
