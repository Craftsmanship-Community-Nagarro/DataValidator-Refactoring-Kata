package com.craftmanship;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateValidator implements PersonValidator {
  @Override
  public ErrorInfo validate(PersonDTO input) {

    if (Objects.isNull(input)) {
      return new ErrorInfo(-1, "Person DTO is null");
    }
    try {
      LocalDate.parse(input.getDate());
      return null;
    } catch (DateTimeParseException e) {
      return new ErrorInfo(input.getRow(), String.format("birthdate (%s) can not be parsed", input.getDate()));
    }

  }
}
