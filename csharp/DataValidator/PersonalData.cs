namespace DataValidator;

public record PersonalData
(
   string FirstName,

   string LastName,

   string CountryCode,

   string Birthdate,

   string Income
);


//public void Deconstruct(out string? firstName, out string? lastName, out string? countryCode, out DateOnly? birthdate, out decimal income) => 
//     (firstName, lastName, countryCode, birthdate, income) = (FirstName, LastName, CountryCode, Birthdate, Income);

