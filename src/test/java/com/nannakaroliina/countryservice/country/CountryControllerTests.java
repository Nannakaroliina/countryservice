package com.nannakaroliina.countryservice.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CountryController.class)
class CountryControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService service;

    @Test
    void givenCountries_whenGetCountries_thenReturnCountryList() throws Exception {
        Country testCountry = new Country("Country01", "CO1");
        Country testCountry2 = new Country("Country02", "C02");
        List<Country> allEmployees = List.of(testCountry, testCountry2);

        given(service.getAllCountries()).willReturn(allEmployees);

        mvc.perform(get("/countries/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].name").value(testCountry.getName()));
    }

    @Test
    void givenCountryName_whenGetCountry_thenReturnCountry() throws Exception {
        DetailedCountry testCountry = new DetailedCountry("Country", "CO", "Capital"
                , 12345, "http://flag.com");

        given(service.getCountryInformation("Country")).willReturn(Optional.of(testCountry));

        mvc.perform(get("/countries/Country").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(testCountry.getName()));
    }

    @Test
    void givenInvalidCountryName_whenGetCountry_thenReturnEmpty() throws Exception {
        DetailedCountry testCountry = new DetailedCountry("Country", "CO", "Capital"
                , 12345, "http://flag.com");

        given(service.getCountryInformation("Countryy")).willReturn(Optional.of(testCountry));

        mvc.perform(get("/countries/Country").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
