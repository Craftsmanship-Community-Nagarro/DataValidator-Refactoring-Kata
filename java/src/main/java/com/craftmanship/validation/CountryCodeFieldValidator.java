package com.craftmanship.validation;

import java.util.Optional;

import com.craftmanship.CountryInfoService;
import com.craftmanship.ErrorInfo;
import com.craftmanship.validation.FieldValidator;

public class CountryCodeFieldValidator implements FieldValidator {
  private final CountryInfoService countryInfoService;

  public CountryCodeFieldValidator(CountryInfoService countryInfoService) {
    this.countryInfoService = countryInfoService;
  }

  @Override
  public Optional<ErrorInfo> validate(Integer row, String value) {
    if (!validC(value)) {
      return Optional.of(new ErrorInfo(row, String.format("country code (%s) doesn't exist", value)));
    }
    return Optional.empty();
  }

  private boolean validC(String string) {
    return countryInfoService.getAllCountries().stream().anyMatch(c -> c.equals(string));
  }
}
