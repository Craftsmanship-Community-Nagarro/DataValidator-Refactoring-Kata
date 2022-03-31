package com.craftmanship.validation;

import java.util.Optional;

import com.craftmanship.ErrorInfo;

public class LastNameFieldValidator implements FieldValidator {
  @Override
  public Optional<ErrorInfo> validate(Integer row, String value) {
    if (!validName(value)) {
      return Optional.of(new ErrorInfo(row, "last name must contain characters"));
    }
    return Optional.empty();
  }

  private boolean validName(String value) {
    return value != null && !value.isBlank() && value.chars().allMatch(Character::isAlphabetic);
  }
}
