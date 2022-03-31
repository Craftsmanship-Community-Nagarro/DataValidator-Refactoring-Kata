package com.craftmanship;

/**
 * Person validator.
 *
 * @since 31.03.2022
 */
public interface PersonValidator {
  /**
   * Validates the input, if it is valid return null, otherwise fill the ErrorInfo object.
   *
   * @param input the DTO for verification, a class per field.
   * @return null if the validation passed, ErrorInfo otherwise
   */
  ErrorInfo validate(PersonDTO input);
}
