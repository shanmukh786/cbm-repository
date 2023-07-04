package com.example.cbm.Controller;

import com.example.cbm.DTO.CustomerOrdersResponse;
import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Products;
import com.example.cbm.Repository.CustomerRepository;
import com.example.cbm.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    CustomerRepository customerRepository;
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping(value = "/")
    public ResponseEntity<String> addOrder(@RequestBody Orders orders)
    {
        orderService.saveOrders(orders);
        return new ResponseEntity<String>("Order Created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{order_number}/{shipped_date}")
    public ResponseEntity<String> updateOrderShippedDate(
            @PathVariable Integer order_number,
            @PathVariable("shipped_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date shipped_date
    ) {
        orderService.updateOrderShippedDate(order_number, shipped_date);
        return new ResponseEntity<>("Order shipped date updated successfully", HttpStatus.OK);
    }
    @PutMapping("/{order_number}")
    public ResponseEntity<String> updateOrderStatusDate(
            @PathVariable Integer order_number,
            @RequestParam String status_date
    ) {
        orderService.updateOrderStatusDate(order_number, status_date);
        return new ResponseEntity<>("Order shipped date updated successfully", HttpStatus.OK);
    }
    @GetMapping("/{customerNumber}")
    public ResponseEntity<CustomerOrdersResponse> getAllOrdersByCustomer(@PathVariable Integer customerNumber) {

        List<Orders> orders = orderService.getAllOrdersByCustomer(customerNumber);
        Customers customer = customerRepository.findById(customerNumber).orElse(null);

        if (customer != null) {
            CustomerOrdersResponse response = new CustomerOrdersResponse(customer, orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{order_number}")
    public ResponseEntity<Orders> getOrder(@PathVariable Integer order_number)
    {
        Orders order = orderService.getOrderDetails(order_number);
        return new ResponseEntity<Orders>(order, HttpStatus.OK);
    }
    @GetMapping(value = "/{order_date}")
    public ResponseEntity<List<Orders>> getOrderByOrderDate(@PathVariable Date order_date)
    {
        List<Orders> order = orderService.getAllOrdersByOrderDate(order_date);
        return new ResponseEntity<List<Orders>>(order, HttpStatus.OK);
    }
    @GetMapping(value = "/{required_date}")
    public ResponseEntity<List<Orders>> getOrderByRequiredDate(@PathVariable Date required_date)
    {
        List<Orders> order = orderService.getAllOrdersByRequiredDate(required_date);
        return new ResponseEntity<List<Orders>>(order, HttpStatus.OK);
    }
    @GetMapping(value = "/{shipped_date}")
    public ResponseEntity<List<Orders>> getOrderByShippedDate(@PathVariable Date shipped_date)
    {
        List<Orders> order = orderService.getAllOrdersByShippedDate(shipped_date);
        return new ResponseEntity<List<Orders>>(order, HttpStatus.OK);
    }
    @GetMapping(value = "/{status}")
    public ResponseEntity<List<Orders>> getOrderByStatus(@PathVariable String status)
    {
        List<Orders> order = orderService.getAllOrdersByStatus(status);
        return new ResponseEntity<List<Orders>>(order, HttpStatus.OK);
    }
    @GetMapping("/{customer_number}/{status}")
    public ResponseEntity<CustomerOrdersResponse> getOrdersByStatusAndCustomer(
            @PathVariable Integer customer_number,
            @PathVariable String status
    ) {
        Customers customer = customerRepository.findById(customer_number).get();

        List<Orders> orders = orderService.getOrdersByStatusAndCustomer(status, customer);

        CustomerOrdersResponse response = new CustomerOrdersResponse(customer, orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{order_id}/products")
    public ResponseEntity<List<Products>> getProductDetailsForOrder(@PathVariable Integer order_id) {
        List<Products> products = orderService.getProductDetailsForOrder(order_id);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{order_id}/product_names")
    public ResponseEntity<List<String>> getProductNamesForOrderNumber(@PathVariable Integer order_id) {
        List<String> productNames = orderService.getProductNamesForOrderNumber(order_id);
        if (!productNames.isEmpty()) {
            return new ResponseEntity<>(productNames, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProductDetailsForAllOrders() {
        List<Products> productDetails = orderService.getAllProductDetailsForAllOrders();
        if (!productDetails.isEmpty()) {
            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{order_status}/orders")
    public ResponseEntity<List<Orders>> getDeliveredOrdersWithSameDeliveryDate(@PathVariable String order_status) {
        List<Orders> orders = orderService.getDeliveredOrdersWithSameDeliveryDate(order_status);
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/product_and_product_line_details")
    public ResponseEntity<List<Products>> getProductsByShipmentDate() {
        List<Products> products = orderService.getProductsByShipmentDate();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
