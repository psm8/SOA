package domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class UserEntity implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
    private byte[] avatar;
    private List<MovieEntity> movies;

    public UserEntity() {
    }

    public UserEntity(String JSONEntity) throws JSONException {
        JSONObject obj = new JSONObject(JSONEntity);
        this.id = obj.getInt("id");
        this.name = obj.getString("name");
        this.age = obj.getInt("age");
        this.avatar = (byte[]) obj.get("avatar");
        this.movies = (List<MovieEntity>) obj.get("movies");
    }

    public UserEntity(String name, Integer age, byte[] avatar, List<MovieEntity> movies) {
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

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }
}
