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

    public IList<ErrorInfo> Check(Dictionary<int, List<string>>? data)
    {
        var errors = new List<ErrorInfo>();
        foreach (KeyValuePair<int, List<string>> entryKey in data!)
        {
            int row = entryKey.Key;
            List<string> columns = entryKey.Value;
            if (columns != null && columns.Any())
            {
                if (!ValidName(columns[0]))
                {
                    errors.Add(new ErrorInfo(row, "first name must contain characters"));
                }

                if (!ValidName(columns[1]))
                {
                    errors.Add(new ErrorInfo(row, "last name must contain characters"));
                }

                if (!ValidCountry(columns[2]))
                {
                    errors.Add(new ErrorInfo(row, string.Format("country code {0} doesn't exist", columns[2])));
                }

                if (!ValidDate(columns[3]))
                {
                    errors.Add(new ErrorInfo(row, string.Format("birthdate {0} can not be parsed", columns[3])));
                }

                if (!ValidMoney(columns[4]))
                {
                    errors.Add(new ErrorInfo(row, string.Format("income {0} can not be parsed", columns[4])));
                }
            }
        }

        return errors;
    }

    public IList<ErrorInfo> Check(Dictionary<int, PersonalData>? data)
    {
        var errors = new List<ErrorInfo>();
        foreach (KeyValuePair<int, PersonalData> entryKey in data!)
        {
            int row = entryKey.Key;
            PersonalData personalData = entryKey.Value;
            if (personalData != null)
            {
                if (!ValidName(personalData.FirstName))
                {
                    errors.Add(new ErrorInfo(row, "first name must contain characters"));
                }

                if (!ValidName(personalData.LastName))
                {
                    errors.Add(new ErrorInfo(row, "last name must contain characters"));
                }

                if (!ValidCountry(personalData.CountryCode))
                {
                    errors.Add(new ErrorInfo(row, string.Format("country code {0} doesn't exist", personalData.CountryCode)));
                }

                if (!ValidDate(personalData.Birthdate))
                {
                    errors.Add(new ErrorInfo(row, string.Format("birthdate {0} can not be parsed", personalData.Birthdate)));
                }

                if (!ValidMoney(personalData.Income))
                {
                    errors.Add(new ErrorInfo(row, string.Format("income {0} can not be parsed", personalData.Income)));
                }
            }
        }

        return errors;
    }

    private bool ValidMoney(string v)
    {
        try
        {
            var money = Decimal.Parse(v, new CultureInfo("en-US"));
            if (money != -1)
            {
                return true;
            }
            return false;
        }
        catch (FormatException)
        {
            return false;
        }
    }

    private bool ValidDate(string string1)
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

    private bool ValidCountry(string string1)
    {
        var allCountries = CountryInfoService.GetAllCountries();
        foreach (string country in allCountries)
        {
            if (country == string1)
            {
                return true;
            }
        }

        return false;
    }

    private bool ValidName(string value)
    {
        return value != null && !String.IsNullOrEmpty(value) && value.Any(char.IsLetter);
    }
}
