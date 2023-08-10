package com.poly.service;

import com.poly.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category save(Category entity);

    List<Category> saveAll(List<Category> entities);

    Optional<Category> findById(String s);

    boolean existsById(String s);

    List<Category> findAll();

    List<Category> findAllById(List<String> strings);

    long count();

    void deleteById(String s);

    void delete(Category entity);

    void deleteAllById(List<String> strings);

    void deleteAll(List<Category> entities);

    void deleteAll();
}
