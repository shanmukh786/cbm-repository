package com.example.cbm.Service;


import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Repository.OrderRepository;
import com.example.cbm.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private OrderRepository ordersRepository;
    private PaymentRepository paymentsRepository;
    private EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository,OrderRepository ordersRepository,PaymentRepository paymentsRepository) {
        this.customerRepository = customerRepository;
        this.ordersRepository=ordersRepository;
        this.paymentsRepository=paymentsRepository;
    }

    public List<Customers> getCustomersByOfficeCode(String officeCode) {
        return customerRepository.findByEmployees_Offices_OfficeCode(officeCode);
    }
    public String saveCustomer(Customers customers)
    {
        customerRepository.save(customers);
        return "Record Created Successfully";
    }
    public List<Customers> searchCustomersByName(String name) {
        return customerRepository.findByCustomerName(name);
    }
    public List<Customers> searchCustomersByCity(String city) {
        return customerRepository.findByCity(city);
    }
    public Customers getCustomerByCustomerNumber(Integer customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber);
    }
    public List<Customers> searchCustomersBySalesRepEmployeeNumber(Integer employeeNumber) {
        return customerRepository.findByEmployeesEmployeeNumber(employeeNumber);
    }
    public List<Customers> searchByCountry(String country) {
        return customerRepository.findByCountry(country);
    }

    public Customers searchByPhoneNumber(String phone) {

        return customerRepository.findByPhone(phone);
    }

    public List<Customers> searchByContactFirstName(String firstName) {
        return customerRepository.findByContactFirstName(firstName);
    }

    public List<Customers> searchByContactLastName(String lastName) {
        return customerRepository.findByContactLastName(lastName);
    }
    public Customers updateCustomerName(Integer customerNumber, String newName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer != null) {
            customer.setCustomerName(newName);
        }
        return customer;
    }
    public Customers updateContactLastName(Integer customerNumber, String newName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer != null) {
            customer.setContactLastName(newName);
        }
        return customer;
    }
    public Customers updateContactFirstName(Integer customerNumber, String newFirstName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer != null) {
            customer.setContactFirstName(newFirstName);
        }
        return customer;
    }
    public String updateCustomerAddress(Integer customerNumber, String newAddressLine1, String newAddressLine2, String newCity, String newState, String newCountry, String newPostalCode) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer != null) {
            customer.setAddressLine1(newAddressLine1);
            customer.setAddressLine2(newAddressLine2);
            customer.setCity(newCity);
            customer.setState(newState);
            customer.setCountry(newCountry);
            customer.setPostalCode(newPostalCode);

        }
        return "customer address updated successfully";
    }


    public List<Customers> searchCustomersByPostalCode(String postalCode) {
        return customerRepository.findByPostalCode(postalCode);
    }

    public List<Customers> getCustomersByCreditRange(BigDecimal minCredit, BigDecimal maxCredit) {
        return customerRepository.findByCreditLimitBetween(minCredit, maxCredit);
    }
    public List<Customers> searchCustomersByFirstName(String searchString) {
        return customerRepository.findByContactFirstNameContaining(searchString);
    }
    public List<Customers> searchCustomersByCreditLimit(BigDecimal creditLimit) {
        return customerRepository.findByCreditLimitGreaterThan(creditLimit);
    }
    public List<Customers> getCustomersWithLowerCreditLimit(BigDecimal creditLimit) {
        return customerRepository.findByCreditLimitLessThan(creditLimit);
    }

    public List<Payments> getPaymentDetailsForCustomer(Integer customerNumber) {

        return entityManager
                .createQuery("SELECT p FROM Payments p WHERE p.customers.customerNumber = :customerNumber", Payments.class)
                .setParameter("customerNumber", customerNumber)
                .getResultList();
    }








}
