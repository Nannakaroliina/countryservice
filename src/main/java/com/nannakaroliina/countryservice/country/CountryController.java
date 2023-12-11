package com.nannakaroliina.countryservice.country;
import com.nannakaroliina.countryservice.exception.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getAllCountries() throws JsonException {
        return countryService.getAllCountries();
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<DetailedCountry> getCountryDetails(@PathVariable("name") String name) throws JsonException {
        return countryService.getCountryInformation(name);
    }

}
