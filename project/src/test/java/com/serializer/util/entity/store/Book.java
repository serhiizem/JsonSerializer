package com.serializer.util.entity.store;

public class Book extends Item {
    private Integer numberOfPages;

    public Book(String title, Double price, Integer numberOfPages) {
        super(title, price);
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "numberOfPages=" + numberOfPages +
                '}';
    }
}
