package com.blackfiresoft.sheepmall.cart;

import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/cart")
public class CartApi {
    @Resource
    private CartService cartService;

    @PostMapping("/add")
    public ResultEntity addProductToCart(@RequestBody Cart cart){
        if(cart == null){
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        cartService.addProductToCart(cart);
        return ResultEntity.success();
   }

    @GetMapping("/list/{userId}")
    public ResultEntity getCartByUseId(@PathVariable Long userId){
        List<Cart> byUserId = cartService.getByUserId(userId);
        if(byUserId == null){
            ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(byUserId);
    }

    @PostMapping("/delete")
    public ResultEntity deleteCart(@RequestBody Long[]ids){
        cartService.deleteCart(ids);
        return ResultEntity.success();
    }

    @PostMapping("/update")
    public ResultEntity updateCart(@RequestBody Cart cart){
        cartService.updateCart(cart);
        return ResultEntity.success();
    }

}
