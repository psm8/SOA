package mock;

import model.Ad;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.List;

@Singleton
@Startup
public class AdMock {
    private List<Ad> ads;

    public AdMock() {
        this.ads = Arrays.asList(
                new Ad("http://stackoverflow.com",
                        "https://ephratacommunitychurch.com/wp-content/uploads/2017/02/200x200px-crown.jpg",
                        "title 1"),
                new Ad("http://github.com",
                        "https://zeroeco.com.au/wp-content/uploads/2019/03/ZeroEcoLogo-200x200px-Home.png",
                        "title 2"));
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
