package com.catalog.business.repository;

import com.catalog.model.entities.NotInserted;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gile on 9/23/2016.
 */
@Transactional
public interface NotInsertedRepository extends CrudRepository<NotInserted, Integer> {

}
