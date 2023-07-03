package com.example.cbm.Repository;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Payments;
import jdk.jfr.StackTrace;
import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payments,String> {

    //Customers findByCustomerNumber(Integer customerNumber);
}

