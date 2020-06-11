package mdb.topic;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "desttinationType", propertyValue = "javax.jms.topic"),
            @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/topic/SOA_lab")
        }, mappedName = "SOA_lab")
public class ForumMDB implements MessageListener {
    Map<String, List<String>> userMessages;

    public ForumMDB() { }

    public Map<String, List<String>> getUserMessage() {
        return userMessages;
    }

    public void setUserMessage(Map<String, List<String>> userMessages) {
        this.userMessages = userMessages;
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = null;
        try {
            if (msg instanceof TextMessage) {
                txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();
            }
        } catch (JMSException e) {}
    }
}
