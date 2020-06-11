package web.jsf;

import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
    private Map<String, List<String>> topicsMap;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Map<String, List<String>> getTopicsMap() {
        if (topicsMap == null) {
            try {
                topicsMap = JMSService.getTopicSubscribers();
            } catch (Exception e){}
        }
        return topicsMap;
    }

    public void setTopicsList(Map<String, List<String>> topicsMap) {
        this.topicsMap = topicsMap;
    }

    public void subscribe(){

    }

    public void unsubscribe(){

    }
}
