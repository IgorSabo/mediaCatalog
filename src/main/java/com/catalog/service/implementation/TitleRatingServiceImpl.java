package com.catalog.service.implementation;

import com.catalog.business.repository.TitleRatingRepository;
import com.catalog.model.entities.TitleRating;
import com.catalog.service.TitleRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Gile on 4/7/2018.
 */
@Service
@Transactional
public class TitleRatingServiceImpl implements TitleRatingService {

    @Autowired
    TitleRatingRepository titleRatingRepository;

    @Override
    public void saveRating(TitleRating rating) {
        titleRatingRepository.save(rating);
    }
}
