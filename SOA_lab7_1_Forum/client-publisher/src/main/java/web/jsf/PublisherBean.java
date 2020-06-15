package web.jsf;


import jms.JMSSender;
import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class PublisherBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PublisherBean.class.getName());

    private List<String> subjectsList;
    private String subject;
    private String specificUser;
    private String message;

    @Inject
    JMSSender JMSSender;

    public String getSpecificUser() {
        return specificUser;
    }

    public void setSpecificUser(String specificUser) {
        this.specificUser = specificUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSubjectsList() {
        if (subjectsList == null) {
            try {
                subjectsList = JMSSender.getSubjects();
            } catch (Exception e){}
        }
        return subjectsList;
    }

    public void setSubjectsList(List<String> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void registerSubject() {
        JMSSender.addSubject(subject);
        subjectsList = null;
        subject = null;
    }

    public void sendMessage(){
        if(specificUser == null || specificUser.equals("")){
            sendToAll(subject);
        }
    }

    public void sendToAll(String subject){
        JMSSender.sendMessage(subject, message);
    }

    public String delete() {

        String message;

        try {
            JMSSender.removeSubject(subject);
            message = "Entry deleted";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred", e);
            message = "Error occurred on deleting this entry.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));

        subjectsList = null;

        return null;
    }
}
