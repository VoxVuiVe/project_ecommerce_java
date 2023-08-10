package com.poly.dao;

import com.poly.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, String> {

}
