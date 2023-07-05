package com.example.cbm.DTO;

import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;

public class EmployeeOfficeDTO {
    private Employees employee;

    public EmployeeOfficeDTO(Employees employee, Offices office) {
        this.employee = employee;
        this.office = office;
    }

    public EmployeeOfficeDTO() {
    }

    private Offices office;

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Offices getOffice() {
        return office;
    }

    public void setOffice(Offices office) {
        this.office = office;
    }
}
