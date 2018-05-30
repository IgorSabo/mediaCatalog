package com.catalog.business.repository;

import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.Title;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Transactional
public interface TitleRepositoryCustom {

	public HashMap<String, Number> getCountsForParams(String genre, String type, String year);
	
	//public void synchronizeTitles();
	
	public Set<Object[]> getQuickSearchResults(String word);

	public Set<Title> getResults(String type, int page, int perPage, String genre, String year);

	public Number getTotalEntities();

}
