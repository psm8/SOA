package model;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    private Long isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="surname", nullable = false)
    private String surname;
    @Column(name="year", nullable = false)
    private Integer year;;
    @Column(name="price", nullable = false)
    private Double price;

    public Book() {
    }

    public Book(Long isbn, String title, String name, String surname, Integer year, Double price) {
        this.isbn = isbn;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.price = price;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
