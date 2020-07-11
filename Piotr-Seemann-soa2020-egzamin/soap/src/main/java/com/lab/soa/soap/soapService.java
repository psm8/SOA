package com.lab.soa.soap;

import com.lab.soa.data.dao.DataDao;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@Stateless
@WebService
@WebContext(contextRoot = "/soa", urlPattern = "/soap")
public class soapService {

/*    @EJB
    DataDao dataDao;*/

    @WebMethod(action = "push")
    public void push(String data){

    }

    @WebMethod(action = "check")
    public void check(long id) {

    }

}
