package com.lab.soa.soap;

import com.lab.soa.data.service.DataService;
import com.lab.soa.service.ActionService;
import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Stateless
@WebService
@SecurityDomain("SOA_2020-egzamin-security-domain")
@WebContext(contextRoot = "/soap", urlPattern = "/dataWebService",
            authMethod = "BASIC", transportGuarantee = "NONE")
public class DataWebService {

    private static final Logger LOG = Logger.getLogger(DataWebService.class);

    @Inject
    DataService dataService;

    @Inject
    ActionService actionService;

    @WebMethod(action = "push")
    @PermitAll
    public long push(@WebParam(name = "data") String data) throws Exception {

        long id = dataService.create(data);

        LOG.info("Starting asynchronous action for data: " + data);
        actionService.action(id);

        return id;
    }

    @WebMethod(action = "check")
    @RolesAllowed("soa2020")
    public boolean check(@WebParam(name = "id") long id) {

        LOG.info("Checking if action is completed for id: " + id);
        return dataService.checkIsCompleted(id);
    }
}
