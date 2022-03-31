package com.craftmanship.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.craftmanship.ErrorInfo;

public class BirthdateFieldValidator implements FieldValidator {
  private static boolean validDate(String string) {
    try {
      LocalDate.parse(string);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  @Override
  public Optional<ErrorInfo> validate(Integer row, String value) {
    if (!validDate(value)) {
      return Optional.of(new ErrorInfo(row, String.format("birthdate (%s) can not be parsed", value)));
    }
    return Optional.empty();
  }
}
