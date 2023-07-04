package com.example.cbm.Service;


import com.example.cbm.DTO.CustomerOrderPaymentDetails;
import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Repository.OrderRepository;
import com.example.cbm.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Customers> getCustomersByOfficeCode(String officeCode) {
        return customerRepository.findByEmployees_Offices_OfficeCode(officeCode);
    }
    public List<Customers> searchCustomersBySalesRepEmployeeNumber(Integer employeeNumber) {
        String queryString = "SELECT c " +
                "FROM Customers c " +
                "WHERE c.employees.employeeNumber = :employeeNumber";

        TypedQuery<Customers> query = entityManager.createQuery(queryString, Customers.class);
        query.setParameter("employeeNumber", employeeNumber);

        return query.getResultList();


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
        List<Customers> customers = customerRepository.findAll();
        return customers.stream()
                .filter(c -> c.getCreditLimit().compareTo(minCredit) >= 0 && c.getCreditLimit().compareTo(maxCredit) <= 0)
                .collect(Collectors.toList());
    }
    public Collection<Customers> getCustomersByPage(int pageNo, String sortBy, Sort.Direction sortDirection, int pageSize) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Customers> customerPage = (Page<Customers>) entityManager.createQuery("SELECT c FROM Customers c", Customers.class).setFirstResult((int) pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).getResultList();

        return customerPage.getContent();
    }
    public List<Customers> searchCustomersByFirstName(String searchString) {
        List<Customers> allCustomers = customerRepository.findAll();

        return allCustomers.stream()
                .filter(customer -> customer.getContactFirstName().contains(searchString))
                .collect(Collectors.toList());
    }
    public List<Customers> searchCustomersByGreaterCreditLimit(BigDecimal creditLimit) {
        List<Customers> allCustomers = customerRepository.findAll();

        return allCustomers.stream()
                .filter(customer -> customer.getCreditLimit().compareTo(creditLimit) > 0)
                .collect(Collectors.toList());
    }

    public List<Customers> searchCustomersByLowerCreditLimit(BigDecimal creditLimit) {
        List<Customers> customersList = customerRepository.findAll();
        return customersList.stream()
                .filter(customer -> customer.getCreditLimit().compareTo(creditLimit) < 0)
                .collect(Collectors.toList());
    }



    public List<Payments> getPaymentDetailsForCustomer(Integer customerNumber) {

        return entityManager
                .createQuery("SELECT p FROM Payments p WHERE p.customers.customerNumber = :customerNumber", Payments.class)
                .setParameter("customerNumber", customerNumber)
                .getResultList();
    }


    public List<Customers> searchCustomersByCreditLimit(BigDecimal creditLimt) {
        return customerRepository.findByCreditLimit(creditLimt);
    }
    public Collection<Object[]> getOrderDetailsForCustomer(Integer customerNumber) {
        String queryString = "SELECT od, p, o, e " +
                "FROM OrderDetails od " +
                "JOIN od.product p " +
                "JOIN od.order o " +
                "JOIN o.customers c " +
                "JOIN c.employees e " +
                "WHERE c.customerNumber = :customerNumber";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("customerNumber", customerNumber);

        return query.getResultList();
    }
    public Collection<CustomerOrderPaymentDetails> getOrderAndPaymentDetailsForCustomer(String customerName) {
        String queryString = "SELECT new com.example.cbm.DTO.CustomerOrderPaymentDetails(o, p) " +
                "FROM Orders o " +
                "JOIN o.customers c " +
                "JOIN Payments p ON p.customers = c " +
                "WHERE c.customerName = :customerName";

        TypedQuery<CustomerOrderPaymentDetails> query = entityManager.createQuery(queryString, CustomerOrderPaymentDetails.class);
        query.setParameter("customerName", customerName);

        return query.getResultList();
    }
}
