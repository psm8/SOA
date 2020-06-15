package mdb.topic;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Inject
    Storage storage;


    @Override
    public void subscribe(String subject, String user) throws Exception{
        JMSConsumer consumer;
        SubscriberMessageListener subscriberMessageListener;

        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            context.stop();
            context.setClientID(user + subject);
            consumer = context.createDurableConsumer(topic , user + "#" + subject);
            subscriberMessageListener = new SubscriberMessageListener(user + "#" + subject);
            consumer.setMessageListener(subscriberMessageListener);
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
                    user + "#" + subject);
            context.unsubscribe(user + "#" + subject);
        } catch (JMSRuntimeException e) {
            logger.log(Level.INFO,
                    "JMSService.unsubscribe: Exception: {0}", e.toString());
            throw new Exception();
        }
    }

    @Override
    public List<String> getMessages(String subject, String user) throws Exception{
        JMSConsumer consumer;
        SubscriberMessageListener subscriberMessageListener;

        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            context.stop();
            context.setClientID(user + subject);
            consumer = context.createDurableConsumer(topic , user + "#" + subject);
            subscriberMessageListener = new SubscriberMessageListener(user + "#" + subject);
            consumer.setMessageListener(subscriberMessageListener);
            context.start();
            Thread.sleep(300);
            logger.log(Level.INFO,
                    "JMSService.subscribe: Creating consumer for topic: {0}",
                    user + subject);
        } catch (JMSRuntimeException e) {
            logger.log(Level.INFO,
                    "JMSService.subscribe: Exception: {0}", e.toString());
            throw new Exception();
        }

        return subscriberMessageListener.getMessages();
    }

    @Override
    public Map<String, List<String>> getSubjectsSubscribers(){
        return storage.getSubjectsSubscribers();
    }

    @Override
    public String getNotification(String subject, String user) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit((new SubscriberWorker(connectionFactory, queue, logger, user + "#" +subject)));
        Runnable r = new ServerWorker(connectionFactory, queue, logger, storage);
        return  future.get();
    }

    @PostConstruct
    private void worker(){
        Runnable r = new ServerWorker(connectionFactory, queue, logger, storage);
        new Thread(r).start();
    }
}
