package model;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Integer id;
    @Column(name="id", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Lob @Basic(fetch=LAZY)
    @Column(name="avatar", columnDefinition="BLOB NOT NULL")
    protected byte[] avatar;
    @ManyToMany
    @JoinTable(name="user_movie",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="movie_id"))
    private List<Movie> movies;

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
