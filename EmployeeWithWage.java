package TDSP_Task2_SoftServe;

import java.io.Serializable;

public class EmployeeWithWage extends Employee {
    private double wage;

    public EmployeeWithWage(String name, String lastName, double wage) {
        setFirstName(name);
        setLastName(lastName);
        this.wage = wage;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + " %-15.2f\n", countIncome());
    }

    @Override
    public double countIncome() {
        return 20.8 * 8 * wage;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
