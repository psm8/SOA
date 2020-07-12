package com.lab.soa.data.service;

import com.lab.soa.data.dao.DataDao;
import com.lab.soa.data.model.Data;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

@Stateless
public class DataServiceImpl implements DataService {

    private static final Logger LOG = Logger.getLogger(DataDao.class);

    @Inject
    DataDao dataDao;

    @Override
    public long create(String data) throws Exception {
        Data obj = dataDao.create(new Data(data));

        return obj.getId();
    }

    @Override
    public boolean checkIsCompleted(long id) {
        Data obj = dataDao.get(id);

        if(obj == null){ return false; }

        return obj.getMessageReceivedDate() != null && !obj.getMessageReceivedDate().equals(obj.getCreateDate());
    }

    @Override
    public Data update(long id) throws Exception {
        Data obj = dataDao.get(id);
        obj.setMessageReceivedDate(new Date());

        return dataDao.update(obj);
    }
}
