package com.poly.controller;

import com.poly.model.ShoppingCart;
import com.poly.model.Product;
import com.poly.service.ProductService;
import com.poly.service.ShoppingCartService;
import com.poly.utils.ParamService;
import com.poly.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("shopping-cart")
public class ShoppingCartController {
    @Autowired
    ParamService paramService;

    @Autowired
    ProductService productService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    SessionService sessionService;

    @GetMapping
    public boolean checkSecurity() {
        String username = sessionService.get("username");
        if (username != null) {
            return true;
        }
        return false;
    }

    @GetMapping("cart")
    public String viewCart(Model model) {
        if(checkSecurity()) {
            String username = sessionService.get("username");
            if (username != null) {
                model.addAttribute("username", username);
            }
            model.addAttribute("CART_ITEMS", shoppingCartService.getAllCartItems());
            model.addAttribute("TOTAL", shoppingCartService.getAmount());
            return "cart";
        }
        return "redirect:/account/login";
    }

    @GetMapping("add/{id}")
    public String add(@PathVariable("id") Integer id) {
        Product product = productService.findProductByID(id);
        if (product != null) {
            ShoppingCart item = new ShoppingCart();
            item.setProductId(product);
            item.setPhoto(product.getImage());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setQty(1);
            shoppingCartService.add(item);
        }
        return "redirect:/shopping-cart/cart";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        shoppingCartService.remove(id);
        return "redirect:/shopping-cart/cart";
    }

    @GetMapping("clear")
    public String clear() {
        shoppingCartService.clear();
        return "redirect:/shopping-cart/cart";
    }

    @PostMapping("update")
    public String update(@RequestParam("id") Integer proID, @RequestParam("qty") Integer qty) {
        System.out.println(proID + " - " + qty);
        shoppingCartService.update(proID, qty);
        return "redirect:/shopping-cart/cart";
    }
}
