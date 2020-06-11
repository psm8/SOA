package web.jsf;

import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PublisherBean implements Serializable {

    @EJB(lookup="java:global/client-publisher-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    private List<String> topicsList;
    private String newTopic;
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

    public String getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(String newTopic) {
        this.newTopic = newTopic;
    }

    public void registerTopic() throws JMSException {
        JMSService.addTopic(newTopic);
        topicsList = null;
        newTopic = null;
    }

    public void sendMessage(String topic){
        if(specificUser == null || specificUser.equals("")){
            sendToAll(topic);
        }
    }

    public void sendToAll(String topic){
        JMSService.sendMessage(topic);
    }

    public void delete(){

    }
}
