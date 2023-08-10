package com.poly.dao;

import com.poly.model.Product;
import com.poly.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    @Query(name="getInventoryByCategory")
    public List<Report> getInventoryByCategory();
}
