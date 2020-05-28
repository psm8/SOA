package domain;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;

@Entity
public class MovieEntity {
    private Integer id;
    private String title;
    private String url;

    public MovieEntity() {
    }

    public MovieEntity(String JSONEntity) throws JSONException {
        JSONObject obj = new JSONObject(JSONEntity);
        this.id = obj.getInt("id");
        this.title = obj.getString("title");
        this.url = obj.getString("url");
    }


    public MovieEntity(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) { this.url = url; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MovieEntity)) {
            return false;
        }
        MovieEntity other = (MovieEntity) obj;
        return other.getId().equals(getId());
    }

}
