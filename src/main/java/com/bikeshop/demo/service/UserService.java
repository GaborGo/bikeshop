package com.bikeshop.demo.service;

import com.bikeshop.demo.model.ShoppingCart;
import com.bikeshop.demo.model.User;
import com.bikeshop.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64.Encoder;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){ this.userRepository = userRepository;}

    // requests
    public User getUser(String username){ return userRepository.findUserByUsername(username); }

    public User getUser(Long id){ return userRepository.findUserById(id); }

    public List<User> getAllUsers(){ return userRepository.findAll(); }

    public List<User> getSearchedUser(String search){ return userRepository.findAllByUsernameContainingOrEmailContaining(search,search);}


    //save
    public void saveUser(User user) { userRepository.save(user);}


    //register
    public String registerUser(User userToRegister) {         //check if registration data is valid(unique)
        User checkUsername = userRepository.findByUsername(userToRegister.getUsername());
        User checkEmail = userRepository.findByEmail(userToRegister.getEmail());

        if( checkUsername != null && checkEmail != null)
            return "wrongUsernameAndEmail";

        if( checkUsername != null )
            return "wrongUsername";

        if( checkEmail != null )
            return "wrongEmail";

        userToRegister.setEnabled(true);                       //encode password, save user
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(encodedPassword);
        userToRegister.setRoles("USER");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userToRegister);
        userToRegister.setShoppingCart(shoppingCart);

        userToRegister.setEnabled(true);
        userRepository.save(userToRegister);

        return "ok";
    }
}
