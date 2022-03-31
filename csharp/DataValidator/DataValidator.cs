using System.Collections;
using System.Globalization;

namespace DataValidator;

public class DataValidator
{
    private readonly CountryInfoService CountryInfoService;

    public DataValidator(CountryInfoService countryInfoService)
    {
        this.CountryInfoService = countryInfoService;
    }

    public IList<ErrorInfo> Check(Dictionary<int, Person>? data)
    {
        var errors = new List<ErrorInfo>();
        foreach (KeyValuePair<int, Person> entryKey in data)
        {
            int row = entryKey.Key;
            var person = entryKey.Value;
            if (person != null)
            {
                if (!IsValidName(person.FirstName))
                {
                    errors.Add(new ErrorInfo(row, "first name must contain characters"));
                }

                if (!IsValidName(person.LastName))
                {
                    errors.Add(new ErrorInfo(row, "last name must contain characters"));
                }

                if (!IsValidCountry(person.CountryCode))
                {
                    errors.Add(new ErrorInfo(row, string.Format("country code {0} doesn't exist", person.CountryCode)));
                }

                if (!IsValidDate(person.Birthday))
                {
                    errors.Add(new ErrorInfo(row, string.Format("birthdate {0} can not be parsed", person.Birthday)));
                }

                if (IsInvalidMoney(person.Income))
                {
                    errors.Add(new ErrorInfo(row, string.Format("income {0} can not be parsed", person.Income)));
                }
            }
        }

        return errors;
    }

    private bool IsInvalidMoney(string income)
    {
        try
        {
            var money = Decimal.Parse(income, new CultureInfo("en-US"));
            if (money == -1)
            {
                return true;
            }

            return false;
        }
        catch (FormatException)
        {
            return true;
        }
    }

    private bool IsValidDate(string string1)
    {
        try
        {
            Convert.ToDateTime(string1);
        }
        catch (FormatException)
        {
            return false;
        }

        return true;
    }

    private bool IsValidCountry(string countryInput)
    {
        var allCountries = CountryInfoService.GetAllCountries();
        foreach (string country in allCountries)
        {
            if (country == countryInput)
            {
                return true;
            }
        }

        return false;
    }

    private bool IsValidName(string value)
    {
        return value != null && !string.IsNullOrEmpty(value) && value.Any(char.IsLetter);
    }
}
