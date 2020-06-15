package mdb.topic;


import model.Conversation;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;


@Stateless
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
        Runnable r = new SubscriberWorker(connectionFactory, topic, queue, logger, storage, user, subject);
        new Thread(r).start();
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
        List<String> messages = Conversation.getOrCreateConversation(storage.getConversations(), user, subject).getMessages();
        return messages;
    }

    @Override
    public Map<String, List<String>> getSubjectsSubscribers(){
        return storage.getSubjectsSubscribers();
    }

    @PostConstruct
    private void worker(){
        Runnable r = new ServerWorker(connectionFactory, queue, logger, storage);
        new Thread(r).start();
    }
}
