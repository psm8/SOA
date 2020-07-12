package com.lab.soa.data.service;

import com.lab.soa.data.dao.DataDao;
import com.lab.soa.data.model.Data;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

        return obj.getMessageReceivedDate() != null;
    }

    @Override
    public Data update(long id) throws Exception {
        Data obj = dataDao.get(id);

        return dataDao.update(obj);
    }
}
