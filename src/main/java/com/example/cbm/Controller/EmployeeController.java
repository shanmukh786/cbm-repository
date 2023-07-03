package com.example.cbm.Controller;

import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.OfficeRepository;
import com.example.cbm.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {

    EmployeeService employeeService;
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    private OfficeRepository officeRepository;
    @Autowired
    public void setOfficeRepository(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @GetMapping(value = "/employee")
    public Employees getEmployeeDetails(@RequestParam Integer cid)
    {
        Employees employees = employeeService.getEmployeeByNumber(cid);
        System.out.println(employees);
        return employees;
    }
    @PostMapping(value="/saveEmployee/{code}")
    public ResponseEntity<Employees> postAddNewEmployee(@PathVariable String code,@RequestBody Employees employee){
        Offices offices = officeRepository.findByOfficeCode(code);

        Employees createdEmployee = employeeService.addEmployee(employee,offices);
        return new ResponseEntity<Employees>(createdEmployee, HttpStatus.CREATED);
    }


}
