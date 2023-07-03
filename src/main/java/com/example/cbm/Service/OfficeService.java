package com.example.cbm.Service;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    OfficeRepository officeRepository;
    @Autowired
    public void setOfficeRepository(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public String saveOffice(Offices offices)
    {
        officeRepository.save(offices);
        return "office details added successfully";
    }
    public Offices getOfficeDetails(String id)
    {
        return officeRepository.findById(id).get();
    }
    public String updatePhoneNumber(String id)
    {
        return "office phone number updated successfully";
    }
    public String updateOfficeAddress(String id)
    {
        return "office address updated successfully";
    }
    public List<Customers> getAllCustomersByOfficeCode(String officeCode)
    {
        return null;
    }
    public List<Offices> getOfficesByCities(List<String> cities) {
        return officeRepository.findByCityIn(cities);
    }
    public Offices findByCode(String code)
    {
        return officeRepository.findByOfficeCode(code);
    }

}
