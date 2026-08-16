package com.example.countryclient;

import com.example.countryclient.service.CountryClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CountryClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CountryClientApplication.class, args);

        CountryClientService service = context.getBean(CountryClientService.class);

        service.printAllCountries();
        service.printCountryByCode("US");
        service.addCountry("FR", "France");
        service.updateCountry("FR", "French Republic");
        service.printCountryByCode("FR");
    }
}