package com.blackfiresoft.sheepmall.cart;

import java.util.List;


public interface CartService {

    void addProductToCart(Cart cart);

    void deleteCart(Long[] cartIds);

    List<Cart> getByUserId(Long userId);

    void updateCart(Cart cart);
}
