package domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "name", unique = true, nullable = false)
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ( !(obj instanceof Category) ) return false;

        final Category category = (Category) obj;

        if ( !category.getName().equals( getName() ) ) return false;

        return true;
    }
}
