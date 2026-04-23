package com.example.country.service;

import com.example.country.domain.CountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.entity.CountryEntity;
import com.example.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryServiceDb implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceDb(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryResponse> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(c -> new CountryResponse(c.getCode(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponse addCountry(CountryRequest request) {
        if (countryRepository.existsById(request.code())) {
            throw new RuntimeException("Country with code " + request.code() + " already exists");
        }

        CountryEntity entity = new CountryEntity();
        entity.setCode(request.code());
        entity.setName(request.name());

        CountryEntity saved = countryRepository.save(entity);
        return new CountryResponse(saved.getCode(), saved.getName());
    }

    @Override
    public CountryResponse updateCountryName(String code, CountryUpdateRequest request) {
        CountryEntity entity = countryRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Country not found with code: " + code));

        if (request.name() != null) {
            entity.setName(request.name());
        }

        CountryEntity updated = countryRepository.save(entity);
        return new CountryResponse(updated.getCode(), updated.getName());
    }
}
