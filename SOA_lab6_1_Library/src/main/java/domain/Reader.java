package domain;

import javax.persistence.*;

@Entity
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;

    public Reader() {
    }

    public Reader(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
