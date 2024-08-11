package com.prajay.Employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Use Mockito's JUnit 5 extension
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void findAll() {
        Employee emp1 = new Employee(1L, "Prajay", "Developer", "IT");
        Employee emp2 = new Employee(2L, "Yuvan", "Developer", "HR");
        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> employees = service.findAll();
        assertNotNull(employees, "The list of employees should not be null");
        assertEquals(2, employees.size(), "There should be two employees in the list");
        assertEquals("Prajay", employees.get(0).getName(), "The name of the first employee should match");
    }

    @Test
    void save() {
        Employee newEmployee = new Employee(null, "Prajay", "Developer", "IT");
        when(repository.save(newEmployee)).thenReturn(new Employee(1L, "Prajay", "Developer", "IT"));

        Employee savedEmployee = service.save(newEmployee);
        assertNotNull(savedEmployee, "The saved employee should not be null");
        assertNotNull(savedEmployee.getId(), "The saved employee should have an ID");
        assertEquals("Prajay", savedEmployee.getName(), "The name of the saved employee should match");
    }

    @Test
    void findById() {
        Optional<Employee> optionalEmployee = Optional.of(new Employee(1L, "Prajay", "Developer", "IT"));
        when(repository.findById(1L)).thenReturn(optionalEmployee);

        Optional<Employee> foundEmployee = service.findById(1L);
        assertTrue(foundEmployee.isPresent(), "An employee should be found with ID 1");
        foundEmployee.ifPresent(e -> assertEquals("Prajay", e.getName(), "The employee's name should match"));
    }

    @Test
    void deleteById() {
        Long employeeId = 1L;
        doNothing().when(repository).deleteById(employeeId);

        service.deleteById(employeeId);
        verify(repository, times(1)).deleteById(employeeId);  // Confirm that the repository method was called
    }
}
