package com.example.countryclient.client;

import com.example.xml.country.AddCountryRequest;
import com.example.xml.country.CountryUpdateRequest;
import com.example.xml.country.CreateCountryRequest;
import com.example.xml.country.GetCountriesRequest;
import com.example.xml.country.GetCountriesResponse;
import com.example.xml.country.GetCountryRequest;
import com.example.xml.country.GetCountryResponse;
import com.example.xml.country.UpdateCountryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CountrySoapClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(CountrySoapClient.class);

    public GetCountriesResponse getAllCountries() {
        GetCountriesRequest request = new GetCountriesRequest();
        log.info("Запрос на получение всех стран");

        return (GetCountriesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(""));
    }

    public GetCountryResponse getCountryByCode(String code) {
        GetCountryRequest request = new GetCountryRequest();
        request.setCode(code);
        log.info("Запрос на получение страны с кодом: {}", code);

        return (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(""));
    }

    public void addCountry(String code, String name) {
        AddCountryRequest request = new AddCountryRequest();
        CreateCountryRequest input = new CreateCountryRequest();
        input.setCode(code);
        input.setName(name);
        request.setInput(input);
        log.info("Запрос на добавление страны: {} - {}", code, name);

        getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(""));

        log.info("Страна {} успешно добавлена", name);
    }

    public void updateCountry(String code, String newName) {
        UpdateCountryRequest request = new UpdateCountryRequest();
        request.setCode(code);
        CountryUpdateRequest input = new CountryUpdateRequest();
        input.setName(newName);
        request.setInput(input);
        log.info("Запрос на обновление страны с кодом {} на новое имя: {}", code, newName);

        getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(""));

        log.info("Страна с кодом {} успешно обновлена", code);
    }
}