package com.example.sanja.itunestoppaidapps;

import java.io.Serializable;

/**
 * Created by sanja on 2/23/2017.
 */

public class App implements Serializable{

    String imageUrl, name, price;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "App{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
