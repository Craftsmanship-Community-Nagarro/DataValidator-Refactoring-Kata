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

	private final CountryInfoService countryInfoService;
	private final Map<Integer, FieldValidator> validatorMap;

	public DataValidator(CountryInfoService countryInfoService) {
		this.countryInfoService = countryInfoService;
		this.validatorMap = createValidators();
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


		validatorMap.forEach((integer, fieldValidator) -> {
			fieldValidator
					.validate(row, columns.get(integer))
					.ifPresent(errors::add);
		});
	}

	private Map<Integer, FieldValidator> createValidators() {
		Map<Integer, FieldValidator> validatorMap = new HashMap<>();

		validatorMap.put(0, (row, value) -> {
			if (!validName(value)) {
				return Optional.of(new ErrorInfo(row, "first name must contain characters"));
			}
			return Optional.empty();
		});

		validatorMap.put(1, (row, value) -> {
			if (!validName(value)) {
				return Optional.of(new ErrorInfo(row, "last name must contain characters"));
			}
			return Optional.empty();
		});

		validatorMap.put(2, (row, value) -> {
			if (!validC(value)) {
				return Optional.of(new ErrorInfo(row, String.format("country code (%s) doesn't exist", value)));
			}
			return Optional.empty();
		});

		validatorMap.put(3, (row, value) -> {
			if (!validDate(value)) {
				return Optional.of(new ErrorInfo(row, String.format("birthdate (%s) can not be parsed", value)));
			}
			return Optional.empty();
		});

		validatorMap.put(4, (row, value) -> {
			if (invalidMoney(value)) {
				return Optional.of(new ErrorInfo(row, String.format("income (%s) can not be parsed", value)));
			}
			return Optional.empty();
		});
		return validatorMap;
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