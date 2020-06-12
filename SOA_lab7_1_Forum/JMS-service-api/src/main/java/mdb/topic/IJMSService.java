package mdb.topic;

import javax.ejb.Remote;
import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

@Remote
public interface IJMSService {
    void sendMessage(String txt);
    void subscribe(String topic, String user) throws Exception;
    void unsubscribe(String topic, String user) throws Exception;
    void addTopic(String topic) throws JMSException;
    void removeTopic(String topic) throws JMSException;
    List<String> getTopicsAsString();
    Map<String, List<String>> getTopicSubscribers();
}
