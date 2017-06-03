package com.catalog.service;

import com.catalog.model.entities.CntByType;

import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
public interface CntByTypeService {
    public void updateRecord(int IDType, int count);

    public List<CntByType> getCntForAllTypes();
}
