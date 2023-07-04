package com.example.cbm.Service;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Offices;
import com.example.cbm.Repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class OfficeService {

    OfficeRepository officeRepository;
    @Autowired
    public void setOfficeRepository(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Offices saveOffice(Offices offices)
    {
        return officeRepository.save(offices);

    }


    public Offices findByOfficeCode(String officeCode) {
        return officeRepository.findByOfficeCode(officeCode);
    }

    public String updatePhoneNumber(String officeCode, String phone) {
        Offices offices = officeRepository.findByOfficeCode(officeCode);
        String s = new String();
        if(offices!=null)
        {
            offices.setPhone(phone);
            officeRepository.save(offices);
            s = "office phone number updated successfully";
        }
        else {

        }
        return s;
    }
    public String updateAddress(String officeCode, String address) {
        Offices offices = officeRepository.findByOfficeCode(officeCode);
        String s = new String();
        if(offices!=null)
        {
            offices.setAddressLine1(address);
            officeRepository.save(offices);
            s = "office address updated successfully";
        }
        else {
            s = "Office not found";
        }
        return s;
    }
    public List<Customers> getCustomersByOfficeCode(String officeCode) {
            String queryString = "SELECT c " +
                    "FROM Customers c " +
                    "WHERE c.employees.offices.officeCode = :officeCode";

            return entityManager.createQuery(queryString, Customers.class)
                    .setParameter("officeCode", officeCode)
                    .getResultList();
        }
    public List<Offices> getOfficesByCities(String... cities) {
        return officeRepository.findByCityIn(cities);
    }


}
