package com.catalog.service;

import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.Title;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gile on 8/30/2016.
 */
public interface TitleService {

    public void createTitle(Title title);

    public void updateTitle(Title title) ;

    public Title showInfo(int titleId);

    public void addToMustWatch(int titleId);

    public void addToFavorite(int titleId);

    public void addToIncorrect(int titleId);

    public void deleteTitle(int titleId);

    public HashMap<String, Number> getCountsForParams(String genre, String type, String year);

    //public void synchronizeTitles();

    public List<Object[]> getQuickSearchResults(String word);

    public List<Title> getResults(String type, int page, int perPage, String genre, String year);

    public Number getTotalEntities();

    public Title getTitle(int IDtitle);

    public List<Duplicate> getDuplicates();

    public List<Title> findByImdbTitle(String title);

}
