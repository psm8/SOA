package model;


import java.util.List;

public class Responses {
    private String name;
    private String email;
    private Integer age;
    private String sex;
    private FemaleSpecificResponses femaleSpecificResponses;
    private MaleSpecificResponses maleSpecificResponses;
    private String education;
    private Integer height;
    private String moneySpend;
    private String shoppingFrequency;
    private List<String> colorPreferred;
    private List<String> typePreferred;

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

    public String getSex() { return sex; }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public FemaleSpecificResponses getFemaleSpecificResponses() {
        if(femaleSpecificResponses == null){
            femaleSpecificResponses = new FemaleSpecificResponses();
        }
        return femaleSpecificResponses;
    }

    public void setFemaleSpecificResponses(FemaleSpecificResponses femaleSpecificResponses) {
        this.femaleSpecificResponses = femaleSpecificResponses;
    }

    public MaleSpecificResponses getMaleSpecificResponses() {
        if(maleSpecificResponses == null){
            maleSpecificResponses = new MaleSpecificResponses();
        }
        return maleSpecificResponses;
    }

    public void setMaleSpecificResponses(MaleSpecificResponses maleSpecificResponses) {
        this.maleSpecificResponses = maleSpecificResponses;
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

    public String getMoneySpend() {
        return moneySpend;
    }

    public void setMoneySpend(String moneySpend) {
        this.moneySpend = moneySpend;
    }

    public String getShoppingFrequency() {
        return shoppingFrequency;
    }

    public void setShoppingFrequency(String shoppingFrequency) {
        this.shoppingFrequency = shoppingFrequency;
    }

    public List<String> getColorPreferred() {
        return colorPreferred;
    }

    public void setColorPreferred(List<String> colorPreferred) {
        this.colorPreferred = colorPreferred;
    }

    public List<String> getTypePreferred() {
        return typePreferred;
    }

    public void setTypePreferred(List<String> typePreferred) {
        this.typePreferred = typePreferred;
    }

    @Override
    public String toString() {
        return "Twoje odpowidzi:" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                "," + femaleSpecificResponses +
                "," + maleSpecificResponses +
                ", education='" + education + '\'' +
                ", height=" + height +
                ", moneySpend='" + moneySpend + '\'' +
                ", shoppingFrequency='" + shoppingFrequency + '\'' +
                ", colorPreferred='" + colorPreferred + '\'' +
                ", typePreferred='" + typePreferred;
    }
}
