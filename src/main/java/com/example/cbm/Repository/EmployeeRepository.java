package com.example.cbm.Repository;

import com.example.cbm.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Integer> {

    List<Employees> findByOffices_City(String city);

    List<Employees> findByOfficesOfficeCode(String officeCode);

    List<Employees> findByOfficesCity(String city);
}
