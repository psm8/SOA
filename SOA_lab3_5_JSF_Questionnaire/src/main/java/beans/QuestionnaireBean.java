package beans;

import model.Responses;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class QuestionnaireBean implements Serializable {
    private Responses responses;

    public Responses getResponses() {
        return responses;
    }

    public void setResponses(Responses responses) {
        this.responses = responses;
    }

    @PostConstruct
    public void init() {
        responses = new Responses();
    }
}
