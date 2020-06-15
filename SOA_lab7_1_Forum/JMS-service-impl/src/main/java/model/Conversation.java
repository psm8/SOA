package model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private String id;
    List<String> messages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message){
        if(messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public static boolean checkIfExist(Conversation c, List<Conversation> conversations){
        for( Conversation conversation: conversations){
            if(conversation.equals(c)){
                return true;
            }
        }
        return false;
    }

    public static Conversation getOrCreateConversation(List<Conversation> conversations, String user, String subject){
        for( Conversation conversation: conversations){
            if(conversation.getId().equals(user + "#" + subject)){
                return conversation;
            }
        }
        Conversation c = new Conversation();
        c.setId(user + "#" + subject);
        return c;
    }
}
