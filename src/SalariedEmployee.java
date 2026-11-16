import java.math.BigDecimal;
import java.math.RoundingMode;

/** An employee with a fixed monthly salary. */
public class SalariedEmployee extends Employee {
    private BigDecimal monthlySalary;

    public SalariedEmployee(String id, String name, BigDecimal monthlySalary) {
        super(id, name);
        setMonthlySalary(monthlySalary);
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public final void setMonthlySalary(BigDecimal monthlySalary) {
        if (monthlySalary == null || monthlySalary.signum() < 0) {
            throw new IllegalArgumentException("Monthly salary must be non-negative.");
        }
        this.monthlySalary = monthlySalary;
    }

    @Override
    public String getEmploymentType() {
        return "Salaried";
    }

    @Override
    public BigDecimal calculatePay() {
        return getMonthlySalary().setScale(2, RoundingMode.HALF_UP);
    }
}
