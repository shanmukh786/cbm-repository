package com.example.cbm.Controller;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value="/payments")
    public ResponseEntity<String> postAddNewEmployee(@RequestParam Integer cid, @RequestBody Payments payments){
        Customers customers = customerRepository.findByCustomerNumber(cid);
        Payments updated = paymentService.savePayment(payments,customers);
        return new ResponseEntity<String>("Payment details added Successfully", HttpStatus.CREATED);

    }

}
