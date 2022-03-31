package com.craftmanship;

import java.util.Objects;

public class LastNameValidator implements PersonValidator {
  @Override
  public ErrorInfo validate(PersonDTO input) {

    if (Objects.isNull(input)) {
      return new ErrorInfo(-1, "Person DTO is null");
    }

    final String value = input.getLastName();
    if (value != null && !value.isBlank() && value.chars().allMatch(Character::isAlphabetic)) {
      return null;
    }

    //No error
    return new ErrorInfo(input.getRow(), "last name must contain characters");
  }
}
