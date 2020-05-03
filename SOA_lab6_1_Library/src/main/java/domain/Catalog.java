package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(mappedBy="catalog")
    private List books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getBooks() {
        return books;
    }

    public void setBooks(List books) {
        this.books = books;
    }
}
