package com.craftmanship;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataValidator {

  private final CountryInfoService countryInfoService;
  private final List<PersonValidator> validators;

  public DataValidator(CountryInfoService countryInfoService, List<PersonValidator> validators) {
    this.countryInfoService = countryInfoService;
    this.validators = validators;
  }

  public List<ErrorInfo> checkDTO(DataDTO dataDTO) {
    return dataDTO.getPersonDTOList().stream().flatMap(personDTO -> validateOnPersonDTO(personDTO).stream()).collect(Collectors.toList());
  }

  private List<ErrorInfo> validateOnPersonDTO(PersonDTO personDTO) {
    return validators.stream().map(v -> v.validate(personDTO)).filter(Objects::nonNull).collect(Collectors.toList());
  }

  public List<ErrorInfo> check(Map<Integer, List<String>> data) {
    return checkDTO(new DataDTO(extractDataFromMap(data)));
  }

  private List<PersonDTO> extractDataFromMap(Map<Integer, List<String>> data) {
    return data.entrySet()
            .stream()
            .filter(integerListEntry -> Objects.nonNull(integerListEntry.getValue()))
            .filter(integerListEntry -> !integerListEntry.getValue().isEmpty())
            .map(integerListEntry -> {

              List<String> columns = integerListEntry.getValue();

              return new PersonDTO.Builder()
                      .row(integerListEntry.getKey())
                      .firstName(columns.get(0))
                      .lastName(columns.get(1))
                      .countryCode(columns.get(2))
                      .date(columns.get(3))
                      .income(columns.get(4))
                      .build();

            }).collect(Collectors.toList());
  }


}