package mdb.topic;


import model.Conversation;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/*@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "desttinationType", propertyValue = "javax.jms.topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/SOA_lab")
        }, mappedName = "SOA_lab")*/
public class SubscriberMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("SubscriberMessageListener");

    private String consumerName;

    @Inject
    Storage storage;

    public SubscriberMessageListener() {
    }

    public SubscriberMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = null;
        try {
            if (msg instanceof TextMessage) {
                txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();
                storage.addConversation(msg.getJMSType(), new Conversation());
            }
        } catch (JMSException e) {}
    }
}
