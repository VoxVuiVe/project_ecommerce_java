package com.poly.service.Impl;

import com.poly.dao.ShoppingCartDAO;
import com.poly.model.ShoppingCart;
import com.poly.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Override
    public ShoppingCart getCartItemById(Long cartItemId) {
        return shoppingCartDAO.findById(cartItemId).orElse(null);
    }

    @Override
    public List<ShoppingCart> getAllCartItems() {
        return shoppingCartDAO.findAll();
    }

    @Override
    public void add(ShoppingCart item) {
        ShoppingCart shoppingCart = shoppingCartDAO.findByIdProduct(item.getProductId());
        if (shoppingCart == null) {
            shoppingCartDAO.save(item);
        } else {
            shoppingCart.setQty(shoppingCart.getQty() + item.getQty());
            shoppingCartDAO.save(shoppingCart);
        }
    }

    @Override
    public void remove(int id) {
        shoppingCartDAO.deleteById((long) id);
    }

    @Override
    public ShoppingCart update(int proID, int qty) {
        ShoppingCart shoppingCart = shoppingCartDAO.findById((long) proID).orElse(null);
        if (shoppingCart != null) {
            shoppingCart.setQty(qty);
            return shoppingCartDAO.save(shoppingCart);
        }
        return null;
    }

    @Override
    public void clear() {
        shoppingCartDAO.deleteAll();
    }

    @Override
    public int getCount() {
        return shoppingCartDAO.findAll().size();
    }

    @Override
    public double getAmount() {
        List<ShoppingCart> shoppingCarts = shoppingCartDAO.findAll();
        double amount = 0;
        for (ShoppingCart item : shoppingCarts) {
            amount += item.getQty() * item.getPrice();
        }
        return amount;
    }
}
