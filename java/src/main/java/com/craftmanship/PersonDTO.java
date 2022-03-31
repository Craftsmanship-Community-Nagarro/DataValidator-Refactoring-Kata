package com.craftmanship;

public class PersonDTO {

  private final Integer row;
  private final String firstName;
  private final String lastName;
  private final String countryCode;
  private final String date;
  private final String income;

  private PersonDTO(final Builder builder) {
    this.row = builder.row;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.countryCode = builder.countryCode;
    this.date = builder.date;
    this.income = builder.income;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getDate() {
    return date;
  }

  public String getIncome() {
    return income;
  }

  public Integer getRow() {
    return row;
  }

  public static class Builder {
    private Integer row;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String date;
    private String income;

    public Builder row(final Integer row) {
      this.row = row;
      return this;
    }

    public Builder firstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder countryCode(final String countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public Builder date(final String date) {
      this.date = date;
      return this;
    }

    public Builder income(final String income) {
      this.income = income;
      return this;
    }

    public PersonDTO build() {
      return new PersonDTO(this);
    }
  }
}
