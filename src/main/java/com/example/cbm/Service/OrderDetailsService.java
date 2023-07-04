package com.example.cbm.Service;

import com.example.cbm.Entity.OrderDetails;
import com.example.cbm.Entity.OrderDetailsId;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderDetailsService {
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    public void setOrderDetailsRepository(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<OrderDetails> getOrderDetailsForMaxPriceOrder() {
        String queryString = "SELECT od FROM OrderDetails od " +
                "WHERE od.priceEach = (SELECT MAX(od2.priceEach) FROM OrderDetails od2)";

        TypedQuery<OrderDetails> query = entityManager.createQuery(queryString, OrderDetails.class);

        return query.getResultList();
    }
    public OrderDetails saveOrderDetails(OrderDetails orderDetails)
    {
        return orderDetailsRepository.save(orderDetails);
    }
    @Transactional
    public void updateQuantityOrdered(Integer order_number,String product_code, Integer newQuantity) {
        OrderDetails orderDetails = orderDetailsRepository.findById(order_number).get();
            orderDetails.setQuantityOrdered(newQuantity);
            orderDetailsRepository.save(orderDetails);
        }
    public List<OrderDetails> searchByOrderNumber( Integer order_number) {
        String queryString = "SELECT od FROM OrderDetails od WHERE od.order.orderNumber = :order_number";
        TypedQuery<OrderDetails> query = entityManager.createQuery(queryString, OrderDetails.class);
        query.setParameter("order_number", order_number);
        return query.getResultList();
    }
    public BigDecimal getTotalAmountByOrderNumber(Integer orderNumber) {
        String queryString = "SELECT SUM(od.quantityOrdered * od.priceEach) " +
                "FROM OrderDetails od " +
                "WHERE od.order.orderNumber = :orderNumber";

        TypedQuery<BigDecimal> query = entityManager.createQuery(queryString, BigDecimal.class);
        query.setParameter("orderNumber", orderNumber);

        BigDecimal totalAmount = query.getSingleResult();
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
    }
    public BigDecimal getTotalSale() {
        String queryString = "SELECT SUM(od.quantityOrdered * od.priceEach) FROM OrderDetails od";
        TypedQuery<BigDecimal> query = entityManager.createQuery(queryString, BigDecimal.class);
        BigDecimal totalSale = query.getSingleResult();

        return totalSale != null ? totalSale : BigDecimal.ZERO;
    }
    public int getOrderDetailsCountByOrderNumber(int orderNumber) {
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        int count = 0;

        for (OrderDetails orderDetails : orderDetailsList) {
            if (orderDetails.getOrder().getOrderNumber() == orderNumber) {
                count++;
            }
        }

        return count;
    }
}
