package com.lab.soa.data.dao;

import com.lab.soa.data.model.Data;

import java.util.List;

public interface DataDao {

    Data create(Data data) throws Exception;

    Data get(long id);

    List<Data> getAll();

    Data update(Data data) throws Exception;

    void delete(Data data) throws Exception;
}
