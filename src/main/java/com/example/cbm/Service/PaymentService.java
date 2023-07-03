package com.example.cbm.Service;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {
    PaymentRepository paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public Payments savePayment(Payments payments,Customers customers)
    {
        payments.getCustomers().setCustomerNumber(customers.getCustomerNumber());
        return paymentRepository.save(payments);
    }
/*

    public String updateCheckNumberForCustomer(int customerNumber, String newCheckNumber) {
        // Find the customer's payment
        Customers customer = paymentRepository.findByCustomerNumber(customerNumber);
        if (customer != null) {
            // Create a new payment object
            Payments payment = new Payments();
            payment.setCheckNumber(newCheckNumber);

            // Save the payment details
            paymentRepository.save(payment);
        }
        return "check number updated successfully";

    }
    public String updateCheckAmountForCustomer(int customerNumber, BigDecimal newAmount) {
        // Find the customer's payment
        Customers customer = paymentRepository.findByCustomerNumber(customerNumber);
        if (customer != null) {
            // Create a new payment object
            Payments payment = new Payments();
            payment.setAmount(newAmount);

            // Save the payment details
            paymentRepository.save(payment);
        }
        return "payment amount details updated successfully";

    }*/
}


