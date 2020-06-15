package workers;


import listeners.ServerMessageListener;
import model.Storage;

import javax.jms.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;


public class ServerWorker implements Runnable {

    ConnectionFactory connectionFactory;
    Queue queue;
    Logger logger;
    Storage storage;

    public ServerWorker(ConnectionFactory connectionFactory, Queue queue, Logger logger, Storage storage) {
        this.connectionFactory = connectionFactory;
        this.queue = queue;
        this.logger = logger;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
                context.stop();
                JMSConsumer consumer = context.createConsumer(queue,"filtering='Operation'");
                consumer.setMessageListener(new ServerMessageListener(storage));
                context.start();
                while (true) {
                    Thread.sleep(5000);
                }
            } catch (JMSRuntimeException e) {
                logger.log(Level.INFO,
                        "JMSService.ServerWorker: Exception: {0}", e.toString());
            }
        } catch (Exception e) {}
    }
}