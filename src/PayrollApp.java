import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Runs the console menu and manages the current month's employee records. */
public class PayrollApp {
    private final List<Employee> employees = new ArrayList<>();
    private final Scanner scanner;

    private PayrollApp(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new PayrollApp(scanner).run();
        } catch (NoSuchElementException exception) {
            System.out.println("\nInput closed. Exiting payroll.");
        }
    }

    private void run() {
        while (true) {
            System.out.println("\nEMPLOYEE PAYROLL SYSTEM");
            System.out.println("1. Add salaried employee");
            System.out.println("2. Add hourly employee");
            System.out.println("3. List employees");
            System.out.println("4. Update monthly hours");
            System.out.println("5. Show monthly payroll");
            System.out.println("0. Exit");

            switch (readLine("Choice: ")) {
                case "1":
                    addEmployee(false);
                    break;
                case "2":
                    addEmployee(true);
                    break;
                case "3":
                    printEmployees(false);
                    break;
                case "4":
                    updateHours();
                    break;
                case "5":
                    printEmployees(true);
                    break;
                case "0":
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid menu choice. Try again.");
            }
        }
    }

    private void addEmployee(boolean hourly) {
        String id = readId();
        if (findEmployee(id) != null) {
            System.out.println("That employee ID already exists.");
            return;
        }

        String name = readName();
        Employee employee;
        if (hourly) {
            BigDecimal rate = readDecimal("Hourly rate (RM): ");
            BigDecimal hours = readDecimal("Hours worked this month: ");
            employee = new HourlyEmployee(id, name, rate, hours);
        } else {
            BigDecimal salary = readDecimal("Monthly salary (RM): ");
            employee = new SalariedEmployee(id, name, salary);
        }
        employees.add(employee);
        System.out.println("Employee " + id + " added.");
    }

    private void updateHours() {
        Employee employee = findEmployee(readId());
        if (employee == null) {
            System.out.println("Employee ID not found.");
        } else if (employee instanceof HourlyEmployee) {
            HourlyEmployee hourlyEmployee = (HourlyEmployee) employee;
            hourlyEmployee.setHoursWorked(readDecimal("New hours for this month: "));
            System.out.println("Hours updated for " + employee.getId() + ".");
        } else {
            System.out.println("Only hourly employees have hours to update.");
        }
    }

    private Employee findEmployee(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    private void printEmployees(boolean showPay) {
        if (employees.isEmpty()) {
            System.out.println("No employees recorded.");
            return;
        }

        String row = "%-12s %-30s %-10s";
        String payColumn = " %18s";
        BigDecimal total = BigDecimal.ZERO;
        System.out.println(showPay ? "\nMONTHLY GROSS PAYROLL" : "\nEMPLOYEE LIST");
        System.out.printf(row, "ID", "Name", "Type");
        System.out.printf(showPay ? payColumn + "%n" : "%n", "Gross pay (RM)");

        for (Employee employee : employees) {
            System.out.printf(row, employee.getId(), employee.getName(), employee.getEmploymentType());
            if (showPay) {
                // The runtime class selects the pay formula for this Employee reference.
                BigDecimal pay = employee.calculatePay();
                System.out.printf(payColumn, money(pay));
                total = total.add(pay);
            }
            System.out.println();
        }
        if (showPay) {
            System.out.printf("Employees: %d | Total gross pay: RM %s%n", employees.size(), money(total));
        }
    }

    private String readId() {
        while (true) {
            String id = readLine("Employee ID: ");
            if (id.matches("[A-Za-z0-9-]{1,12}") && id.matches(".*[A-Za-z0-9].*")) {
                return id.toUpperCase(Locale.ROOT);
            }
            System.out.println("Use 1 to 12 letters, digits or hyphens, including a letter or digit.");
        }
    }

    private String readName() {
        while (true) {
            String name = readLine("Employee name: ");
            if (!name.isBlank() && name.length() <= 30 && name.chars().noneMatch(Character::isISOControl)) {
                return name;
            }
            System.out.println("Enter a name from 1 to 30 characters.");
        }
    }

    private BigDecimal readDecimal(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (value.matches("[0-9]{1,6}(\\.[0-9]{1,2})?")) {
                return new BigDecimal(value);
            }
            System.out.println("Enter a non-negative number up to 999999.99 with at most two decimals.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }

    private String money(BigDecimal value) {
        return String.format(Locale.US, "%,.2f", value);
    }
}
