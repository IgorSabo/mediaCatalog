package com.catalog.service.implementation;

import com.catalog.business.repository.TypeRepository;
import com.catalog.model.entities.Type;
import com.catalog.service.GenreService;
import com.catalog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Created by Gile on 9/26/2016.
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    public ArrayList<Type> findAll() {
        return  (ArrayList<Type>) typeRepository.findAll();
    }
}
