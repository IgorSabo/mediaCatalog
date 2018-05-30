package com.catalog.service;

import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.RawNames;
import com.catalog.model.entities.Title;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    public Set<Object[]> getQuickSearchResults(String word);

    public Set<Title> getResults(String type, int page, int perPage, String genre, String year);

    public Number getTotalEntities();

    public Title getTitle(int IDtitle);

    public Set<Duplicate> getDuplicates();

    public Set<Title> findByImdbTitle(String title);

    void processNewTitles(HttpSession session);

    String returnProgress(HttpSession session);

}
