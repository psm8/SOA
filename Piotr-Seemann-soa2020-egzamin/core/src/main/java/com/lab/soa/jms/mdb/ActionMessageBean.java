package com.lab.soa.jms.mdb;

import com.lab.soa.data.service.DataService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue =   "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/SOA_2020-egzamin")
})
public class ActionMessageBean implements MessageListener {

    private static final Logger LOG = Logger.getLogger(ActionMessageBean.class);

    @Inject
    DataService dataService;

    public void onMessage(Message msg) {
        TextMessage txtMsg = null;
        try {
            if (msg instanceof TextMessage) {
                txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();
                dataService.update(Long.parseLong(txt));
            }
        } catch (Exception e) {
            LOG.error((e.getMessage()));
        }
    }
}