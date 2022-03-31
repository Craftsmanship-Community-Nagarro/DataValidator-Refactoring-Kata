package com.craftmanship.validation;

import java.math.BigDecimal;
import java.util.Optional;

import com.craftmanship.ErrorInfo;

public class IncomeFieldValidator implements FieldValidator {
  @Override
  public Optional<ErrorInfo> validate(Integer row, String value) {
    if (invalidMoney(value)) {
      return Optional.of(new ErrorInfo(row, String.format("income (%s) can not be parsed", value)));
    }
    return Optional.empty();
  }

  private boolean invalidMoney(String s) {
    try {
      BigDecimal money = new BigDecimal(s);
      if (money.compareTo(BigDecimal.ZERO) == -1) {
        return true;
      }
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }
}
