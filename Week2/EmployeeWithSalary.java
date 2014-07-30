package TDSP_Task2_SoftServe;

import java.io.Serializable;

public class EmployeeWithSalary extends Employee {
    private double salary;

    public EmployeeWithSalary(String name, String lastName, double salary) {
        setFirstName(name);
        setLastName(lastName);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + " %-15.2f\n", countIncome());
    }

    @Override
    public double countIncome() {
        return salary;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
