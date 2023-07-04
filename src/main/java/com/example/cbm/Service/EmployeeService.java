package com.example.cbm.Service;

import com.example.cbm.DTO.EmployeeOfficeDTO;
import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.EmployeeRepository;
import com.example.cbm.Repository.OfficeRepository;
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
    OfficeRepository officeRepository;
    @Autowired
    public void setOfficeRepository(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Employees addEmployee(Employees employees)
    {


        return employeeRepository.save(employees);
    }

    public Employees updateEmployeeHierarchy(Integer employee_number,Integer new_Employee_Number)
    {
        Employees employees = employeeRepository.findById(employee_number).get();
        employees.setReportsTo(new_Employee_Number);
        employeeRepository.save(employees);
        return employees;

    }
    public Employees updateEmployeeRoll(Integer employee_no,String role)
    {
        Employees employees = employeeRepository.findById(employee_no).get();
        employees.setJobTitle(role);
        employeeRepository.save(employees);
        return employees;
    }
    public EmployeeOfficeDTO assignOfficeToEmployee(String officeCode, Integer employeeNumber) {
        Offices office = officeRepository.findByOfficeCode(officeCode);
        Employees employee = employeeRepository.findById(employeeNumber).get();

        if (office == null || employee == null) {
            // Handle the case where either the office or employee is not found
            return null;
        }

        employee.setOffices(office);
        employeeRepository.save(employee);

        EmployeeOfficeDTO employeeOfficeDTO = new EmployeeOfficeDTO(employee,office);


        return employeeOfficeDTO;
    }
    public Employees getEmployeeByNumber(Integer cid)
    {
        return employeeRepository.findById(cid).get();
    }
    public List<Employees> getAllEmployees()
    {
        return employeeRepository.findAll();
    }
    public List<Employees> getAllEmployeesByOfficeCode(String officeCode) {
        return employeeRepository.findByOfficesOfficeCode(officeCode);
    }

    public List<Employees> getEmployeesByCity(String city) {
        return employeeRepository.findByOfficesCity(city);
    }
    }








































