package com.example.cbm.Repository;

import com.example.cbm.Entity.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Offices,String> {

    public Offices findByOfficeCode(String code);
    List<Offices> findByCityIn(List<String> cities);
}
