package com.example.cbm.Controller;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/office")
public class OfficeController {

    OfficeService officeService;
    @Autowired
    public void setOfficeService(OfficeService officeService) {
        this.officeService = officeService;
    }
    @PostMapping(value = "/")
    public ResponseEntity<String> addOffice(@RequestBody Offices offices)
    {
        Offices offices1 = officeService.saveOffice(offices);
        return new ResponseEntity<String>("office details added successfully", HttpStatus.CREATED);
    }
    @GetMapping(value = "/{office_code}")
    public ResponseEntity<Offices> findOfficeByOfficeCode(@PathVariable String office_code)
    {
        Offices offices1 = officeService.findByOfficeCode(office_code);
        return new ResponseEntity<Offices>(offices1, HttpStatus.CREATED);
    }
    @PutMapping(value = "{office_code}/{phone}")
    public ResponseEntity<String> updatePhoneNumber(@PathVariable String office_code,@PathVariable String phone)
    {
        String s = officeService.updatePhoneNumber(office_code,phone);
        return new ResponseEntity<>(s,HttpStatus.OK);
    }
    @PutMapping(value = "{office_code}")
    public ResponseEntity<String> updateAddress(@PathVariable String office_code,@RequestParam String address)
    {
        String s = officeService.updateAddress(office_code,address);
        return new ResponseEntity<>(s,HttpStatus.OK);
    }
    @GetMapping("customers/{office_code}")
    public ResponseEntity<List<Customers>> getCustomersByOfficeCode(@PathVariable String office_code) {
        List<Customers> customer =  officeService.getCustomersByOfficeCode(office_code);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
    @GetMapping("/a/{city1}/{city2}")
    public List<Offices> getOfficesByCities(@PathVariable String city1,@PathVariable String city2) {
        return officeService.getOfficesByCities(city1,city2);
    }
}
