package com.catalog.service;

import com.catalog.model.entities.NotInserted;

import java.util.List;

/**
 * Created by Gile on 9/23/2016.
 */
public interface NotInsertedService {
    public List<NotInserted> getAllResults();
    public NotInserted getOne(int id);
    public void removeFromNotInserted(int id);
}
