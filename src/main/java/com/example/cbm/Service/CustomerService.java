package com.example.cbm.Service;


import com.example.cbm.DTO.CustomerOrderPaymentDetails;
import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Exception.CustomerNotFoundException;
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
        List<Customers> customers = customerRepository.findByCustomerName(name);

        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with name: " + name);
        }

        return customers;
    }

    public List<Customers> searchCustomersByCity(String city) {

        List<Customers> customers =  customerRepository.findByCity(city);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found in city : " + city);
        }
        return customers;
    }
    public Customers getCustomerByCustomerNumber(Integer customerNumber) {
        Customers customer = customerRepository.findByCustomerNumber(customerNumber);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with customer number: " + customerNumber);
        }
        return customer;
    }
    public List<Customers> getCustomersByOfficeCode(String officeCode) {
        List<Customers> customers= customerRepository.findByEmployees_Offices_OfficeCode(officeCode);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with officeCode : " + officeCode);
        }
        return customers;
    }
    public List<Customers> searchCustomersBySalesRepEmployeeNumber(Integer employeeNumber) {

            String queryString = "SELECT c " +
                    "FROM Customers c " +
                    "WHERE c.employees.employeeNumber = :employeeNumber";

            TypedQuery<Customers> query = entityManager.createQuery(queryString, Customers.class);
            query.setParameter("employeeNumber", employeeNumber);
        List<Customers> customers= query.getResultList();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with SalesRepEmployeeNumber : " + employeeNumber);
        }
        return customers;

    }
    public List<Customers> searchByCountry(String country) {

        List<Customers> customers= customerRepository.findByCountry(country);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found in country : " + country);
        }
        return customers;
    }

    public Customers searchByPhoneNumber(String phone) {


        Customers customer = customerRepository.findByPhone(phone);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with phone: " + phone);
        }
        return customer;

    }


    public List<Customers> searchByContactLastName(String lastName) {

        List<Customers> customers= customerRepository.findByContactLastName(lastName);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with LastName : " + lastName);
        }
        return customers;
    }
    public Customers updateCustomerName(Integer customerNumber, String newName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);
        }
        customer.setCustomerName(newName);
        return customer;
    }
    public Customers updateContactLastName(Integer customerNumber, String newName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);
        }
        customer.setContactLastName(newName);
        return customer;
    }
    public Customers updateContactFirstName(Integer customerNumber, String newFirstName) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);
        }
        customer.setContactFirstName(newFirstName);
        return customer;
    }
    public String updateCustomerAddress(Integer customerNumber, String newAddressLine1, String newAddressLine2, String newCity, String newState, String newCountry, String newPostalCode) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);

        }
        customer.setAddressLine1(newAddressLine1);
        customer.setAddressLine2(newAddressLine2);
        customer.setCity(newCity);
        customer.setState(newState);
        customer.setCountry(newCountry);
        customer.setPostalCode(newPostalCode);
        return "customer address updated successfully";
    }


    public List<Customers> searchCustomersByPostalCode(String postalCode) {
        List<Customers> customers = customerRepository.findByPostalCode(postalCode);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with PostalCode : " + postalCode);
        }
        return customers;
    }

    public List<Customers> getCustomersByCreditRange(BigDecimal minCredit, BigDecimal maxCredit) {
        List<Customers> customers = customerRepository.findAll();

        List<Customers> customers1 = customers.stream()
                .filter(c -> c.getCreditLimit().compareTo(minCredit) >= 0 && c.getCreditLimit().compareTo(maxCredit) <= 0)
                .collect(Collectors.toList());
        if(customers1.isEmpty())
        {
            throw new CustomerNotFoundException("Customer not found with given Credit Range");
        }
        return customers1;
    }
    public Collection<Customers> getCustomersByPage(int pageNo, String sortBy, Sort.Direction sortDirection, int pageSize) {
        Sort sort = Sort.by(sortDirection, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Customers> customerPage = (Page<Customers>) entityManager.createQuery("SELECT c FROM Customers c", Customers.class).setFirstResult((int) pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).getResultList();

        return customerPage.getContent();
    }
    public List<Customers> searchCustomersByFirstName(String searchString) {
        List<Customers> allCustomers = customerRepository.findAll();

        List<Customers> customers1 = allCustomers.stream()
                .filter(customer -> customer.getContactFirstName().contains(searchString))
                .collect(Collectors.toList());
        if(customers1.isEmpty())
        {
            throw new CustomerNotFoundException("Customer not found with given FirstName");
        }
        return customers1;
    }
    public List<Customers> searchCustomersByGreaterCreditLimit(BigDecimal creditLimit) {
        List<Customers> allCustomers = customerRepository.findAll();
        String stringValue = creditLimit.toString();
        List<Customers> customers1 = allCustomers.stream()
                .filter(customer -> customer.getCreditLimit().compareTo(creditLimit) > 0)
                .collect(Collectors.toList());
        if(customers1.isEmpty())
        {
            throw new CustomerNotFoundException("Customer not found with Credit limit greater than" + stringValue);
        }
        return customers1;
    }

    public List<Customers> searchCustomersByLowerCreditLimit(BigDecimal creditLimit) {
        List<Customers> customersList = customerRepository.findAll();
        String stringValue = creditLimit.toString();
        List<Customers> customers1 = customersList.stream()
                .filter(customer -> customer.getCreditLimit().compareTo(creditLimit) < 0)
                .collect(Collectors.toList());
        if(customers1.isEmpty())
        {
            throw new CustomerNotFoundException("Customer not found with Credit limit less than" + stringValue);
        }
        return customers1;
    }



    public List<Payments> getPaymentDetailsForCustomer(Integer customerNumber) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);
        }
        List<Payments> payments = entityManager
                .createQuery("SELECT p FROM Payments p WHERE p.customers.customerNumber = :customerNumber", Payments.class)
                .setParameter("customerNumber", customerNumber)
                .getResultList();
        if(payments.isEmpty())
        {
            throw new CustomerNotFoundException("No Payment Details for CustomerNumber" + customerNumber);
        }
        return payments;
    }



    public List<Customers> searchCustomersByCreditLimit(BigDecimal creditLimt) {
        List<Customers> customers = customerRepository.findByCreditLimit(creditLimt);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with creditLimit : " + creditLimt);
        }
        return customers;
    }
    public Collection<Object[]> getOrderDetailsForCustomer(Integer customerNumber) {
        Customers customer = customerRepository.findById(customerNumber).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with number: " + customerNumber);
        }
        String queryString = "SELECT od, p, o, e " +
                "FROM OrderDetails od " +
                "JOIN od.product p " +
                "JOIN od.order o " +
                "JOIN o.customers c " +
                "JOIN c.employees e " +
                "WHERE c.customerNumber = :customerNumber";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("customerNumber", customerNumber);

        Collection<Object[]> objects = query.getResultList();
        if (objects.isEmpty()) {
            throw new CustomerNotFoundException("No order details found for Customer : " + customerNumber);
        }
        return objects;
    }
    public Collection<CustomerOrderPaymentDetails> getOrderAndPaymentDetailsForCustomer(String customerName) {
        List<Customers> customers1 = customerRepository.findByCustomerName(customerName);

        if (customers1.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with name: " + customerName);
        }
        String queryString = "SELECT new com.example.cbm.DTO.CustomerOrderPaymentDetails(o, p) " +
                "FROM Orders o " +
                "JOIN o.customers c " +
                "JOIN Payments p ON p.customers = c " +
                "WHERE c.customerName = :customerName";

        TypedQuery<CustomerOrderPaymentDetails> query = entityManager.createQuery(queryString, CustomerOrderPaymentDetails.class);
        query.setParameter("customerName", customerName);

        Collection<CustomerOrderPaymentDetails> customerOrderPaymentDetails = query.getResultList();
        if(customerOrderPaymentDetails.isEmpty())
        {
            throw new CustomerNotFoundException("No order payment details found for Customer : " + customerName);
        }
        return customerOrderPaymentDetails;
    }
}
