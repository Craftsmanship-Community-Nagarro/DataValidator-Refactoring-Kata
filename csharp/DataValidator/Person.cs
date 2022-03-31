using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataValidator
{
    public record Person (string FirstName, string LastName, string CountryCode, string Birthday, string Income);
}
