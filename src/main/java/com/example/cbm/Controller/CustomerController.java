package com.example.cbm.Controller;

import com.example.cbm.Entity.Employees;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Entity.Payments;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
     CustomerService customerService;
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value="/{customer_id}/paymentdetails")
    public ResponseEntity<List<Payments>> showPaymentForCustomer(@PathVariable Integer customer_id)
    {
        List<Payments> payments = customerService.getPaymentDetailsForCustomer(customer_id);
        return new ResponseEntity<List<Payments>>(payments,HttpStatus.OK);
    }
}
