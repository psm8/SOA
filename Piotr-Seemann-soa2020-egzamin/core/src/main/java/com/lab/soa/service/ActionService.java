package com.lab.soa.service;

import com.lab.soa.jms.service.JMSService;
import org.jboss.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ActionService {

    private static final Logger LOG = Logger.getLogger(ActionService.class);

    @Inject
    JMSService JMSService;

    @Asynchronous
    public void action(long id) {
        int actionDuration = 5000 + (int) (45000 * Math.random());
        try {
            Thread.sleep(actionDuration);
        }catch (Exception e){LOG.error(e.getMessage());}

        JMSService.sendMessage(String.valueOf(id));
    }
}
