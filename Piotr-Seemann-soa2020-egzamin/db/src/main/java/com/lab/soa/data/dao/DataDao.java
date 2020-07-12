package com.lab.soa.data.dao;

import com.lab.soa.data.model.Data;


public interface DataDao {

    Data create(Data data) throws Exception;

    Data get(long id);

    Data update(Data data) throws Exception;
}
