import java.math.BigDecimal;

/** Shared employee details and the contract for monthly gross pay. */
public abstract class Employee {
    private final String id;
    private String name;

    protected Employee(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Employee ID is required.");
        }
        this.id = id;
        setName(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name is required.");
        }
        this.name = name.strip();
    }

    public abstract String getEmploymentType();

    public abstract BigDecimal calculatePay();
}
