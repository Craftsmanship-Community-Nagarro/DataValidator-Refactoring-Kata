package com.craftmanship.validation;

import java.util.Optional;

import com.craftmanship.ErrorInfo;

public interface FieldValidator {
  Optional<ErrorInfo> validate(Integer row, String value);
}
