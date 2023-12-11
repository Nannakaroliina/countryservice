package com.nannakaroliina.countryservice.country;

import com.nannakaroliina.countryservice.exception.JsonException;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> getAllCountries() throws JsonException;
    Optional<DetailedCountry> getCountryInformation(String name) throws JsonException;
}
