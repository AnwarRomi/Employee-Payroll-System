import java.math.BigDecimal;
import java.math.RoundingMode;

/** An employee paid for hours worked in the current month. */
public class HourlyEmployee extends Employee {
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;

    public HourlyEmployee(String id, String name, BigDecimal hourlyRate, BigDecimal hoursWorked) {
        super(id, name);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public final void setHourlyRate(BigDecimal hourlyRate) {
        if (hourlyRate == null || hourlyRate.signum() < 0) {
            throw new IllegalArgumentException("Hourly rate must be non-negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public final void setHoursWorked(BigDecimal hoursWorked) {
        if (hoursWorked == null || hoursWorked.signum() < 0) {
            throw new IllegalArgumentException("Hours worked must be non-negative.");
        }
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmploymentType() {
        return "Hourly";
    }

    @Override
    public BigDecimal calculatePay() {
        return getHourlyRate().multiply(getHoursWorked()).setScale(2, RoundingMode.HALF_UP);
    }
}
