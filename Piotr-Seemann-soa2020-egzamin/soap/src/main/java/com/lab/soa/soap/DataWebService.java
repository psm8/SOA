package com.lab.soa.soap;

import com.lab.soa.data.dao.DataDao;
import com.lab.soa.data.model.Data;
import com.lab.soa.data.service.DataService;
import com.lab.soa.jms.service.JMSService;
import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Stateless
@WebService
@SecurityDomain("SOA_2020-egzamin-security-domain")
@WebContext(contextRoot = "/soap", urlPattern = "/dataWebService")
public class DataWebService {

    private static final Logger LOG = Logger.getLogger(DataWebService.class);

    @Inject
    DataService dataService;

    @Inject
    JMSService JMSService;

    @WebMethod(action = "push")
    public long push(@WebParam(name = "data") String data) throws Exception {

        long id = dataService.create(data);
        action(id);

        return id;
    }

    @WebMethod(action = "check")
    public boolean check(@WebParam(name = "id") long id) {

        return dataService.checkIsCompleted(id);
    }

    @Asynchronous
    private void action(long id) {
        int actionDuration = 5000 + (int) (45000 * Math.random());
        try {
            Thread.sleep(actionDuration);
        }catch (Exception e){LOG.error(e.getMessage());}

        JMSService.sendMessage(String.valueOf(id));
    }

}
