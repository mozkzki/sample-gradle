package sample.junit5;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee> list;

    public EmployeeService() {
        this.list = new ArrayList<Employee>();
        this.list.add(new Employee("Eiji", "Yoshikawa"));
    }

    public Employee findById(int id) {
        return new Employee("Taro", "Hoge");
    }

    public List<Employee> findByFirstNameStartingWith(String search) {
        return this.list;
    }
}