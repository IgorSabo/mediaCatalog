package com.catalog.service.implementation;

import com.catalog.business.repository.GenreRepository;
import com.catalog.model.entities.Genre;
import com.catalog.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gile on 9/26/2016.
 */
@Repository
@Transactional
public class GenreServiceImpl implements GenreService{

    @Autowired
    GenreRepository genreRepo;

    @Override
    public List<Genre> findAll() {
        return (ArrayList<Genre>) genreRepo.findAll();
    }
}
