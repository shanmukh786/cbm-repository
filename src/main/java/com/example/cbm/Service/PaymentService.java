package com.example.cbm.Service;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    PaymentRepository paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    private EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Payments savePayment(Payments payments, Customers customers)
    {
        payments.getCustomers().setCustomerNumber(customers.getCustomerNumber());
        return paymentRepository.save(payments);
    }


    public void updateCheckNumberForCustomer(Integer customerNumber,String old, String newCheckNumber) {
        // Retrieve the Payments entity for the specified customer number
        List<Payments> payment = paymentRepository.findByCustomers_CustomerNumber(customerNumber);
        for (Payments p:payment) {
            if (p.getCheckNumber().equals(old)) {

                p.setCheckNumber(newCheckNumber);

                // Save the updated entity
                paymentRepository.save(p);
                break;
            }
        }


    }


    public void updateCheckAmountForCustomer(Integer customerNumber, String checkNumber, BigDecimal amount) {
        Payments payments = paymentRepository.findById(checkNumber).get();
        payments.setAmount(amount);
        paymentRepository.save(payments);
    }
    public List<Payments> getAllPaymentsByDate(Date paymentDate) {
        return paymentRepository.findByPaymentDate(paymentDate);
    }
    public List<Payments> searchPaymentsByCheckNumber(String checkNumber) {
        return paymentRepository.findByCheckNumber(checkNumber);
    }
    public BigDecimal getTotalAmountByCustomerNumber(Integer customerNumber) {
        List<Payments> payments = paymentRepository.findByCustomersCustomerNumber(customerNumber);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Payments payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
        }

        return totalAmount;
    }
    public List<Customers> getCustomersByCheckNumber(String checkNumber) {
        List<Payments> payments = paymentRepository.findAll();

        return payments.stream()
                .filter(payment -> payment.getCheckNumber().equals(checkNumber))
                .map(Payments::getCustomers)
                .collect(Collectors.toList());
    }
    public Customers findCustomerWithMaxPaymentAmount() {
        String jpql = "SELECT p.customers FROM Payments p WHERE p.amount = (SELECT MAX(p.amount) FROM Payments p)";

        TypedQuery<Customers> query = entityManager.createQuery(jpql, Customers.class);
        query.setMaxResults(1);

        List<Customers> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null; // No customer found
        }

        return resultList.get(0);
    }
    public List<Customers> getCustomersByPaymentDate(Date date) {
        return paymentRepository.findByPaymentDate(date)
                .stream()
                .map(Payments::getCustomers)
                .collect(Collectors.toList());
    }
    public Offices getOfficeWithMaxPaymentCollection() {
        String queryString = "SELECT p.customers.employees.offices.officeCode, SUM(p.amount) FROM Payments p GROUP BY p.customers.employees.offices.officeCode ORDER BY SUM(p.amount) DESC";
        Query query = entityManager.createQuery(queryString);
        query.setMaxResults(1);

        Object[] result = (Object[]) query.getSingleResult();

        String officeCode = (String) result[0];
        Offices office = entityManager.find(Offices.class, officeCode);

        return office;
    }

}


