package com.poly.service.Impl;

import com.poly.dao.CategoryDAO;
import com.poly.model.Category;
import com.poly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public Category save(Category entity) {
        return categoryDAO.save(entity);
    }

    @Override
    public List<Category> saveAll(List<Category> entities) {
        return (List<Category>) categoryDAO.saveAll(entities);
    }

    @Override
    public Optional<Category> findById(String s) {
        return categoryDAO.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return categoryDAO.existsById(s);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryDAO.findAll();
    }

    @Override
    public List<Category> findAllById(List<String> strings) {
        return (List<Category>) categoryDAO.findAllById(strings);
    }

    @Override
    public long count() {
        return categoryDAO.count();
    }

    @Override
    public void deleteById(String s) {
        categoryDAO.deleteById(s);
    }

    @Override
    public void delete(Category entity) {
        categoryDAO.delete(entity);
    }

    @Override
    public void deleteAllById(List<String> strings) {
        categoryDAO.deleteAllById(strings);
    }

    @Override
    public void deleteAll(List<Category> entities) {
        categoryDAO.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        categoryDAO.deleteAll();
    }
}
