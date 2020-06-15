package mdb.topic;


import model.Conversation;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("QueryMessageListener");

    private Conversation conversation;

    public QueryMessageListener(Conversation conversation) {
        this.conversation = conversation;
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
                conversation.addMessage(txt);
                logger.log(Level.INFO,
                        "QueryMessageListener.onMessage: "+
                                getId().substring(getId().lastIndexOf("#") + 1) + ": {0}", txt);
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "QueryMessageListener.onMessage: Exception: {0}", e.toString());
        }
    }
}