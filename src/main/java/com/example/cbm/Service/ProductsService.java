package com.example.cbm.Service;

import com.example.cbm.Entity.OrderDetails;
import com.example.cbm.Entity.Products;
import com.example.cbm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {


    ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Products addProduct(Products products)
    {
        return productRepository.save(products);
    }
    public void updateBuyPrice(String productCode, BigDecimal newBuyPrice) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setBuyPrice(newBuyPrice);
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found with code: " + productCode);
        }
    }
    public void updateQuantityInStock(String productCode, Short newQuantity) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setQuantityInStock(newQuantity);
            productRepository.save(product);
        }
    }
    public void updateMsrpOfProduct(String productCode, BigDecimal newMsrp) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setMsrp(newMsrp);
            productRepository.save(product);
        }
    }
    public void updateProductVendor(String productCode, String newVendor) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setProductVendor(newVendor);
            productRepository.save(product);
        }
    }


    public void updateProductScale(String productCode, String newScale) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setProductScale(newScale);
            productRepository.save(product);
        }
    }


    public void updateProductName(String productCode, String newName) {
        Products product = productRepository.findByProductCode(productCode);
        if (product != null) {
            product.setProductName(newName);
            productRepository.save(product);
        }
    }
    public Products getProduct(String code)
    {
        return productRepository.findByProductCode(code);
    }

    public Products getProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }

        public List<Products> searchByProductScale(String productScale) {
        List<Products> productList = productRepository.findAll();
            return productList.stream()
                    .filter(product -> product.getProductScale().equalsIgnoreCase(productScale))
                    .collect(Collectors.toList());
        }

    public List<Products> searchByProductVendor(String productVendor) {
        List<Products> productList = productRepository.findAll();
        return productList.stream()
                .filter(product -> product.getProductScale().equalsIgnoreCase(productVendor))
                .collect(Collectors.toList());
    }
    public Long getTotalOrderedQuantityForProduct(String productCode) {
        String queryString = "SELECT SUM(od.quantityOrdered) " +
                "FROM OrderDetails od " +
                "WHERE od.product.productCode = :productCode";

        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("productCode", productCode);

        Long totalOrderedQuantity = query.getSingleResult();
        return totalOrderedQuantity != null ? totalOrderedQuantity : 0L;
    }
    public BigDecimal getTotalSaleForProduct(String productCode) {
        String queryString = "SELECT SUM(od.priceEach * od.quantityOrdered) " +
                "FROM OrderDetails od " +
                "WHERE od.product.productCode = :productCode";

        TypedQuery<BigDecimal> query = entityManager.createQuery(queryString, BigDecimal.class);
        query.setParameter("productCode", productCode);

        BigDecimal totalSale = query.getSingleResult();
        return (totalSale != null) ? totalSale : BigDecimal.ZERO;
    }
    public List<Object[]> getTotalSaleAmountPerProduct() {
        String queryString = "SELECT od.product.productCode, SUM(od.priceEach * od.quantityOrdered) " +
                "FROM OrderDetails od " +
                "GROUP BY od.product.productCode";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        return query.getResultList();
    }
    public List<Products> getHighlyDemandedProducts(int minimumQuantity) {
        String queryString = "SELECT od.product " +
                "FROM OrderDetails od " +
                "WHERE od.quantityOrdered >= :minimumQuantity " +
                "GROUP BY od.product " +
                "ORDER BY SUM(od.quantityOrdered) DESC";

        TypedQuery<Products> query = entityManager.createQuery(queryString, Products.class);
        query.setParameter("minimumQuantity", minimumQuantity);
        query.setMaxResults(10); // Limit the result to the top 10 products

        return query.getResultList();
    }
}
