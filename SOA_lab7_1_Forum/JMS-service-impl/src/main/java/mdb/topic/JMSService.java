package mdb.topic;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.io.InputStreamReader;
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
            TopicSession session = setup();
            MessageProducer publisher = null;
            publisher = session.createProducer(topicExample);
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
    public void subscribe(String topic, String user) throws Exception{
        ForumMDB listener;
        JMSConsumer consumer;

        InputStreamReader inputStreamReader;
        char answer = '\0';

        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Creating consumer for topic");
            context.stop();
            consumer = context.createDurableConsumer(findTopic(topic), "MakeItLast");
            listener = new ForumMDB();
            consumer.setMessageListener(listener);
            System.out.println("Starting consumer");
            context.start();

        } catch (JMSRuntimeException | JMSException e) {
            throw new Exception("Exception occurred: " + e.toString());
        }
    }

    @Override
    public void unsubscribe(String topic, String user) throws Exception{
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Unsubscribing from durable subscription");
            context.unsubscribe("MakeItLast");
        } catch (JMSRuntimeException e) {
            throw new Exception("Exception occurred: " + e.toString());
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
    public void removeTopic(String topic) throws JMSException {

    }

    @Override
    public List<String> getTopicsAsString(){
        return storage.getTopicsAsString();
    }

    @Override
    public Map<String, List<String>> getTopicSubscribers(){
        return storage.getTopicSubscribers();
    }

    private TopicSession setup() throws JMSException{
        connection = connectionFactory.createConnection();
        TopicSession session = (TopicSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        return session;
    }

    private Topic findTopic(String topicName) throws JMSException{
        for(Topic topic : storage.getTopics()){
            if(topic.getTopicName().equals(topicName)){
                return topic;
            }
        }
        return null;
    }

    @PostConstruct
    private void addFakeData(){
        for (int i = 0; i < 10; i++) {
            try {
                this.addTopic("t" + i);
            } catch (JMSException e){}
        }
    }
}
