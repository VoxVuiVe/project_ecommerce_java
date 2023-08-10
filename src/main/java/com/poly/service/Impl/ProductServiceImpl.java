package com.poly.service.Impl;

import com.poly.dao.ProductDAO;
import com.poly.model.Product;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDAO;

    @Override
    public Product save(Product entity) {
        return productDAO.save(entity);
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return (List<Product>) productDAO.findAll(sort);
    }

    @Override
    public Page<Product> findAllPage(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    @Override
    public List<Product> saveAll(List<Product> entities) {
        return (List<Product>) productDAO.saveAll(entities);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productDAO.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return productDAO.existsById(id);
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productDAO.findAll();
    }

    @Override
    public List<Product> findAllById(List<Integer> ids) {
        return (List<Product>) productDAO.findAllById(ids);
    }

    @Override
    public long count() {
        return productDAO.count();
    }

    @Override
    public void deleteById(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public void delete(Product entity) {
        productDAO.delete(entity);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        productDAO.deleteAllById(ids);
    }

    @Override
    public void deleteAll(List<Product> entities) {
        productDAO.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        productDAO.deleteAll();
    }

    @Override
    public Product findProductByID(int id) {
        for (Product product : productDAO.findAll()) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
