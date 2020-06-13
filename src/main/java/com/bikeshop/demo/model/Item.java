package com.bikeshop.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;


    private float price;

    @Column(length = 1000)
    private String description;

    private String category;

    private String brand;

    private boolean active;

    @OneToMany
    private List<ItemImage> images = new ArrayList<>();


    public Item(){}

    public Item(String name, float price, String description, String category, String brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.active = true;
        this.images = new ArrayList<>();
    }

    public Item(String name, float price, String description, String category,String brand, List<ItemImage> itemImage) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.images = itemImage;
        this.brand = brand;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) { this.category = category; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public List<ItemImage> getImages() { return images; }

    public void setImages(List<ItemImage> images) { this.images = images; }

    public List<String> imagesPaths (){
        List<String> imagePaths = new ArrayList<>();
        for(ItemImage itemImage : images)
            imagePaths.add(new String(Base64.getDecoder().decode(itemImage.getImgPath())));

         return imagePaths;
    }

}
