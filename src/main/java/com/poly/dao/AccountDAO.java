package com.poly.dao;

import com.poly.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDAO extends CrudRepository<Account, String> {

}
