package com.lab.soa.soap;

import com.lab.soa.data.dao.DataDao;
import com.lab.soa.data.model.Data;
import com.lab.soa.jms.service.JMSService;
import org.jboss.security.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Stateless
@WebService
@SecurityDomain("SOA_2020-egzamin-security-domain")
@WebContext(contextRoot = "/soap", urlPattern = "/dataWebService")
public class dataWebService {

    @Inject
    DataDao dataDao;

    @Inject
    JMSService JMSService;

    @WebMethod(action = "push")
    public long push(@WebParam(name = "data") String data) throws Exception {

        Data obj = dataDao.create(new Data(data));
        JMSService.sendMessage(data);

        return obj.getId();
    }

    @WebMethod(action = "check")
    public boolean check(@WebParam(name = "id") long id) {

        return false;
    }

}
