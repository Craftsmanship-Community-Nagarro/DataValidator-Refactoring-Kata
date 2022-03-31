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
        Dictionary<int, Person> data = new Dictionary<int, Person>{
            {1, new Person ("Susi", "Sunshine", "AT", "1971-12-04", "2300.20" )}
        };
        var dataValidator = new DataValidator.DataValidator(new CountryInfoServiceAT());
        var errors = dataValidator.Check(data);


        errors.Should().BeEmpty();
    }

    [Fact]
    public void ShouldContainNoErrorsIfDataIsEmpty()
    {
        Dictionary<int, Person> data = new Dictionary<int, Person>{
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
        Dictionary<int, Person> data = new Dictionary<int, Person>{
            { 1, new Person ("Susi", "", "AT", "1971-12-04", "2300.20" ) },
             { 2, new Person ("", "Sunshine", "AT", "1971-12-04", "2300.20}" ) },
                 { 3, new Person ("A1", "Sunshine", "AT", "1971-12-04", "2300.20}" ) },
                     { 4, new Person ("Susi", "Sunshine", "DE", "1971-12-04", "2300.20") },
                         { 5, new Person ("Susi", "Sunshine", "AT", "not a date", "2300.20" )  },
                             { 6, new Person ("Susi", "Sunshine", "AT", "1971-12-04", "not a BigDecimal") },
                                 { 7, new Person ("Susi", "Sunshine", "AT", "1971-12-04", "-1" ) } };
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
