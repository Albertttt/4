package com.example.four.service;

import com.example.four.entity.Employee;
import com.example.four.exception.NotFoundException;
import com.example.four.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee get(long id) {
        return employeeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee createOrSaveEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployeeById(Long id) {
        return get(id);
    }

    public Employee updateEmployee(Employee newEmployee, Long id) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(newEmployee.getFirstName());
            employee.setLastName(newEmployee.getLastName());
            employee.setEmail(newEmployee.getEmail());
            employee.setPassword(newEmployee.getPassword());
            employee.setUserName(newEmployee.getUserName());
            return employeeRepository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepository.save(newEmployee);
        });
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
