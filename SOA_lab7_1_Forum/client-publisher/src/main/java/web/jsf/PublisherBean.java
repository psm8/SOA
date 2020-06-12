package web.jsf;

import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class PublisherBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PublisherBean.class.getName());

    @EJB(lookup="java:global/client-publisher-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    private List<String> topicsList;
    private String topic;
    private String specificUser;
    private String message;

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

    public List<String> getTopicsList() {
        if (topicsList == null) {
            try {
                topicsList = JMSService.getTopicsAsString();
            } catch (Exception e){}
        }
        return topicsList;
    }

    public void setTopicsList(List<String> topicsList) {
        this.topicsList = topicsList;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void registerTopic() throws JMSException {
        JMSService.addTopic(topic);
        topicsList = null;
        topic = null;
    }

    public void sendMessage(String topic){
        if(specificUser == null || specificUser.equals("")){
            sendToAll(topic);
        }
    }

    public void sendToAll(String topic){
        JMSService.sendMessage(topic);
    }

    public String delete() {

        String message;

        try {
            JMSService.removeTopic(topic);
            message = "Entry deleted";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "Error occurred on deleting this entry.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));

        topicsList = null;

        return null;
    }
}
