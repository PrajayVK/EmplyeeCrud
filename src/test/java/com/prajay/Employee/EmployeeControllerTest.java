package com.prajay.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee1 = new Employee(1L, "Prajay", "Developer", "IT");
        employee2 = new Employee(2L, "Yuvan", "Developer", "HR");
    }

    @Test
    void getAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(service.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = controller.getAllEmployees();

        // Assert
        assertEquals(2, result.size());
        assertEquals(employee1, result.get(0));
        assertEquals(employee2, result.get(1));
        verify(service, times(1)).findAll();
    }

    @Test
    void createEmployee() {
        // Arrange
        when(service.save(employee1)).thenReturn(employee1);

        // Act
        Employee result = controller.createEmployee(employee1);

        // Assert
        assertNotNull(result);
        assertEquals(employee1, result);
        verify(service, times(1)).save(employee1);
    }

    @Test
    void getEmployeeById() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(employee1));

        // Act
        Employee result = controller.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(employee1, result);
        verify(service, times(1)).findById(1L);
    }

    @Test
    void updateEmployee() {
        // Arrange
        Employee updatedEmployee = new Employee(1L, "Prajay Updated", "Senior Developer", "IT");
        when(service.findById(1L)).thenReturn(Optional.of(employee1));
        when(service.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        Employee result = controller.updateEmployee(1L, updatedEmployee);

        // Assert
        assertNotNull(result);
        assertEquals("Prajay Updated", result.getName());
        assertEquals("Senior Developer", result.getRole());
        verify(service, times(1)).findById(1L);
        verify(service, times(1)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee() {
        // Act
        controller.deleteEmployee(1L);

        // Assert
        verify(service, times(1)).deleteById(1L);
    }
}
