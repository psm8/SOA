package mdb.topic;

import javax.ejb.Singleton;
import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class Storage {
    private Map<Topic, ArrayList<String>> topicsSubscibers;
    private Map<String, ArrayList<String>> userMessages;

    public Storage() {
        topicsSubscibers = new HashMap<>();
        userMessages = new HashMap<>();
    }

    public Map<Topic, ArrayList<String>> getTopicSubscibers() {
        return topicsSubscibers;
    }

    public void addTopicsSubscibers(Map<Topic, ArrayList<String>> topicsSubscibers) {
        this.topicsSubscibers = topicsSubscibers;
    }

    public Map<String, ArrayList<String>> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(Map<String, ArrayList<String>> userMessages) {
        this.userMessages = userMessages;
    }

    public void addTopicsSubscribers(Topic topic, ArrayList<String> subscibers){
        if(!topicsSubscibers.containsKey(topic)) {
            topicsSubscibers.put(topic, subscibers);
        }
    }

    public void addTopic(Topic topic){
        if(!topicsSubscibers.containsKey(topic)) {
            topicsSubscibers.put(topic, new ArrayList<>());
        }
    }

    public void addSubscriber(Topic topic, String subscriber){
        if(topicsSubscibers.containsKey(topic)) {
            topicsSubscibers.get(topic).add(subscriber);
        }
    }
}