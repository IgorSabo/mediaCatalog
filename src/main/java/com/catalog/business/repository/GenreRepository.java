package com.catalog.business.repository;

import com.catalog.model.entities.Genre;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gile on 9/26/2016.
 */
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
