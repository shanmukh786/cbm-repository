package com.example.cbm.Service;

import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.EmployeeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employees addEmployee(Employees employees, Offices offices)
    {
        employees.getOffices().setOfficeCode(offices.getOfficeCode());

        return employeeRepository.save(employees);
    }

    public String updateEmployeeHierarchy()
    {
        return null;
    }
    public String updateEmployeeRoll()
    {
        return null;
    }
    public Employees assignOfficeToEmployee()
    {
        return null;
    }
    public Employees getEmployeeByNumber(Integer cid)
    {
        return employeeRepository.findById(cid).get();
    }
    public List<Employees> getAllEmployees()
    {
        return employeeRepository.findAll();
    }
    public List<Employees> getEmployeeByOfficeId()
    {
        return null;
    }

    public List<Employees> getEmployeesByCity(String city) {
        return employeeRepository.findByOffices_City(city);
    }







































}
