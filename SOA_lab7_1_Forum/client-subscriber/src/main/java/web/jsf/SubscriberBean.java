package web.jsf;

import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class SubscriberBean implements Serializable {

    @EJB(lookup="java:global/client-subscriber-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    String user;
    String topic;
    private Map<String, List<String>> topicsMap;
    private List<String> messages;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, List<String>> getTopicsMap() {
        if (topicsMap == null) {
            try {
                topicsMap = JMSService.getTopicSubscribers();
            } catch (Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("couldn't get topic map"));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            }
        }
        return topicsMap;
    }

    public void setTopicsMap(Map<String, List<String>> topicsMap) {
        this.topicsMap = topicsMap;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void loadMessages(){
        this.messages = JMSService.getTopicsAsString();
    }

    public void subscribe(String topic){
        String message = "subscription failed";
        try {
            JMSService.subscribe(topic, user);
        } catch(Exception e){
            message = "subscription successful";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void unsubscribe(String topic){
        String message = "unsubscription failed";
        try {
            JMSService.unsubscribe(topic, user);
        } catch(Exception e){
            message = "unsubscription successful";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
