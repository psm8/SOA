package beans;

import model.Ad;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class AdViewBean implements Serializable {
    @Inject
    private AdBean adBean;
    private Ad randomAdAndClick;

    @PostConstruct
    void init(){
        this.randomAdAndClick = adBean.getRandomAd();
    }

    public Ad getRandomAdAndClick() {
        return randomAdAndClick;
    }

    public void setRandomAdAndClick(Ad randomAdAndClick) {
        this.randomAdAndClick = randomAdAndClick;
    }

    public void onClick() throws IOException{
        randomAdAndClick.setClicks(randomAdAndClick.getClicks()+1);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(randomAdAndClick.getLink());
    }
}
