package jms;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;

@Singleton
public class JMSSender {
    static final Logger logger = Logger.getLogger("JMSSender");

    @Resource(mappedName = "java:/jms/topic/SOA_lab")
    private Topic topic;
    @Resource(mappedName = "java:/jms/queue/SOA_lab")
    private Queue queue;
    @Resource
    private SessionContext sc;
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    List<String> subjects = new ArrayList<>();

    public List<String> getSubjects() {
        return subjects;
    }

    public void sendMessage(String subject, String txt) {
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setStringProperty("Type", subject);
            message.setText(txt);
            logger.log(Level.INFO,
                    "JMSSender.sendMessage: Setting message text to: {0}",
                    message.getText());
            context.createProducer().send(topic, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "JMSSender.sendMessage: Exception: {0}", e.toString());
            sc.setRollbackOnly();
        }
    }

    public void sendMessageToOne(String specificUser, String subject, String txt) {
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setStringProperty("Type", specificUser + "#" + subject);
            message.setText(txt);
            logger.log(Level.INFO,
                    "JMSSender.sendMessage: Setting message text to: {0}",
                    message.getText());
            context.createProducer().send(queue, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "JMSSender.sendMessage: Exception: {0}", e.toString());
            sc.setRollbackOnly();
        }
    }

    public void addSubject(String subject){
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setStringProperty("Operation", "Add");
            message.setStringProperty("filtering", "Operation");
            message.setText(subject);
            logger.log(Level.INFO,
                    "JMSSender.sendMessage: Setting message text to: {0}",
                    message.getText());
            context.createProducer().send(queue, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "JMSSender.sendMessage: Exception: {0}", e.toString());
            sc.setRollbackOnly();
            return;
        }
        subjects.add(subject);
    }

    public void removeSubject(String subject){
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setStringProperty("Operation", "Remove");
            message.setStringProperty("filtering", "Operation");
            message.setText(subject);
            logger.log(Level.INFO,
                    "JMSSender.sendMessage: Setting message text to: {0}",
                    message.getText());
            context.createProducer().setProperty("Operation", "Operation").send(queue, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "JMSSender.sendMessage: Exception: {0}", e.toString());
            sc.setRollbackOnly();
            return;
        }
        subjects.remove(subject);
    }

    @PostConstruct
    private void addFakeData(){
        for (int i = 0; i < 10; i++) {
            this.addSubject("t" + i);
            for (int j = 0; j < 5; j++) {
                this.sendMessage("t"+i,"mes"+j + "t" + i);
            }
        }
    }
}
