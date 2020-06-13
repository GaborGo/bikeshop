package com.bikeshop.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private boolean isEnabled;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "user")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    public User(){}

    public User(String username, String password, String roles, List<ShoppingCart> shoppingCarts, String email ) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.shoppingCart = new ShoppingCart();
        this.isEnabled = true;
    }

    public User(String username, String password, String roles, String email) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.shoppingCart=new ShoppingCart();
        this.isEnabled = true;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<ShoppingCart> getShoppingCarts() { return shoppingCarts; }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public boolean isEnabled() { return isEnabled; }

    public void setEnabled(boolean enabled) { isEnabled = enabled; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            System.out.println("ROLES LIST SIZE:  " + roles);
            return Arrays.asList(this.roles.split(","));}
        System.out.println("ROLES LIST SIZE:  < !!!" + roles);
        return new ArrayList<>();
    }

    public Float getSumOfTotalOrders(){
        Float totalOrders = 0f;
        for(ShoppingCart shoppingCart : shoppingCarts)
            totalOrders += shoppingCart.calcCartPrice();
        return totalOrders;
    }

    public List<ShoppingCart> getCartsOrderedByIdDesc (){
        List<ShoppingCart> orderedCarts = shoppingCarts;
        for(ShoppingCart cart : orderedCarts){
            if( !cart.isCompleted() ) {
                orderedCarts.remove(cart);
                break;
            }
        }

        orderedCarts.sort(Comparator.comparing(ShoppingCart::getId));
        Collections.reverse(orderedCarts);
        return orderedCarts;
    }

    public List<ShoppingCart> getCartsOrderedByPriceDesc (){
        List<ShoppingCart> orderedCarts = shoppingCarts;
        orderedCarts.sort(Comparator.comparing(ShoppingCart::getPrice));
        Collections.reverse(orderedCarts);
        return orderedCarts;
    }

    }

