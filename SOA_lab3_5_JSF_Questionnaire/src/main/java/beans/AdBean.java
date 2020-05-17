package beans;

import mock.AdMock;
import model.Ad;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

@Singleton
@Startup
@DependsOn("AdMock")
public class AdBean implements Serializable {
    private List<Ad> adsAndClicks;

    @Inject
    @PostConstruct
    private void startup(AdMock adMock) {
        adsAndClicks = adMock.getAds();
    }

    public AdBean() {
    }

    public List<Ad> getAdsAndClicks() {
        return adsAndClicks;
    }

    public void setAdsAndClicks(List<Ad> adsAndClicks) {
        this.adsAndClicks = adsAndClicks;
    }

    public Ad getRandomAd(){
        return adsAndClicks.get((int) (adsAndClicks.size() * Math.random()));
    }

}
