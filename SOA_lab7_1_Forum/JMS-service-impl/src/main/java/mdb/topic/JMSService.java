package mdb.topic;


import workers.ServerWorker;
import workers.SubscriberWorker;
import model.Conversation;
import model.Storage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;




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

    Set<Thread> threads = new HashSet<>();

    @Override
    public void subscribe(String subject, String user){
        storage.addSubscriber(subject, user);
        Runnable r = new SubscriberWorker(connectionFactory, topic, queue, logger, storage, user, subject);
        Thread t = new Thread(r);
        t.setName(user + "#" + subject);
        threads.add(t);
        t.start();
    }

    @Override
    public void unsubscribe(String subject, String user) throws Exception{

        try {
            storage.getSubjectsSubscribers().get(subject).remove(user);
            for (Thread t: threads) {
                if (t.getName().equals(user + "#" + subject)) {
                    t.interrupt();
                    threads.remove(t);
                    break;
                }
            }
            logger.log(Level.INFO,
                    "JMSService.unsubscribe: Unsubscribing from durable subscription: {0}",
                    user + "#" + subject);
        } catch (Exception e) {
            logger.log(Level.INFO,
                    "JMSService.unsubscribe: Exception: {0}", e.toString());
            throw new Exception();
        }
    }

    @Override
    public List<String> getMessages(String subject, String user){
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
