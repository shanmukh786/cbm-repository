package com.example.cbm.Controller;

import com.example.cbm.Entity.OrderDetails;
import com.example.cbm.Service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orderdetails")
public class OrderDetailsController {
    OrderDetailsService orderDetailsService;
    @Autowired
    public void setOrderDetailsService(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/max/price")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsForMaxPriceOrder() {
        List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsForMaxPriceOrder();
        if (!orderDetails.isEmpty()) {
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/")
    public ResponseEntity<String> addOrderDetails(@RequestBody OrderDetails orderDetails)
    {
        orderDetailsService.saveOrderDetails(orderDetails);
        return new ResponseEntity<String>("Record Created succefully",HttpStatus.CREATED);
    }
    @PutMapping(value="/{order_number}/{product_code}/{quantity_ordered}")
    public ResponseEntity<String> updateQuantityOrdered(
            @PathVariable Integer order_number,@PathVariable String product_code,
            @PathVariable Integer quantity_ordered) {

        try {
            orderDetailsService.updateQuantityOrdered(order_number,product_code, quantity_ordered);
            return new ResponseEntity<>("Quantity updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{order_number}")
    public ResponseEntity<List<OrderDetails>> searchByOrderNumber(@PathVariable Integer order_number) {
        List<OrderDetails> orderDetails = orderDetailsService.searchByOrderNumber(order_number);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetails);
    }
    @GetMapping("/{orderNumber}/total")
    public ResponseEntity<String> getTotalAmountByOrderNumber(@PathVariable("orderNumber") Integer orderNumber) {
        BigDecimal totalAmount = orderDetailsService.getTotalAmountByOrderNumber(orderNumber);
        return new ResponseEntity<>("Total amount: " + totalAmount, HttpStatus.OK);
    }
    @GetMapping("/total_sale")
    public ResponseEntity<BigDecimal> getTotalSale() {
        BigDecimal totalSale = orderDetailsService.getTotalSale();
        return new ResponseEntity<>(totalSale, HttpStatus.OK);
    }
    @GetMapping("/count/{orderNumber}")
    public int getOrderDetailsCountByOrderNumber(@PathVariable int orderNumber) {
        return orderDetailsService.getOrderDetailsCountByOrderNumber(orderNumber);
    }

}
