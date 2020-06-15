package mdb.topic;


import model.Conversation;

import javax.ejb.Singleton;
import java.util.*;

@Singleton
public class Storage {
    private Map<String, List<String>> subjectsSubscribers;
    private List<Conversation> conversations;

    public Storage() {
        subjectsSubscribers = new HashMap<>();
        conversations = new ArrayList<>();
    }

    public Map<String, List<String>> getSubjectsSubscribers() {
        return subjectsSubscribers;
    }

    public List<String> getSubjects(){
        return new ArrayList<>(subjectsSubscribers.keySet());
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void addSubjectsSubscribers(Map<String, List<String>> subjectsConversations) {
        this.subjectsSubscribers = subjectsConversations;
    }

    public void addSubjectsSubscribers(String subject, List<String> subscribers){
        if(!subjectsSubscribers.containsKey(subject)) {
            subjectsSubscribers.put(subject, subscribers);
        }
    }

    public void addSubject(String subject){
        if(!subjectsSubscribers.containsKey(subject)) {
            subjectsSubscribers.put(subject, new ArrayList<>());
        }
    }

    public void removeSubject(String subject){
        subjectsSubscribers.remove(subject);
    }

    public void addSubscriber(String subject, String subscriber){
        if(!subjectsSubscribers.containsKey(subject)) {
            List<String> subscribers = new ArrayList<>();
            subscribers.add(subscriber);
            subjectsSubscribers.put(subject, subscribers);
        }
        subjectsSubscribers.get(subject).add(subscriber);
    }
}