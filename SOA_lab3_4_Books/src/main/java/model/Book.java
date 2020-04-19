package model;

import model.enumeration.BookType;
import model.enumeration.Currency;

public class Book {
    private int id;
    private String name;
    private String author;
    private BookType bookType;
    private double price;
    private Currency currency;
    private int pageNo;

    public Book(int id, String name, String author, BookType bookType, double price, Currency currency, int pageNo) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.bookType = bookType;
        this.price = price;
        this.currency = currency;
        this.pageNo = pageNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
