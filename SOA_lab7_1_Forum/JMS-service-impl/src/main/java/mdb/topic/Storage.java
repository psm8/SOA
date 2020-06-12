package mdb.topic;

import model.Conversation;

import javax.ejb.Singleton;
import javax.jms.*;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class Storage {
    private Map<Topic, List<Conversation>> topicsConversations;

    public Storage() {
        topicsConversations = new HashMap<>();
    }

    public Map<String, List<String>> getTopicSubscribers() {
        return topicsConversations.entrySet().stream().collect(Collectors.toMap(
                e -> {
                    String key;
                    try {
                        key = e.getKey().getTopicName();
                    } catch (JMSException ex){
                        key = "exception";
                    }
                    return key;
                },
                e -> {
                   List<String> result = new ArrayList<>();
                   for(Conversation c:e.getValue()){
                       result.add(c.getUser());
                   }
                   return result;
                })
        );
    }

    public List<String> getTopicsAsString(){
        return topicsConversations.keySet().stream().map(
                e -> {
                    String key;
                    try {
                        key = e.getTopicName();
                    }catch (JMSException ex){
                        key = "exception";
                        }
                    return key;
        }).collect(Collectors.toList());
    }

    public List<Topic> getTopics(){
        return new ArrayList<>(topicsConversations.keySet());
    }

    public void addTopicsConversations(Map<Topic, List<Conversation>> topicsConversations) {
        this.topicsConversations = topicsConversations;
    }

    public void addTopicsConversations(Topic topic, List<Conversation> conversations){
        if(!topicsConversations.containsKey(topic)) {
            topicsConversations.put(topic, conversations);
        }
    }

    public void addTopic(Topic topic){
        if(!topicsConversations.containsKey(topic)) {
            topicsConversations.put(topic, new ArrayList<>());
        }
    }

    public void addConversation(Topic topic, Conversation conversation){
        if(topicsConversations.containsKey(topic)) {
            topicsConversations.get(topic).add(conversation);
        }
    }
}