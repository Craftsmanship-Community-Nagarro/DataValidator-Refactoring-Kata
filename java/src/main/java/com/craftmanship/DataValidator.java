package com.craftmanship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.craftmanship.validation.FieldValidator;

public class DataValidator {

	private CountryInfoService countryInfoService;

	public DataValidator(CountryInfoService countryInfoService) {
		this.countryInfoService = countryInfoService;
	}

	public List<ErrorInfo> check(Map<Integer, List<String>> data) {

		List<ErrorInfo> errors = new ArrayList<>();
		data.forEach((row, columns) -> {
			validateRow(errors, row, columns);
		});
		return errors;
	}

	private void validateRow(List<ErrorInfo> errors, Integer row, List<String> columns) {
		if (columns == null || columns.isEmpty()) {
			return;
		}

		Map<Integer, FieldValidator> validatorMap = new HashMap<>();

		validatorMap.put(0, value -> {
			if (!validName(columns.get(0))) {
				return Optional.of(new ErrorInfo(row, "first name must contain characters"));
			}
			return Optional.empty();
		});

		validatorMap.put(1, value -> {
			if (!validName(columns.get(1))) {
				return Optional.of(new ErrorInfo(row, "last name must contain characters"));
			}
			return Optional.empty();
		});

		validatorMap.put(2, value -> {
			if (!validC(columns.get(2))) {
				return Optional.of(new ErrorInfo(row, String.format("country code (%s) doesn't exist", columns.get(2))));
			}
			return Optional.empty();
		});

		validatorMap.put(3, value -> {
			if (!validDate(columns.get(3))) {
				return Optional.of(new ErrorInfo(row, String.format("birthdate (%s) can not be parsed", columns.get(3))));
			}
			return Optional.empty();
		});

		validatorMap.put(4, value -> {
			if (invalidMoney(columns.get(4))) {
				return Optional.of(new ErrorInfo(row, String.format("income (%s) can not be parsed", columns.get(4))));
			}
			return Optional.empty();
		});

		validatorMap.forEach((integer, fieldValidator) -> {
			fieldValidator
					.validate(columns.get(integer))
					.ifPresent(errors::add);
		});
	}

	private boolean validName(String value) {
		return value != null && !value.isBlank() && value.chars().allMatch(Character::isAlphabetic);
	}

	private boolean validC(String string) {
		return countryInfoService.getAllCountries().stream().anyMatch(c -> c.equals(string));
	}

	private static boolean validDate(String string) {
		try {
			LocalDate.parse(string);
		} catch (DateTimeParseException  e) {
			return false;
		}
		return true;
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