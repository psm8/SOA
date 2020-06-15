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


    @EJB(lookup="java:global/ear-1.0-SNAPSHOT/JMS-service-impl-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    private String user;
    private String subject;
    private Map<String, List<String>> subjectsMap;
    private List<String> messages;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, List<String>> getSubjectsMap() {
        try {
            subjectsMap = JMSService.getSubjectsSubscribers();
        } catch (Exception e){
            String message = "couldn't get messages";
            facesAddMessage(message);
        }
        return subjectsMap;
    }

    public void setSubjectsMap(Map<String, List<String>> subjectsMap) {
        this.subjectsMap = subjectsMap;
    }

    public List<String> getMessages() {
        loadMessages();
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void loadMessages(){
        String message = "success";
        try {
            messages = JMSService.getMessages(subject, user);
        } catch(Exception e){
            message = "couldn't get messages";
        }
        facesAddMessage(message);
    }

    public void subscribe(){
        String message;
        List<String> topicSubsciber = subjectsMap.get(subject);
        if(topicSubsciber.contains(user)){
            message = "you are already a subscriber";
        } else {
            message = "subscription successful";
            try {
                JMSService.subscribe(subject, user);
            } catch (Exception e) {
                message = "subscription failed";
            }
        }
        facesAddMessage(message);
    }

    public void unsubscribe(){
        String message;
        List<String> topicSubsciber = subjectsMap.get(subject);
        if(!topicSubsciber.contains(user)){
            message = "you are not a subscriber";
        } else {
            message = "unsubscription successful";
            try {
                JMSService.unsubscribe(subject, user);
            } catch (Exception e) {
                message = "unsubscription failed";
            }
        }
        facesAddMessage(message);
    }

    private void facesAddMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
