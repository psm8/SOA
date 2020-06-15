package mdb.topic;

import model.Conversation;

import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;

class SubscriberWorker implements Runnable {

    ConnectionFactory connectionFactory;
    Topic topic;
    Queue queue;
    Logger logger;
    Storage storage;
    String user;
    String subject;

    public SubscriberWorker(ConnectionFactory connectionFactory, Topic topic, Queue queue, Logger logger, Storage storage, String user, String subject) {
        this.connectionFactory = connectionFactory;
        this.topic = topic;
        this.queue = queue;
        this.logger = logger;
        this.storage = storage;
        this.user = user;
        this.subject = subject;
    }

    @Override
    public void run() {
        JMSConsumer consumer;
        SubscriberMessageListener subscriberMessageListener;

        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            context.stop();
            context.setClientID(user + "#" + subject);
            System.out.println("Type='" + subject +"'");
            consumer = context.createDurableConsumer(topic , user + "#" + subject,"Type='" + subject +"'", false);
            Conversation conversation = Conversation.getOrCreateConversation(storage.getConversations(), user, subject);
            subscriberMessageListener = new SubscriberMessageListener(conversation);
            consumer.setMessageListener(subscriberMessageListener);
            JMSConsumer consumerQuery = context.createConsumer(queue, "Type='" + user + "#" + subject + "'");
            consumerQuery.setMessageListener(new QueryMessageListener(conversation));
            storage.getConversations().add(conversation);
            context.start();
            Thread.sleep(2000);
            logger.log(Level.INFO,
                    "JMSService.subscribe: Creating consumer for topic: {0}",
                    user + subject);
        } catch (Exception e) {
            logger.log(Level.INFO,
                    "JMSService.subscribe: Exception: {0}", e.toString());
        }
    }
}