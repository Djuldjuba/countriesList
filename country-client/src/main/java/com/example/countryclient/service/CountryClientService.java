package com.example.countryclient.service;

import com.example.countryclient.client.CountrySoapClient;
import com.example.xml.country.CountryResponse;
import com.example.xml.country.GetCountriesResponse;
import com.example.xml.country.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryClientService {

    private static final Logger log = LoggerFactory.getLogger(CountryClientService.class);

    private final CountrySoapClient soapClient;

    @Autowired
    public CountryClientService(CountrySoapClient soapClient) {
        this.soapClient = soapClient;
    }

    public void printAllCountries() {
        try {
            GetCountriesResponse response = soapClient.getAllCountries();
            if (response.getCountry() != null && !response.getCountry().isEmpty()) {
                log.info("Список всех стран:");
                for (CountryResponse country : response.getCountry()) {
                    log.info("Код: {}, Название: {}", country.getCode(), country.getName());
                }
                log.info("Всего стран: {}", response.getCountry().size());
            } else {
                log.warn("Список стран пуст");
            }
        } catch (Exception e) {
            log.error("Ошибка при получении списка стран", e);
        }
    }

    public void printCountryByCode(String code) {
        try {
            GetCountryResponse response = soapClient.getCountryByCode(code);
            CountryResponse country = response.getCountry();
            if (country != null) {
                log.info("Страна с кодом {} ", code);
                log.info("Код: {}, Название: {}", country.getCode(), country.getName());
            } else {
                log.warn("Страна с кодом {} не найдена", code);
            }
        } catch (Exception e) {
            log.error("Ошибка при получении страны с кодом: {}", code, e);
        }
    }

    public void addCountry(String code, String name) {
        try {
            soapClient.addCountry(code, name);
            log.info("Страна {} ({}) успешно добавлена", name, code);
        } catch (Exception e) {
            log.error("Ошибка при добавлении страны {} ({}): {}", name, code, e.getMessage());
        }
    }

    public void updateCountry(String code, String newName) {
        try {
            soapClient.updateCountry(code, newName);
            log.info("Страна с кодом {} обновлена на {}", code, newName);
        } catch (Exception e) {
            log.error("Ошибка при обновлении страны с кодом {}: {}", code, e.getMessage());
        }
    }
}