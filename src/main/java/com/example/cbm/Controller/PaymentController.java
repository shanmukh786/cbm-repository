package com.example.cbm.Controller;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Repository.PaymentRepository;
import com.example.cbm.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class PaymentController {

    private PaymentService paymentService;
    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
   private CustomerRepository customerRepository;
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(value="/payments/{id}")
    public ResponseEntity<String> postAddNewEmployee(@PathVariable Integer id,@RequestBody Payments payments){
        Customers customers = customerRepository.findByCustomerNumber(id);
        Payments updated = paymentService.savePayment(payments,customers);
        return new ResponseEntity<String>("Payment details added Successfully", HttpStatus.CREATED);

    }
    @PutMapping("/payments/update/{customer_number}/{check_number}")
    public ResponseEntity<String> updateCheckNumberForCustomer(
            @PathVariable Integer customer_number,
            @PathVariable String check_number,@RequestParam String new_number) {

        try {
            paymentService.updateCheckNumberForCustomer(customer_number, check_number,new_number);
            return ResponseEntity.ok("Check number updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "update1/{customer_number}/{check_number}")
    public ResponseEntity<String> updateCheckAmountForCustomer(
            @PathVariable Integer customer_number,
            @PathVariable String check_number,@RequestParam BigDecimal amount)
    {
        paymentService.updateCheckAmountForCustomer(customer_number, check_number,amount);
        return ResponseEntity.ok("Check number updated successfully");
    }
    @GetMapping("/payments/{payment_date}")
    public ResponseEntity<List<Payments>> getAllPaymentsByDate(
            @PathVariable("paymentDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date payment_date) {
        List<Payments> payments = paymentService.getAllPaymentsByDate(payment_date);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    @GetMapping("/payments/{check_number}")
    public ResponseEntity<List<Payments>> getPaymentsByCheckNumber(@PathVariable String check_number) {
        List<Payments> payments = paymentService.searchPaymentsByCheckNumber(check_number);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    @GetMapping("/payments/totalAmount/{customer_number}")
    public ResponseEntity<BigDecimal> getTotalAmountByCustomerNumber(@PathVariable Integer customer_number) {
        BigDecimal totalAmount = paymentService.getTotalAmountByCustomerNumber(customer_number);
        return ResponseEntity.ok(totalAmount);
    }
    @GetMapping("payments/customers/{checkno}")
    public List<Customers> getCustomersByCheckNumber(@PathVariable String checkno) {
        return paymentService.getCustomersByCheckNumber(checkno);
    }
    @GetMapping("/payments/customers/maxamount")
    public ResponseEntity<Customers> getCustomerWithMaxPaymentAmount() {
        Customers customer = paymentService.findCustomerWithMaxPaymentAmount();

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }
    @GetMapping("payments/{payment_date}/customers")
    public List<Customers> getCustomersByPaymentDate(@PathVariable("payment_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date payment_date) {
        return paymentService.getCustomersByPaymentDate(payment_date);
    }
    @GetMapping("/payment/highest_collection_office_details")
    public ResponseEntity<Offices> getOfficeWithMaxPaymentCollection() {
        Offices office = paymentService.getOfficeWithMaxPaymentCollection();
        return new ResponseEntity<Offices>(office, HttpStatus.OK);
    }
    @GetMapping("payments/a/{customer_number}")
    public List<Payments> getPaymentsByCustomer(@PathVariable Integer customer_number) {
        return paymentService.getPaymentsByCustomer(customer_number);
    }
    @GetMapping("/customers/{start_Pay_date}/{end_Pay_date}")
    public List<Payments> getPaymentsByDateRange(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_Pay_date,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_Pay_date
    ) {
        return paymentService.getPaymentsByDateRange(start_Pay_date, end_Pay_date);
    }

}
