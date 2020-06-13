package com.bikeshop.demo.model;

import javax.persistence.*;
import java.io.File;
import java.util.Base64;
import java.util.Base64.Encoder;

@Entity
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    public Item item;

    private byte[] imgPath;

    public ItemImage() {}

    public ItemImage(Item item, byte[] imgPath) {
        this.item = item;
        this.imgPath = imgPath;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }

    public byte[] getImgPath() {
        return imgPath;
    }

    public void setImgPath(byte[] imgPath) {
        this.imgPath = imgPath;
    }

    //Decode coded byte[] to string
    public String getDecodedImgPath(){ ;
        return new String(Base64.getDecoder().decode(imgPath));
    }

}
