package com.nannakaroliina.countryservice.country;

import com.fasterxml.jackson.databind.JsonNode;
import com.nannakaroliina.countryservice.exception.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger logger = LogManager.getLogger(CountryServiceImpl.class);
    private static final String BASE_URL = "https://restcountries.com/v3.1/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Country> getAllCountries() throws JsonException {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(BASE_URL + "all", JsonNode.class);
            JsonNode arrayNode = response.getBody();
            List<Country> countries = new ArrayList<>();
            if (arrayNode != null) {
                arrayNode.forEach(json -> countries.add(
                        new Country(json.get("name").get("common").asText(),
                                    json.get("cca2").asText())));
            }
            return countries;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error fetching all countries: ", e);
            throw new JsonException("Error processing JSON: " + e.getMessage());
        }
    }

    @Override
    public Optional<DetailedCountry> getCountryInformation(String name) throws JsonException {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(BASE_URL + "name/" + name, JsonNode.class);
            JsonNode arrayNode = response.getBody();
            if (arrayNode != null) {
                return arrayNode.elements().hasNext() ? Optional.of(arrayNode.elements().next()).map(json ->
                        new DetailedCountry(
                                json.path("name").path("common").asText(),
                                json.path("cca2").asText(),
                                json.path("capital").has(0) ? json.get("capital").get(0).asText() : "",
                                json.path("population").asInt(),
                                json.path("flags").path("png").asText()
                        )
                ) : Optional.empty();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error fetching country details: ", e);
            throw new JsonException("Error processing JSON: " + e.getMessage());
        }
        return Optional.empty();
    }
}
