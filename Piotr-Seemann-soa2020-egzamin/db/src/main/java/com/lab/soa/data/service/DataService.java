package com.lab.soa.data.service;

import com.lab.soa.data.model.Data;


public interface DataService {
    long create(String data) throws Exception;

    boolean checkIsCompleted(long id);

    Data update(long id) throws Exception;
}
