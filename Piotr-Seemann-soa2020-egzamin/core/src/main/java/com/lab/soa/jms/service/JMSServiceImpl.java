package com.lab.soa.jms.service;


import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.*;

import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;

@Stateless
public class JMSServiceImpl implements JMSService{
    private static final Logger LOG = Logger.getLogger(JMSService.class);

    @Resource(mappedName = "java:/jms/queue/SOA_2020-egzamin")
    private Queue queue;

    @Resource
    private SessionContext sc;
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void sendMessage(String txt) {
        TextMessage message;
        try (JMSContext context = connectionFactory.createContext(AUTO_ACKNOWLEDGE)){
            message = context.createTextMessage();
            message.setText(txt);
            LOG.info("Setting message text to: " + message.getText());
            context.createProducer().send(queue, message);
        } catch (JMSException e) {
            LOG.error(e.toString());
            sc.setRollbackOnly();
        }
    }
}
