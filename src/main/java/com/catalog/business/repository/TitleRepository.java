package com.catalog.business.repository;

import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TitleRepository extends CrudRepository<Title, Integer>, TitleRepositoryCustom {

    @Query("select count(t.imdbTitle), t.IDfilm, t.imdbTitle, t.location from Title t GROUP BY t.imdbTitle having count(t.imdbTitle) > 1")
    public List<Object[]> getDuplicates();

    public List<Title> findByImdbTitle(String title);


}
