package com.example.back_end.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Récupérer tous les employés
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Récupérer un employé par son ID
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    // Ajouter un employé
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Mettre à jour un employé
    public Employee updateEmployee(Integer id, Employee newEmployeeData) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployeeData.getName());
                    employee.setEmail(newEmployeeData.getEmail());
                    employee.setAddress(newEmployeeData.getAddress());
                    employee.setPhone(newEmployeeData.getPhone());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // Supprimer un employé
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}