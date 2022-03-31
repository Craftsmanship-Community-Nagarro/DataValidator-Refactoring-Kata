package com.craftmanship;

import java.math.BigDecimal;
import java.util.Objects;

public class MoneyValidator implements PersonValidator {
  @Override
  public ErrorInfo validate(PersonDTO input) {
    if (Objects.isNull(input)) {
      return new ErrorInfo(-1, "Person DTO is null");
    }

    try {
      BigDecimal money = new BigDecimal(input.getIncome());
      if (money.compareTo(BigDecimal.ZERO) == -1) {
        return new ErrorInfo(input.getRow(), String.format("income (%s) can not be parsed", input.getIncome()));
      }
      return null;
    } catch (NumberFormatException e) {
      return new ErrorInfo(input.getRow(), String.format("income (%s) can not be parsed", input.getIncome()));
    }
  }
}
