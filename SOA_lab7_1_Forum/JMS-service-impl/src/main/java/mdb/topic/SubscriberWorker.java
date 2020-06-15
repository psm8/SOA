package mdb.topic;

import javax.jms.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;

class SubscriberWorker implements Callable<String> {

    ConnectionFactory connectionFactory;
    Queue queue;
    Logger logger;
    String id;

    public SubscriberWorker(ConnectionFactory connectionFactory, Queue queue, Logger logger, String id) {
        this.connectionFactory = connectionFactory;
        this.queue = queue;
        this.logger = logger;
        this.id = id;
    }

    @Override
    public String call() {
        SubscriberMessageListener subscriberMessageListener = new SubscriberMessageListener(id);
        try {
            try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
                context.stop();
                JMSConsumer consumer = context.createConsumer(queue);
                consumer.setMessageListener(subscriberMessageListener);
                context.start();
                while (subscriberMessageListener.getMessages() == null) {
                    Thread.sleep(5000);
                }
            } catch (JMSRuntimeException e) {
                logger.log(Level.INFO,
                        "JMSService.ServerWorker: Exception: {0}", e.toString());
            }
        } catch (Exception e) {}
        return subscriberMessageListener.getMessages().get(0);
    }
}