package TDSP_Task2_SoftServe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Employee implements SalaryPayment, Comparable<Employee>, Serializable {
    private static long count;
    private final long id = ++count;
    private String firstName;
    private String lastName;
//    private double income;

    public abstract double countIncome();

    public long getId() {
        return id;
    }

    public static void serializeCount(ObjectOutputStream oos) throws IOException {
        oos.writeLong(count);
    }

    public static void deserializeCount(ObjectInputStream ois) throws IOException {
        count = ois.readLong();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%-4s %-20s %-15s", id, lastName, firstName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Employee that) {

        int compareToSalary = Double.compare(that.countIncome(), this.countIncome());
        if ( compareToSalary != 0 )
            return compareToSalary;
        int compareToLastName = this.lastName.compareTo(that.lastName);
        if ( compareToLastName != 0 )
            return compareToLastName;
        else
            return this.firstName.compareTo(that.firstName);
    }
}
