package com.catalog.service.implementation;

import com.catalog.business.repository.RawNamesRepository;
import com.catalog.model.entities.RawNames;
import com.catalog.model.entities.Title;
import com.catalog.service.RawNamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
@Service
@Transactional
public class RawNamesServiceImpl implements RawNamesService {

    @Autowired
    RawNamesRepository rawNamesRepository;

    @Override
    public List<RawNames> findByLastAdded(int value) {
        return rawNamesRepository.findByLastAdded(1);
    }

    @Override
    public RawNames save(RawNames title){
        return rawNamesRepository.save(title);
    }

    @Override
    public void delete(RawNames title) {
        rawNamesRepository.delete(title);
    }

    @Override
    public void delete(int id) {
        rawNamesRepository.delete(id);
    }

    @Override
    public void updateRawNames(RawNames wiredTitle) {

        RawNames tmp = rawNamesRepository.findOne(wiredTitle.getIDname());
        tmp.setLastAdded(wiredTitle.getLastAdded());
        tmp.setLocation(wiredTitle.getLocation());
        tmp.setName(wiredTitle.getName());
        tmp.setType(wiredTitle.getType());
        rawNamesRepository.save(tmp);
    }

    @Override
    public RawNames find(int id) {
        return rawNamesRepository.findOne(id);
    }

}
