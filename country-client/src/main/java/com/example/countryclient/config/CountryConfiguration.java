package com.example.countryclient.config;

import com.example.countryclient.client.CountrySoapClient;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.xml.country");
        return marshaller;
    }

    @Bean
    public CountrySoapClient countrySoapClient(
            WebServiceTemplateBuilder builder,
            Jaxb2Marshaller marshaller,
            SoapProperties soapProperties) {

        builder = builder.setMarshaller(marshaller).setUnmarshaller(marshaller);

        CountrySoapClient client = new CountrySoapClient();
        client.setWebServiceTemplate(builder.build());
        client.setDefaultUri(soapProperties.getUri());
        return client;
    }
}