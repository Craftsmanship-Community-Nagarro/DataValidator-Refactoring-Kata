using System.Collections;
using System.Collections.Generic;
using ApprovalTests;
using ApprovalTests.Reporters;
using DataValidator;
using FluentAssertions;
using Xunit;

namespace DataValidatorTest;

public class DataValidatorTest
{

    [Fact]
    public void ShouldContainNoErrorsIfDataIsValid()
    {
        Dictionary<int, List<string>> data = new Dictionary<int, List<string>>{
            {1, new List<string> {"Susi", "Sunshine", "AT", "1971-12-04", "2300.20" }}
        };
        var dataValidator = new DataValidator.DataValidator(new CountryInfoServiceAT());
        var errors = dataValidator.Check(data);


        errors.Should().BeEmpty();
    }

    [Fact]
    public void ShouldContainNoErrorsIfDataIsEmpty()
    {
        Dictionary<int, List<string>> data = new Dictionary<int, List<string>>{
            {1, new List<string>() },
            {2, null}
        };
        var dataValidator = new DataValidator.DataValidator(new CountryInfoServiceAT());
        var errors = dataValidator.Check(data);


        errors.Should().BeEmpty();
    }

    [Fact]
    [UseReporter(typeof(VisualStudioReporter))]
    public void ShouldContainErrorsForEachInvalidEntry()
    {
        Dictionary<int, List<string>> data = new Dictionary<int, List<string>>{
            { 1, new List<string> {"Susi", "", "AT", "1971-12-04", "2300.20" } },
             { 2, new List<string> {"", "Sunshine", "AT", "1971-12-04", "2300.20}" } },
                 { 3, new List<string> {"A1", "Sunshine", "AT", "1971-12-04", "2300.20}" } },
                     { 4, new List<string> {"Susi", "Sunshine", "DE", "1971-12-04", "2300.20"} },
                         { 5, new List<string> { "Susi", "Sunshine", "AT", "not a date", "2300.20" }  },
                             { 6, new List<string> {"Susi", "Sunshine", "AT", "1971-12-04", "not a BigDecimal"} },
                                 { 7, new List<string> {"Susi", "Sunshine", "AT", "1971-12-04", "-1" } } };
        var dataValidator = new DataValidator.DataValidator(new CountryInfoServiceAT());
        var errors = dataValidator.Check(data);

        string errorMessage = "";


        foreach (ErrorInfo info in errors)
        {
            errorMessage = errorMessage + info.Message() + "\n";
        }


        Approvals.Verify(errorMessage);
    }

}

public class CountryInfoServiceAT : CountryInfoService
{
    public override IEnumerable GetAllCountries()
    {
        return new List<string> { "AT" };
    }
}
