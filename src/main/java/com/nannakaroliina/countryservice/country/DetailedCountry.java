package com.nannakaroliina.countryservice.country;

public class DetailedCountry extends Country {
    private String capital;
    private Integer population;
    private String flagFileUrl;

    public DetailedCountry(String name, String countryCode, String capital,
                   int population, String flagFileUrl) {
        super(name, countryCode);
        this.capital = capital;
        this.population = population;
        this.flagFileUrl = flagFileUrl;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getFlagFileUrl() {
        return flagFileUrl;
    }

    public void setFlagFileUrl(String flagFileUrl) {
        this.flagFileUrl = flagFileUrl;
    }

}
