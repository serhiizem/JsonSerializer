package com.serializer.util.entity.store;

import com.serializer.annotations.JsonIgnore;

public class Item {

    private String title;

    @JsonIgnore
    private Double price;

    public Item(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
