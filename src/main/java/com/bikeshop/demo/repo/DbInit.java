package com.bikeshop.demo.repo;

import com.bikeshop.demo.model.CartItem;
import com.bikeshop.demo.model.Item;
import com.bikeshop.demo.model.ShoppingCart;
import com.bikeshop.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DbInit implements CommandLineRunner {


private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private CartItemRepository cartItemRepository;

    public DbInit(UserRepository userRepository, ItemRepository itemRepository, ShoppingCartRepository shoppingCartRepository,
                  PasswordEncoder passwordEncoder, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public void run(String... args)   {
        User usher2 = new User("admin", passwordEncoder.encode("admin"), "ADMIN,USER", "admin@b.admincom");
       // User usher = new User("usher", passwordEncoder.encode("pass"), "USER", "a@b.com");
//        Item item = new Item("fork", 325.0f, "cat: fork, br:Trek", "forks","Trek");
//        Item item2 = new Item("bike", 4800.0f, "cat: ppp, br:cube", "ppp","Cube");
//        Item item3 = new Item("break", 4800.0f, "cat:wheels,br:Giant",  "wheels","Giant");
//        Item item4 = new Item("rearwheel", 4800.0f, "cat:breaks,br:Xfact",  "breaks","Xfact");
//        Item item5 = new Item("trek", 4800.0f, "cat: wheels, br:specialized",  "wheels", "Specialized");
//        Item item6 = new Item("bike2", 4800.0f, "cat:zzz, br: Santa C", "zzz", "Santa Cruz");
//        Item item7 = new Item("bike3", 4800.0f, "cat: bbb, Br: Santa", "bbb", "Santa Cruz");
//        Item item8 = new Item("bike4", 4800.0f, "cat:aaa, br: Specialized", "aaa", "Specialized");

        if(userRepository.findByUsername("admin") == null)
        userRepository.save(usher2);
//        itemRepository.save(item);
//
//        itemRepository.save(item2);
//        itemRepository.save(item3);
//        itemRepository.save(item4);
//        itemRepository.save(item5);
//        itemRepository.save(item6);
//        itemRepository.save(item7);
//        itemRepository.save(item8);

    }
}
