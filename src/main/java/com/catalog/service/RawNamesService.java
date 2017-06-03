package com.catalog.service;

import com.catalog.model.entities.RawNames;
import com.catalog.model.entities.Title;

import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
public interface RawNamesService {
    public List<RawNames> findByLastAdded(int value);
    public RawNames save(RawNames title);
    public void delete(RawNames title);
    public void delete(int IDtitle);

    void updateRawNames(RawNames wiredTitle);

    public RawNames find(int id);


}
