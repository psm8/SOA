package domain;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    private Long isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="author_id")
    private Author author;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="category_name")
    private Category category;

    public Book() {
    }

    public Book(Long isbn, String title, Author author, Category category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", category=" + category +
                '}';
    }
}
