package mdb.topic;


import model.Conversation;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.logging.Logger;

/*@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "desttinationType", propertyValue = "javax.jms.topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/SOA_lab")
        }, mappedName = "SOA_lab")*/
public class SubscriberMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("SubscriberMessageListener");

    private String id;
    private Storage storage;

    public SubscriberMessageListener(String id, Storage storage) {
        this.id = id;
        this.storage = storage;
    }

    public String getId() {
        return id;
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = null;
        try {
            if (msg instanceof TextMessage) {
                txtMsg = (TextMessage) msg;
                String test = msg.getPropertyNames().nextElement().toString();
                String txt = txtMsg.getText();
                storage.addConversation(txt, new Conversation());
            }
        } catch (JMSException e) {}
    }
}
