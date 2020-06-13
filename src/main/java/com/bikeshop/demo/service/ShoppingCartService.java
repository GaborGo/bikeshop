package com.bikeshop.demo.service;

import com.bikeshop.demo.model.ShoppingCart;
import com.bikeshop.demo.repo.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public void setShoppingCartRepository(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    public ShoppingCart getShoppingCartById(Long id){ return shoppingCartRepository.findShoppingCartById(id); }

    public ShoppingCart getActiveShoppingCartByUsername(String username, Boolean isCompleted) {
        List<ShoppingCart>shoppingCarts = shoppingCartRepository.findAllByUserUsernameAndCompleted(username,isCompleted);
        if(!shoppingCarts.isEmpty() )
            return shoppingCarts.get(0);

         return new ShoppingCart();
    }

    public List<ShoppingCart> getCompletedCarts(String username, Boolean isCompleted) {
        return shoppingCartRepository.findAllByUserUsernameAndCompletedOrderByDateDesc(username, isCompleted);
    }

    public List<ShoppingCart> getAllShoppingcarts() {
        return shoppingCartRepository.findAll();
    }

    public void saveShoppingCart(ShoppingCart shoppingCart) { shoppingCartRepository.save(shoppingCart);
    }

    public void deleteShoppingCartById(Long id){ shoppingCartRepository.deleteById(id);}

}
