package com.prajay.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    @Test
    void findAllInitiallyEmpty() {
        List<Employee> employees = repository.findAll();
        assertTrue(employees.isEmpty(), "Repository should be initially empty");
    }

    @Test
    void saveAndFindById() {
        Employee newEmployee = new Employee(null, "Prajay", "Developer", "IT");
        Employee savedEmployee = repository.save(newEmployee);

        assertNotNull(savedEmployee.getId(), "Employee ID should not be null after save");
        assertEquals("Prajay", savedEmployee.getName(), "Name should match the saved employee");

        Optional<Employee> foundEmployee = repository.findById(savedEmployee.getId());
        assertTrue(foundEmployee.isPresent(), "Employee should be found by ID");
        assertEquals("Developer", foundEmployee.get().getRole(), "Role should match the saved employee");
    }

    @Test
    void findAllAfterAddingEmployees() {
        repository.save(new Employee(null, "Prajay", "Developer", "IT"));
        repository.save(new Employee(null, "Milin", "Manager", "HR"));

        List<Employee> employees = repository.findAll();
        assertEquals(2, employees.size(), "Should contain exactly two employees");
    }

    @Test
    void updateEmployee() {
        Employee employee = repository.save(new Employee(null, "Prajay", "Developer", "IT"));
        employee.setName("Yuvan");
        employee.setRole("Senior Developer");
        Employee updatedEmployee = repository.save(employee);

        assertEquals("Yuvan", updatedEmployee.getName(), "Name should be updated");
        assertEquals("Senior Developer", updatedEmployee.getRole(), "Role should be updated");

        Optional<Employee> foundUpdatedEmployee = repository.findById(employee.getId());
        assertEquals("Yuvan", foundUpdatedEmployee.get().getName(), "Updated name should persist in the repository");
    }

    @Test
    void deleteById() {
        Employee employee = repository.save(new Employee(null, "Prajay", "Developer", "IT"));
        repository.deleteById(employee.getId());

        Optional<Employee> foundEmployee = repository.findById(employee.getId());
        assertFalse(foundEmployee.isPresent(), "Employee should not be found after deletion");
    }
}
