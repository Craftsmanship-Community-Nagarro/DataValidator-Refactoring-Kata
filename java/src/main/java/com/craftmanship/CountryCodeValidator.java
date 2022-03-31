package com.craftmanship;

import java.util.Objects;

public class CountryCodeValidator implements PersonValidator {
  private final CountryInfoService countryInfoService;

  public CountryCodeValidator(CountryInfoService countryInfoService) {
    this.countryInfoService = countryInfoService;
  }

  @Override
  public ErrorInfo validate(PersonDTO input) {

    if (Objects.isNull(input)) {
      return new ErrorInfo(-1, "Person DTO is null");
    }

    if (this.countryInfoService.getAllCountries().stream().anyMatch(c -> c.equals(input.getCountryCode()))) {
      return null;
    }

    return new ErrorInfo(input.getRow(), String.format("country code (%s) doesn't exist", input.getCountryCode()));
  }
}
