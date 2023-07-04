package com.example.cbm.Repository;

import com.example.cbm.Entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Integer> {
    List<Customers> findByEmployees_Offices_OfficeCode(String officeCode);
    List<Customers> findByCustomerName(String customerName);
    List<Customers> findByCity(String city);
    Customers findByCustomerNumber(Integer customerNumber);
    List<Customers> findByEmployeesEmployeeNumber(Integer employeeNumber);
    List<Customers> findByCountry(String country);

    Customers findByPhone(String phone);

    List<Customers> findByContactFirstName(String firstName);

    List<Customers> findByContactLastName(String lastName);

    List<Customers> findByPostalCode(String postalCode);
    List<Customers> findByCreditLimitBetween(BigDecimal minCredit, BigDecimal maxCredit);
    List<Customers> findByContactFirstNameContaining(String searchString);
    List<Customers> findByCreditLimitGreaterThan(BigDecimal creditLimit);
    List<Customers> findByCreditLimitLessThan(BigDecimal creditLimit);


    List<Customers> findByCreditLimit(BigDecimal creditLimit);
}
