package model;

public class Ad {
    private String link;
    private String title;
    private String image;
    private Integer clicks;

    public Ad(String link, String image, String title) {
        this.link = link;
        this.image = image;
        this.title = title;
        this.clicks = 0;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }


}
