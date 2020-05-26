package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Lob @Basic(fetch=LAZY)
    @Column(name="avatar", columnDefinition="BLOB NOT NULL")
    private byte[] avatar;
    @ManyToMany(cascade=CascadeType.MERGE, fetch = EAGER)
    @JoinTable(name="user_movie",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="movie_id"))
    private List<Movie> movies;

    public User() {
    }

    public User(String name, Integer age, byte[] avatar, List<Movie> movies) {
        this.name = name;
        this.age = age;
        this.avatar = avatar;
        this.movies = movies;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
