package com.catalog.service.implementation;

import com.catalog.business.repository.NotInsertedRepository;
import com.catalog.model.entities.NotInserted;
import com.catalog.service.NotInsertedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Gile on 9/23/2016.
 */
@Repository
@Transactional
public class NotInsertedServiceImpl implements NotInsertedService {

    @Autowired
    NotInsertedRepository notInsertedRepository;

    @Override
    @Transactional
    public List<NotInserted> getAllResults() {
        return (List<NotInserted>) notInsertedRepository.findAll();
    }

    @Override
    @Transactional
    public NotInserted getOne(int id) {
        return notInsertedRepository.findOne(id);
    }

    @Override
    @Transactional
    public void removeFromNotInserted(int id) {
        notInsertedRepository.delete(id);
    }

    @Override
    @Transactional
    public void save(NotInserted notInserted) {
        notInsertedRepository.save(notInserted);
    }
}
