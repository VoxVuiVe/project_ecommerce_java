package com.poly.dao;


import com.poly.model.ShoppingCart;
import com.poly.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartDAO extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT ci FROM ShoppingCart ci WHERE ci.productId = :product")
    ShoppingCart findByIdProduct(@Param("product") Product product);

}