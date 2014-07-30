package TDSP_Task2_SoftServe;

import sun.org.mozilla.javascript.internal.ObjToIntMap;

import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        Set<Employee> employees = new TreeSet<Employee>();

//        if ( Files.exists(Paths.get("F:\\Employees.out")) ) {
//            initEmployeeStaticField();
//        }

        displayMenu(employees);

        scanner.close();
    }

//    private static void initEmployeeStaticField() throws IOException {
//        ObjectInputStream objectReader = new ObjectInputStream(new FileInputStream("F:\\Employees.out"));
//        Employee.deserializeCount(objectReader);
//        objectReader.close();
//    }

    private static void displayMenu(Set<Employee> employees) throws IOException {

        //Any initial number different from 1, 2, 3, 4 and 0
        int input = 6;

        while ( input != 0 ) {
            System.out.println("Enter 1 if you want to read data from the file.");
            System.out.println("Enter 2 if you want to write data to the file.");
            System.out.println("Enter 3 if you want to add data about new employee manually.");
            System.out.println("Enter 4 if you want to delete some employee.");
            System.out.println("Enter 5 if you have created employees list in order to perform б) и в) " +
                    "items of the task");
            System.out.println("And finally if you want to exit, please enter 0.");
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    employees = readEmployees();
                    printEmployees(employees);
                    break;
                case 2:
                    writeEmployees(employees);
                    System.out.println("\nThe employees catalog was saved into the <Employees.out> file.\n");
                    break;
                case 3:
                    addEmployees(employees);
                    break;
                case 4:
                    System.out.println("\nEnter an employee last name that you want to remove:");
                    String lastName = scanner.next();
                    System.out.println("Enter an employee first name that you want to remove:");
                    String firstName = scanner.next();
                    System.out.println("Enter a salary of this person:");
                    Double salary = scanner.nextDouble();
                    System.out.println();
                    removeEmployee(employees, lastName, firstName, salary);
                    break;
                case 5:
                    someTasks(employees);
                    break;
                default:
            }
        }
    }

    private static void someTasks(Set<Employee> employees) {
        //Пункт б) задачи
        //Если файла нет то нужно создать добавлением записей и собственно затем записать эти данные в файл
        {
            Iterator iterator = employees.iterator();
            System.out.println("First 5 employees:");
            for ( int i = 0; i < 5 && iterator.hasNext(); i++ ) {
                Employee emp = ((Employee) iterator.next());
                System.out.println(emp.getLastName() + " " + emp.getFirstName());
            }
        }
        //Пункт в) задачи
        {
            Iterator iterator = ((TreeSet<Employee>) employees).descendingIterator();
            System.out.println("Last 3 IDs:");
            for ( int i = 0; i < 3 && iterator.hasNext(); i++ ) {
                Employee emp = ((Employee) iterator.next());
                System.out.println(emp.getId());
            }
        }
    }

    //Метод удаляет сотрудника по фамилии и имени
    private static void removeEmployee(Set<Employee> employees, String lastName, String firstName, double salary) {
        employees.remove(new EmployeeWithSalary(firstName, lastName, salary));
    }

    //Метод печатает в консоль всех сотрудников
    private static void printEmployees(Set<Employee> employees) {

        System.out.format("\n%-4s %-20s %-15s %-15s\n\n", "ID", "Last Name", "First Name", "Monthly Salary");
        for ( Employee employee : employees )
            System.out.println(employee);

    }

    //Метод добавляет одного сотрудника
    public static void addEmployees(Set<Employee> employees)
    {

        String firstName = null;
        String lastName = null;
        double wageOrSalary = 0;
        String forCheckType = null;

        System.out.println("\nEnter a last name:");
        lastName = scanner.next();
        System.out.println("Enter a first name:");
        firstName = scanner.next();

        //Определяет является ли сотрудник с месячной зарплатой или оплата по часовая
        //Если часовая, вводим - hourly
        //Если месячная, вводим - monthly
        System.out.println("Does this person have an hourly wage or monthly? Please, type \"hourly\" or \"monthly\"");
        forCheckType = scanner.next();
        System.out.println("Enter an hourly wage or monthly salary:");
        wageOrSalary = scanner.nextDouble();
        System.out.println();

        if (forCheckType.equals("monthly"))
            employees.add(new EmployeeWithSalary(firstName, lastName, wageOrSalary));
        else
            employees.add(new EmployeeWithWage(firstName, lastName, wageOrSalary));

    }

    //Метод читает и восстанавливает коллекцию с сотрудниками из файла Employees.out
    public static Set<Employee> readEmployees() throws IOException {
        Set<Employee> employees = null;

        FileInputStream fileReader = null;
        ObjectInputStream objectReader = null;
        try {
            fileReader = new FileInputStream("F:\\Employees.out");
            objectReader = new ObjectInputStream(fileReader);

            //Отдельная десериализация для статического поля:
            Employee.deserializeCount(objectReader);

            employees = (Set<Employee>) objectReader.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Something wrong with file! Access failed!\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            objectReader.close();
            fileReader.close();
        }

        return employees;
    }

    //Метод записывает коллекцию состоящую из сотрудников в файл Employees.out
//    @SuppressWarnings("unchecked")
    public static void writeEmployees(Set<Employee> employees) throws IOException {
        FileOutputStream fileWriter = null;
        ObjectOutputStream objectWriter = null;

        try {
            fileWriter = new FileOutputStream("F:\\Employees.out");
            objectWriter = new ObjectOutputStream(fileWriter);

            //Отдельная сериализация для статического поля:
            Employee.serializeCount(objectWriter);

            objectWriter.writeObject(employees);
        } catch (FileNotFoundException e) {
            System.err.println("Something wrong with file! Access failed!\n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            objectWriter.flush();
            objectWriter.close();
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
