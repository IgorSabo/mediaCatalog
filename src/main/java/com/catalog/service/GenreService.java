package com.catalog.service;

import com.catalog.model.entities.Genre;

import java.util.List;

/**
 * Created by Gile on 9/26/2016.
 */
public interface GenreService {
    public List<Genre> findAll();
}
