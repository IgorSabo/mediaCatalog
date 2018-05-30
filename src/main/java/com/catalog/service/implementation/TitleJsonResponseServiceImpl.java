package com.catalog.service.implementation;

import com.catalog.business.repository.TitleJsonResponseRepository;
import com.catalog.model.entities.TitleJsonResponse;
import com.catalog.service.TitleJsonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Gile on 4/1/2018.
 */
@Service
@Transactional
public class TitleJsonResponseServiceImpl implements TitleJsonResponseService{

    @Autowired
    TitleJsonResponseRepository titleJsonResponseRepository;

    @Override
    public void saveTitleJsonResponse(TitleJsonResponse response) {
        titleJsonResponseRepository.save(response);
    }
}
