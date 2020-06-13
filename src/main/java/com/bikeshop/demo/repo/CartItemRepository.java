package com.bikeshop.demo.repo;

import com.bikeshop.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

CartItem getById(Long id);

}
