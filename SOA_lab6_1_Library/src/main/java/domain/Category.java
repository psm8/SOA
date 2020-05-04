package domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "name", nullable = false)
    String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
