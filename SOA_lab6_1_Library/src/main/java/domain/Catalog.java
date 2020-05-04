package domain;

import javax.persistence.*;

@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="book_isbn")
    private Book book;
    @Column(name = "is_rented", nullable = false)
    private Boolean isRented;

    public Catalog() {
    }

    public Catalog(Book book, Boolean isRented) {
        this.book = book;
        this.isRented = isRented;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getIsRented() {
        return isRented;
    }

    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }
}
