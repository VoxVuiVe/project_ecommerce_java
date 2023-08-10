package com.poly.service;

import com.poly.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart getCartItemById(Long cartItemId);

    List<ShoppingCart> getAllCartItems();

    void add(ShoppingCart item);

    void remove(int id);

    ShoppingCart update(int proID, int qty);

    void clear();

    int getCount();

    double getAmount();
}
