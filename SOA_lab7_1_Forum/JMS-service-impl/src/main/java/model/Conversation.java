package model;

import java.util.List;

public class Conversation {
    private String user;
    List<String> messages;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message){
        messages.add(message);
    }
}
