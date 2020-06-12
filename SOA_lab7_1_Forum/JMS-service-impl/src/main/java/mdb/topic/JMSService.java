package mdb.topic;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
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
    @Resource
    private SessionContext sc;
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    Storage storage;

    @Override
    public void sendMessage(String subject, String txt) {
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setStringProperty("Type", subject);
            message.setText(txt);
            logger.log(Level.INFO,
                    "JMSService.sendMessage: Setting message text to: {0}",
                    message.getText());
            context.createProducer().send(topic, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "JMSService.sendMessage: Exception: {0}", e.toString());
            sc.setRollbackOnly();
        }
    }

    @Override
    public void subscribe(String subject, String user) throws Exception{
        JMSConsumer consumer;

        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            context.setClientID(user + subject);
            consumer = context.createDurableConsumer(topic , user + subject);
            consumer.setMessageListener(new SubscriberMessageListener(user));
            logger.log(Level.INFO,
                    "JMSService.subscribe: Creating consumer for topic: {0}",
                    subject + user);
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
                    subject + user);
            context.unsubscribe(user + subject);
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
    private void addFakeData(){
        for (int i = 0; i < 10; i++) {
            this.addSubject("t" + i);
        }
    }
}
