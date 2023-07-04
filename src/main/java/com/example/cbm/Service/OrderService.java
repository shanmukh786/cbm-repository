package com.example.cbm.Service;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Products;
import com.example.cbm.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Orders saveOrders(Orders orders)
    {
        return orderRepository.save(orders);
    }
    public Orders updateOrderShippedDate(Integer orderNumber, Date shippedDate) {
        Orders order = orderRepository.findById(orderNumber).get();
        if (order != null) {
            order.setShippedDate(shippedDate);
            orderRepository.save(order);
        }
        return order;
    }
    public Orders updateOrderStatusDate(Integer orderNumber, String statusdate) {
        Orders order = orderRepository.findById(orderNumber).get();
        if (order != null) {
            order.setStatus(statusdate);
            orderRepository.save(order);
        }
        return order;
    }
    public List<Orders> getAllOrdersByCustomer(Integer customerNumber) {
        String queryString = "SELECT o FROM Orders o WHERE o.customers.customerNumber = :customerNumber";

        TypedQuery<Orders> query = entityManager.createQuery(queryString, Orders.class);
        query.setParameter("customerNumber", customerNumber);

        return query.getResultList();
    }
    public Orders getOrderDetails(Integer orderNumber)
    {
        return orderRepository.findById(orderNumber).get();
    }
    public List<Orders> getAllOrdersByOrderDate(Date date)
    {
        return orderRepository.findByOrderDate(date);
    }
    public List<Orders> getAllOrdersByRequiredDate(Date date)
    {
        return orderRepository.findByRequiredDate(date);
    }

    public List<Orders> getAllOrdersByShippedDate(Date shippedDate) {
        return orderRepository.findByShippedDate(shippedDate);
    }

    public List<Orders> getAllOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    public List<Orders> getOrdersByStatusAndCustomer(String orderStatus, Customers customer) {
        String queryString = "SELECT o FROM Orders o WHERE o.status = :status AND o.customers = :customer";

        TypedQuery<Orders> query = entityManager.createQuery(queryString, Orders.class);
        query.setParameter("status", orderStatus);
        query.setParameter("customer", customer);

        return query.getResultList();
    }
    public List<Products> getProductDetailsForOrder(Integer orderNumber) {
        String queryString = "SELECT od.product FROM OrderDetails od WHERE od.order.orderNumber = :orderNumber";

        TypedQuery<Products> query = entityManager.createQuery(queryString, Products.class);
        query.setParameter("orderNumber", orderNumber);

        return query.getResultList();
    }
    public List<String> getProductNamesForOrderNumber(Integer orderNumber) {
        String queryString = "SELECT od.product.productName " +
                "FROM OrderDetails od " +
                "WHERE od.order.orderNumber = :orderNumber";

        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("orderNumber", orderNumber);

        return query.getResultList();
    }
    public List<Products> getAllProductDetailsForAllOrders() {
        String queryString = "SELECT od.product FROM OrderDetails od";
        TypedQuery<Products> query = entityManager.createQuery(queryString, Products.class);
        return query.getResultList();
    }
    public List<Orders> getDeliveredOrdersWithSameDeliveryDate(String order_status) {
        String queryString = "SELECT o FROM Orders o " +
                "WHERE o.status = :order_status " +
                "AND o.shippedDate IS NOT NULL " +
                "AND o.shippedDate = o.orderDate";

        TypedQuery<Orders> query = entityManager.createQuery(queryString, Orders.class);
        query.setParameter("order_status", order_status);

        return query.getResultList();
    }
    public List<Products> getProductsByShipmentDate() {
        String queryString = "SELECT od.product FROM OrderDetails od " +
                "WHERE od.order.shippedDate = 2023-01-01";

        TypedQuery<Products> query = entityManager.createQuery(queryString, Products.class);

        return query.getResultList();
    }
}

