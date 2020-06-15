package mdb.topic;

import model.Conversation;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("SubscriberMessageListener");

    private Conversation conversation;

    public QueryMessageListener(String id) {
        this.conversation = new Conversation();
        conversation.setId(id);
    }

    public String getId() {
        return conversation.getId();
    }

    public List<String> getMessages(){return conversation.getMessages();}

    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();
                Enumeration enumeration = msg.getPropertyNames();
                if (enumeration != null) {
                    while (enumeration.hasMoreElements()) {
                        String key = (String) enumeration.nextElement();
                        if(key.equals("Operation")) {



                        }
                    }
                }
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "ServerMessageListener.onMessage: Exception: {0}", e.toString());
        }
    }
}