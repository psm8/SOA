package mdb.topic;

import javax.ejb.Remote;
import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

@Remote
public interface IJMSService {
    void sendMessage(String txt);
    void addTopic(String newTopic) throws JMSException;
    List<String> getTopicsAsString();
    Map<String, List<String>> getTopicSubscribers();
}
