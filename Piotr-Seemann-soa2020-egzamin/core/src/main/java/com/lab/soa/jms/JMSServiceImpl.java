package com.lab.soa.jms;


import org.jboss.logging.Logger;

import javax.ejb.Stateless;

@Stateless
public class JMSServiceImpl implements JMSService{
    private static final Logger LOG = Logger.getLogger(JMSService.class);
}
