package mdb.topic;

import javax.ejb.Remote;
import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.List;

@Remote
public interface IJMSService {
    void sendMessage(String txt);
    void addTopic(String newTopic) throws JMSException;
    List<String> getTopicsAsString() throws JMSException;
}
