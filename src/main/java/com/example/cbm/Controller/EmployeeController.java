package com.example.cbm.Controller;

import com.example.cbm.DTO.EmployeeOfficeDTO;
import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.OfficeRepository;
import com.example.cbm.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/employee")
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
    @PostMapping(value = "/")
    public ResponseEntity<String> addEmployee(@RequestBody Employees employees)
    {
        employeeService.addEmployee(employees);
        return new ResponseEntity<>("New Employee added successfully",HttpStatus.OK);
    }
    @PutMapping(value = "/{employe_no}/reports_to/{new_employee_no}")
    public ResponseEntity<String> updateHierarchy(@PathVariable Integer employee_no,@PathVariable Integer new_employee_no)
    {
        Employees employees = employeeService.updateEmployeeHierarchy(employee_no,new_employee_no);
        return new ResponseEntity<>("details updated successfully",HttpStatus.OK);
    }
    @PutMapping(value = "/{role}")
    public ResponseEntity<Employees> updateRole(@PathVariable String role,@RequestParam Integer employee_number)
    {
        Employees employees = employeeService.updateEmployeeRoll(employee_number,role);
        return new ResponseEntity<Employees>(employees,HttpStatus.OK);
    }
    @GetMapping(value = "/employee")
    public Employees getEmployeeDetails(@RequestParam Integer cid)
    {
        Employees employees = employeeService.getEmployeeByNumber(cid);
        System.out.println(employees);
        return employees;
    }
    @PutMapping("//mapToOffice/{office_code}/")
    public ResponseEntity<EmployeeOfficeDTO> assignOfficeToEmployee(
            @RequestParam("employeeNumber") Integer employeeNumber,
            @PathVariable String office_code) {

        EmployeeOfficeDTO employeeOfficeDTO = employeeService.assignOfficeToEmployee(office_code, employeeNumber);

        if (employeeOfficeDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeOfficeDTO);
    }

    @GetMapping(value = "/all_employee_details")
    public ResponseEntity<List<Employees>> getEmployeeDetails()
    {
        List<Employees> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees,HttpStatus.OK);

    }
    @GetMapping("/byOfficeCode/{officeCode}")
    public List<Employees> getAllEmployeesByOfficeCode(@PathVariable String officeCode) {
        return employeeService.getAllEmployeesByOfficeCode(officeCode);
    }
    @GetMapping("/{city}")
    public List<Employees> getEmployeesByCity(@PathVariable String city) {
        return employeeService.getEmployeesByCity(city);
    }



}
