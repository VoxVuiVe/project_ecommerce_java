package com.poly.service;


import com.poly.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account save(Account entity);

    List<Account> saveAll(List<Account> entities);

    Optional<Account> findById(String id);

    boolean existsById(String id);

    List<Account> findAll();

    List<Account> findAllById(List<String> ids);

    long count();

    void deleteById(String id);

    void delete(Account entity);

    void deleteAllById(List<String> ids);

    void deleteAll(List<Account> entities);

    void deleteAll();
}
