package com.example.country.service;

import com.example.country.domain.CountryResponse;
import com.example.country.domain.CreateCountryRequest;
import com.example.country.domain.CountryUpdateRequest;
import com.example.grpc.country.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GrpcCountriesService extends CountriesServiceGrpc.CountriesServiceImplBase {

    private final CountryService countryService;
    private final Random random = new Random();

    public GrpcCountriesService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void getCountry(Code request, StreamObserver<CountryResponseProto> responseObserver) {
        try {
            CountryResponse country = countryService.getCountryByCode(request.getId());

            CountryResponseProto response = CountryResponseProto.newBuilder()
                    .setCode(country.code())
                    .setName(country.name())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAllCountries(EmptyRequest request, StreamObserver<CountryListResponse> responseObserver) {
        try {
            List<CountryResponse> countries = countryService.getAllCountries();

            CountryListResponse.Builder responseBuilder = CountryListResponse.newBuilder();
            for (CountryResponse country : countries) {
                CountryResponseProto proto = CountryResponseProto.newBuilder()
                        .setCode(country.code())
                        .setName(country.name())
                        .build();
                responseBuilder.addCountries(proto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponseProto> responseObserver) {
        try {
            CreateCountryRequest createRequest = new CreateCountryRequest(
                    request.getCode(),
                    request.getName()
            );

            CountryResponse country = countryService.addCountry(createRequest);

            CountryResponseProto response = CountryResponseProto.newBuilder()
                    .setCode(country.code())
                    .setName(country.name())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateCountry(UpdateCountryRequest request, StreamObserver<CountryResponseProto> responseObserver) {
        try {
            CountryUpdateRequest updateRequest = new CountryUpdateRequest(request.getName());

            CountryResponse country = countryService.updateCountryName(
                    request.getCode(),
                    updateRequest
            );

            CountryResponseProto response = CountryResponseProto.newBuilder()
                    .setCode(country.code())
                    .setName(country.name())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void randomCountries(CountRequest request, StreamObserver<CountryResponseProto> responseObserver) {
        try {
            List<CountryResponse> allCountries = countryService.getAllCountries();
            int count = request.getCount();

            if (allCountries.isEmpty()) {
                responseObserver.onError(new RuntimeException("No countries available"));
                return;
            }

            for (int i = 0; i < count; i++) {
                CountryResponse randomCountry = allCountries.get(random.nextInt(allCountries.size()));
                CountryResponseProto response = CountryResponseProto.newBuilder()
                        .setCode(randomCountry.code())
                        .setName(randomCountry.name())
                        .build();
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public StreamObserver<CountryRequest> addCountriesStream(
            StreamObserver<AddCountriesResponse> responseObserver) {

        return new StreamObserver<>() {

            private int addedCount = 0;
            private int failedCount = 0;

            @Override
            public void onNext(CountryRequest request) {
                try {
                    String code = request.getCode();
                    String name = request.getName();
                    CreateCountryRequest createRequest = new CreateCountryRequest(code, name);
                    countryService.addCountry(createRequest);
                    addedCount++;

                } catch (Exception e) {
                    failedCount++;
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                AddCountriesResponse response = AddCountriesResponse.newBuilder()
                        .setAddedCount(addedCount)
                        .setFailedCount(failedCount)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}