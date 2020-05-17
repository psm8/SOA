import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public class QuestionnaireBean {
    private String name;
    private String email;
    private Integer age;
    private String sex;
    private String education;
    private Integer height;
    private Integer waistSizeMale;
    private Integer chestSize;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWaistSizeMale() {
        return waistSizeMale;
    }

    public void setWaistSizeMale(Integer waistSizeMale) {
        this.waistSizeMale = waistSizeMale;
    }

    public Integer getChestSize() {
        return chestSize;
    }

    public void setChestSize(Integer chestSize) {
        this.chestSize = chestSize;
    }
}
