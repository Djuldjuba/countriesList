package com.example.country.service.soap;

import com.example.country.config.AppConfig;
import com.example.country.domain.CountryResponse;
import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryUpdateRequest;
import com.example.country.service.CountryService;
import com.example.xml.country.AddCountryRequest;
import com.example.xml.country.GetCountriesResponse;
import com.example.xml.country.GetCountryRequest;
import com.example.xml.country.GetCountryResponse;
import com.example.xml.country.UpdateCountryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CountryEndpoint {

  private final CountryService countryService;

  @Autowired
  public CountryEndpoint(CountryService countryService) {
    this.countryService = countryService;
  }

  @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "getCountriesRequest")
  @ResponsePayload
  public GetCountriesResponse getCountriesRequest() {
    GetCountriesResponse response = new GetCountriesResponse();
    List<CountryResponse> countries = countryService.getAllCountries();
    for (CountryResponse country : countries) {
      com.example.xml.country.CountryResponse xmlCountry = new com.example.xml.country.CountryResponse();
      xmlCountry.setCode(country.code());
      xmlCountry.setName(country.name());
      response.getCountry().add(xmlCountry);
    }
    return response;
  }

  @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "getCountryRequest")
  @ResponsePayload
  public GetCountryResponse getCountryRequest(@RequestPayload GetCountryRequest request) {
    GetCountryResponse response = new GetCountryResponse();
    CountryResponse country = countryService.getCountryByCode(request.getCode());
    com.example.xml.country.CountryResponse xmlCountry = new com.example.xml.country.CountryResponse();
    xmlCountry.setCode(country.code());
    xmlCountry.setName(country.name());
    response.setCountry(xmlCountry);
    return response;
  }

  @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "addCountryRequest")
  @ResponsePayload
  public GetCountryResponse addCountryRequest(@RequestPayload AddCountryRequest request) {
    GetCountryResponse response = new GetCountryResponse();
    com.example.xml.country.CreateCountryRequest xmlInput = request.getInput();
    CreateCountryRequest input = new CreateCountryRequest(
            xmlInput.getCode(),
            xmlInput.getName()
    );
    CountryResponse country = countryService.addCountry(input);
    com.example.xml.country.CountryResponse xmlCountry = new com.example.xml.country.CountryResponse();
    xmlCountry.setCode(country.code());
    xmlCountry.setName(country.name());
    response.setCountry(xmlCountry);
    return response;
  }

  @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "updateCountryRequest")
  @ResponsePayload
  public GetCountryResponse updateCountryRequest(@RequestPayload UpdateCountryRequest request) {
    GetCountryResponse response = new GetCountryResponse();
    String code = request.getCode();
    com.example.xml.country.CountryUpdateRequest xmlInput = request.getInput();
    CountryUpdateRequest input = new CountryUpdateRequest(
            xmlInput.getName()
    );
    CountryResponse country = countryService.updateCountryName(code, input);
    com.example.xml.country.CountryResponse xmlCountry = new com.example.xml.country.CountryResponse();
    xmlCountry.setCode(country.code());
    xmlCountry.setName(country.name());
    response.setCountry(xmlCountry);
    return response;
  }
}