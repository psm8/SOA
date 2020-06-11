package mdb.topic;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Stateless
public class JMSService implements IJMSService, Serializable {
    @Resource(mappedName = "java:/jms/topic/SOA_lab")
    private Topic topicExample;
    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;
    private Connection connection;

    @Inject
    Storage storage;

    @Override
    public void sendMessage(String txt) {
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = null;
            publisher = session.createProducer(topicExample);
            connection.start();
            TextMessage message = session.createTextMessage(txt);
            publisher.send(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addTopic(String newTopic) throws JMSException {
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(newTopic);
        storage.addTopic(topic);
        connection.close();
    }

    @Override
    public List<String> getTopicsAsString(){
        return storage.getTopics();
    }

    @Override
    public Map<String, List<String>> getTopicSubscribers(){
        return storage.getTopicSubscribers();
    }
}
