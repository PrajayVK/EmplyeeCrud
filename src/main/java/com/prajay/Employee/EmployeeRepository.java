package com.prajay.Employee;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();
    private Long currentId = 0L;

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(++currentId);
            employees.add(employee);
        } else {
            employees.replaceAll(emp -> emp.getId().equals(employee.getId()) ? employee : emp);
        }
        return employee;
    }

    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }
}


