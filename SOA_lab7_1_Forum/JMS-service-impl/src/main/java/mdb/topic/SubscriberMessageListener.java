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

/*@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "desttinationType", propertyValue = "javax.jms.topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/SOA_lab")
        }, mappedName = "SOA_lab")*/
public class SubscriberMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("SubscriberMessageListener");

    private Conversation conversation;

    public SubscriberMessageListener(String id) {
        this.conversation = new Conversation();
        conversation.setId(id);
    }

    public String getId() {
        return conversation.getId();
    }

    public List<String> getMessages(){return conversation.getMessages();}

    @Override
    public void onMessage(Message msg) {
        try{
            if (msg instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();
                Enumeration enumeration = msg.getPropertyNames();
                if (enumeration != null) {
                    while (enumeration.hasMoreElements()) {
                        String key = (String) enumeration.nextElement();
                        if (key.equals("Type")) {
                            if (msg.getObjectProperty("Type").equals(
                                    getId().substring(getId().lastIndexOf("#") + 1))) {
                                conversation.addMessage(txt);
                                logger.log(Level.INFO,
                                        "SubscriberMessageListener.onMessage: "+
                                                getId().substring(getId().lastIndexOf("#") + 1) + ": {0}", txt);
                            }
                            logger.log(Level.INFO,
                                    "SubscriberMessageListener.onMessage: "+
                                            getId().substring(getId().lastIndexOf("#") + 1) + ": {0}", txt);
                            System.out.println(msg.getObjectProperty("Type"));
                        }
                    }
                }
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "SubscriberMessageListener.onMessage: Exception: {0}", e.toString());
        }
    }
}
