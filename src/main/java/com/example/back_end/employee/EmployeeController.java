package com.example.back_end.employee;


import com.example.back_end.sqs.SqsLoggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private final SqsLoggerService sqsLoggerService;

    public EmployeeController(SqsLoggerService sqsLoggerService) {
        this.sqsLoggerService = sqsLoggerService;
    }

    // Récupérer tous les employés
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Récupérer un employé par son ID
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    // Ajouter un employé
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        try {
            sqsLoggerService.logEvent("create", new ObjectMapper().writeValueAsString(employee));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return employeeService.addEmployee(employee);
    }

    // Mettre à jour un employé
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee newEmployeeData) {
        try {
            sqsLoggerService.logEvent("Put", new ObjectMapper().writeValueAsString(employeeService.getEmployeeById(id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return employeeService.updateEmployee(id, newEmployeeData);
    }

    // Supprimer un employé
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        try {
            sqsLoggerService.logEvent("create", new ObjectMapper().writeValueAsString(employeeService.getEmployeeById(id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        employeeService.deleteEmployee(id);
    }
}