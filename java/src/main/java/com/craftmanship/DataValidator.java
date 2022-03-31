package com.craftmanship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craftmanship.validation.BirthdateFieldValidator;
import com.craftmanship.validation.CountryCodeFieldValidator;
import com.craftmanship.validation.FieldValidator;
import com.craftmanship.validation.FirstNameFieldValidator;
import com.craftmanship.validation.IncomeFieldValidator;
import com.craftmanship.validation.LastNameFieldValidator;

public class DataValidator {

	private final CountryInfoService countryInfoService;
	private final Map<Integer, FieldValidator> validatorMap;

	public DataValidator(CountryInfoService countryInfoService) {
		this.countryInfoService = countryInfoService;
		this.validatorMap = createValidators();
	}

	public List<ErrorInfo> check(Map<Integer, List<String>> data) {
		List<ErrorInfo> errors = new ArrayList<>();

		data.forEach((row, columns) -> validateRow(errors, row, columns));

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

		validatorMap.put(0, new FirstNameFieldValidator());

		validatorMap.put(1, new LastNameFieldValidator());

		validatorMap.put(2, new CountryCodeFieldValidator(countryInfoService));

		validatorMap.put(3, new BirthdateFieldValidator());

		validatorMap.put(4, new IncomeFieldValidator());

		return validatorMap;
	}

}