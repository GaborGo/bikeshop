package com.bikeshop.demo.repo;

import com.bikeshop.demo.model.ShoppingCart;
import com.bikeshop.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findUserById(Long id);

    User findUserByUsername(String username);

    User findByUsername(String s);

    User findByEmail(String email);

    List<User> findAllByUsernameContainingOrEmailContaining(String username, String email);
}
