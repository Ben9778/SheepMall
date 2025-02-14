package com.blackfiresoft.sheepmall.cart;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImp implements CartService {
    @Resource
    private CartRepository cartRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProductToCart(Cart cart) {
        cartRepository.saveAndFlush(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCart(Long[] cartIds) {
        for (Long CartId : cartIds) {
            cartRepository.deleteById(CartId);
        }
    }

    @Override
    public List<Cart> getByUserId(Long userId) {
        Optional<Users> byId = userRepository.findByIdFetchCart(userId);
        return byId.map(Users::getCartList).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCart(Cart cart) {
        cartRepository.findById(cart.getId()).ifPresent(c -> {
            c.setQuantity(cart.getQuantity());
            c.setTotalPrice(cart.getTotalPrice());
            cartRepository.saveAndFlush(c);
        });
    }
}
