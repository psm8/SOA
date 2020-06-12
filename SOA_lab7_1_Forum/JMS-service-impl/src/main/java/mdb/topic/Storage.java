package mdb.topic;

import model.Conversation;

import javax.ejb.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class Storage {
    private Map<String, List<Conversation>> subjectsConversations;

    public Storage() {
        subjectsConversations = new HashMap<>();
    }

    public Map<String, List<String>> getSubjectsSubscribers() {
        return subjectsConversations.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                   List<String> result = new ArrayList<>();
                   for(Conversation c:e.getValue()){
                       result.add(c.getUser());
                   }
                   return result;
                })
        );
    }

    public List<String> getSubjects(){
        return new ArrayList<>(subjectsConversations.keySet());
    }

    public void addsubjectsConversations(Map<String, List<Conversation>> subjectsConversations) {
        this.subjectsConversations = subjectsConversations;
    }

    public void addsubjectsConversations(String subject, List<Conversation> conversations){
        if(!subjectsConversations.containsKey(subject)) {
            subjectsConversations.put(subject, conversations);
        }
    }

    public void addSubject(String subject){
        if(!subjectsConversations.containsKey(subject)) {
            subjectsConversations.put(subject, new ArrayList<>());
        }
    }

    public void removeSubject(String subject){
        subjectsConversations.remove(subject);
    }

    public void addConversation(String subject, Conversation conversation){
        if(subjectsConversations.containsKey(subject)) {
            subjectsConversations.get(subject).add(conversation);
        }
    }
}