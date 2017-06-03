package com.catalog.service.implementation;

import com.catalog.business.repository.CntByGenreRepository;
import com.catalog.business.repository.CntByTypeRepository;
import com.catalog.model.entities.CntByGenre;
import com.catalog.model.entities.CntByType;
import com.catalog.service.CntByGenreService;
import com.catalog.service.CntByTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
@Repository
@Transactional
public class CntByTypeServiceImpl implements CntByTypeService {

    @Autowired
    CntByTypeRepository ctByTypeRepository;


    @Override
    public void updateRecord(int IDType, int count) {
        CntByType typeInfo = ctByTypeRepository.findOne(IDType);
        typeInfo.setTotal(count);
        ctByTypeRepository.save(typeInfo);
    }

    @Override
    public List<CntByType> getCntForAllTypes() {
        return (List<CntByType>) ctByTypeRepository.findAll() ;
    }
}
