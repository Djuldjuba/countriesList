package com.example.country.service;

import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.entity.CountryEntity;
import com.example.country.exception.CountryAlreadyExistsException;
import com.example.country.exception.CountryNotFoundException;
import com.example.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryResponse> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(c -> new CountryResponse(c.getCode(), c.getName()))
                .toList();
    }

    @Transactional
    public CountryResponse addCountry(CreateCountryRequest request) {
        if (countryRepository.existsById(request.code())) {
            throw new CountryAlreadyExistsException(request.code());
        }

        CountryEntity entity = new CountryEntity();
        entity.setCode(request.code());
        entity.setName(request.name());

        CountryEntity saved = countryRepository.save(entity);
        return new CountryResponse(saved.getCode(), saved.getName());
    }

    @Transactional
    public CountryResponse updateCountryName(String code, CountryUpdateRequest request) {
        CountryEntity entity = countryRepository.findById(code)
                .orElseThrow(() -> new CountryNotFoundException(code));

        entity.setName(request.name());

        CountryEntity updated = countryRepository.save(entity);
        return new CountryResponse(updated.getCode(), updated.getName());
    }
}
