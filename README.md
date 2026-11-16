# Employee Payroll System

Java console application for City University Malaysia's Object Oriented Programming Assignment 2.

## Run locally

From the repository root with JDK 21:

```text
javac -d out src/Employee.java src/SalariedEmployee.java src/HourlyEmployee.java src/PayrollApp.java
java -cp out PayrollApp
```

## Menu

| Option | Action |
| --- | --- |
| 1 | Add a salaried employee with a fixed monthly salary |
| 2 | Add an hourly employee with a rate and hours worked in the current month |
| 3 | List employee IDs, names and employment types |
| 4 | Update the current month's hours for an hourly employee |
| 5 | Show individual and total monthly gross pay in RM |
| 0 | Exit |

Employee IDs are unique and case-insensitive. Use a decimal point for numeric input. Records are kept in memory for the current run, so enter the sample data again after restarting.

## Sample calculation

Add a salaried employee with a monthly salary of RM 3,200.00 and an hourly employee with a rate of RM 18.50 and 80.5 monthly hours. The hourly gross pay is RM 1,489.25 and total gross payroll is RM 4,689.25. If you update monthly hours to 90.5, total gross payroll becomes RM 4,874.25.

The assignment uses gross pay before deductions. Salaried pay is fixed. Hourly pay is rate multiplied by hours, with each employee's gross pay rounded to the nearest sen using half-up rounding. The total adds the individual rounded amounts.

## Classes

- `Employee` defines shared identity fields and an abstract pay contract.
- `SalariedEmployee` and `HourlyEmployee` extend `Employee` and override the pay calculation.
- `PayrollApp` contains the menu and stores both employee types in a `List<Employee>`.
