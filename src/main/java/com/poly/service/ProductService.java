package com.poly.service;

import com.poly.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product entity);

    List<Product> findAll(Sort sort);

    Page<Product> findAllPage(Pageable pageable);

    List<Product> saveAll(List<Product> entities);

    Optional<Product> findById(Integer id);

    boolean existsById(Integer id);

    List<Product> findAll();

    List<Product> findAllById(List<Integer> ids);

    long count();

    void deleteById(Integer id);

    void delete(Product entity);

    void deleteAllById(List<Integer> ids);

    void deleteAll(List<Product> entities);

    void deleteAll();

    public Product findProductByID(int id);
}
