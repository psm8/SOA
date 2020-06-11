package web.jsf;

import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PublisherBean implements Serializable {

    @EJB(lookup="java:global/client-publisher-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    private List<String> topicList;
    private String newTopic;
    private String specificUser;

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

    private String message;

    public List<String> getTopicList() {
        if (topicList == null) {
            try {
                topicList = JMSService.getTopicsAsString();
            } catch (Exception e){}
        }
        return topicList;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

    public String getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(String newTopic) {
        this.newTopic = newTopic;
    }

    public void registerTopic() throws JMSException {
        JMSService.addTopic(newTopic);
        topicList = null;
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
