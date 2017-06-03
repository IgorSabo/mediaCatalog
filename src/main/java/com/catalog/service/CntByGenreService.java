package com.catalog.service;

import com.catalog.business.repository.CntByGenreRepository;
import com.catalog.model.entities.CntByGenre;

import java.util.List;

/**
 * Created by Gile on 1/2/2017.
 */
public interface CntByGenreService {
    public void updateRecord(int IDType, int count);

    public List<CntByGenre> getCntForAllGenres();
}
