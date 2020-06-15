package mdb.topic;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;

@Singleton
public class JMSService implements IJMSService, Serializable {

    static final Logger logger = Logger.getLogger("JMSService");

    @Resource(mappedName = "java:/jms/topic/SOA_lab")
    private  Topic topic;
    @Resource(mappedName = "java:/jms/queue/SOA_lab")
    private Queue queue;
    @Resource
    private SessionContext sc;
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    Storage storage;

    List<JMSConsumer> activeConsumers = new ArrayList<>();


    @Override
    public void subscribe(String subject, String user) throws Exception{
        JMSConsumer consumer;
        SubscriberMessageListener subscriberMessageListener;

        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            context.stop();
            context.setClientID(user + subject);
            consumer = context.createDurableConsumer(topic , "test"/*user + subject*/);
            subscriberMessageListener = new SubscriberMessageListener(user + subject, storage);
            consumer.setMessageListener(subscriberMessageListener);
            context.start();
            TextMessage message = context.createTextMessage();
            message.setStringProperty("Type", subject);
            message.setText("wiadomosc");
            context.createProducer().send(topic, message);
            activeConsumers.add(consumer);
            logger.log(Level.INFO,
                    "JMSService.subscribe: Creating consumer for topic: {0}",
                    user + subject);
        } catch (JMSRuntimeException e) {
            logger.log(Level.INFO,
                    "JMSService.subscribe: Exception: {0}", e.toString());
            throw new Exception();
        }
    }

    @Override
    public void unsubscribe(String subject, String user) throws Exception{
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)) {
            logger.log(Level.INFO,
                    "JMSService.unsubscribe: Unsubscribing from durable subscription: {0}",
                    user + subject);
            context.unsubscribe("test"/*user + subject*/);
        } catch (JMSRuntimeException e) {
            logger.log(Level.INFO,
                    "JMSService.unsubscribe: Exception: {0}", e.toString());
            throw new Exception();
        }
    }

    @Override
    public void addSubject(String subject){
        storage.addSubject(subject);
    }

    @Override
    public void removeSubject(String subject){
        storage.removeSubject(subject);
    }

    @Override
    public List<String> getSubjects(){
        return storage.getSubjects();
    }

    @Override
    public Map<String, List<String>> getSubjectsSubscribers(){
        return storage.getSubjectsSubscribers();
    }

    private String findSubject(String subject){
        for(String s : storage.getSubjects()){
            if(s.equals(subject)){
                return subject;
            }
        }
        return null;
    }

    @PostConstruct
    private void worker(){
        Runnable r = new ServerWorker(connectionFactory, queue, logger, storage);
        new Thread(r).start();
    }
}
