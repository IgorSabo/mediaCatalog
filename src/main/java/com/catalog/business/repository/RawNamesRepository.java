package com.catalog.business.repository;

import com.catalog.model.entities.RawNames;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
@Transactional
public interface RawNamesRepository extends CrudRepository<RawNames, Integer>,  RawNamesRepositoryCustom {
    public List<RawNames> findByLastAdded(int value);
}
