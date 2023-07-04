package com.example.cbm.Repository;

import com.example.cbm.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByOrderDate(Date date);
    List<Orders> findByRequiredDate(Date date);
    List<Orders> findByShippedDate(Date date);

    List<Orders> findByStatus(String status);
}
