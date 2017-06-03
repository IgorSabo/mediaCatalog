package com.catalog.service.implementation;

//import com.example.dao.rawnames.RawNamesDAO;
import com.catalog.business.repository.TitleRepository;
import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.Title;
import com.catalog.service.TitleService;
//import com.example.utils.CntTablesManipulator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*import com.example.utils.CntTablesManipulator;
import com.example.utils.CreateEntities;
import com.example.utils.FillDatabase;*/

@Repository
@Transactional
public class TitleServiceImpl implements TitleService {

//	@PersistenceContext
//    private EntityManager em;
	
	/*@Autowired
	private CreateEntities createEntities;*/
	
	/*@Autowired 
	private FillDatabase fillDatabase;*/
	
//	@Autowired
//	private RawNamesDAO rawnamesDao;
//
//	@Autowired
//	private CntTablesManipulator cntTablesManipulator;

	@Autowired
	TitleRepository titleRepository;

	
	@Override
	@Transactional
	public void createTitle(Title title) {

		titleRepository.save(title);
//		Session session = em.unwrap(Session.class);
//		session.save(title);

	} 

	@Override
	@Transactional
	public void updateTitle(Title title) {
		System.out.println(title.getActors());
		System.out.println(title.getDescription());
		System.out.println(title.getImdbTitle());
		System.out.println(title.getRawName());
		System.out.println(title.getLocation());
		System.out.println(title.getQuality());
		System.out.println(title.getPicture());
		System.out.println(title.getGenre());
		System.out.println(title.getIDtype());
		System.out.println(title.getYear().replace("â","–").replace("\u2013","–").replace("â??", "–"));
		
		//Session session = em.unwrap(Session.class);
		
		Title titleFromDb = titleRepository.findOne(title.getIDfilm());

				//session.get(Title.class, title.getIDfilm());
		titleFromDb.setActors(title.getActors());
		titleFromDb.setDescription(title.getDescription());
		titleFromDb.setImdbTitle(title.getImdbTitle());
		titleFromDb.setRawName(title.getRawName());
		titleFromDb.setLocation(title.getLocation());
		titleFromDb.setQuality(title.getQuality());
		titleFromDb.setPicture(title.getPicture());
		titleFromDb.setGenre(title.getGenre().replace("-", ""));
		titleFromDb.setYear(title.getYear().replace("â","–").replace("\u2013","–").replace("â??", "–"));
		titleFromDb.setIDtype(title.getIDtype());

		titleRepository.save(titleFromDb);
		//session.update(titleFromDb);
	}

	@Override
	@Transactional
	public Title showInfo(int titleId) {
		//Session session = em.unwrap(Session.class);

		Title title = titleRepository.findOne(titleId);
		return title;
	}

	@Override
	@Transactional
	public void addToMustWatch(int titleId) {
		//Session session = em.unwrap(Session.class);
		Title title = titleRepository.findOne(titleId);
		title.setMustWatch(1);
		titleRepository.save(title);

	}

	@Override
	@Transactional
	public void addToFavorite(int titleId) {
		//Session session = em.unwrap(Session.class);
		//Title title = (Title) session.get(Title.class, titleId);
		Title title = titleRepository.findOne(titleId);
		title.setFavorite(1);
		titleRepository.save(title);
	}

	@Override
	@Transactional
	public void addToIncorrect(int titleId) {
		//Session session = em.unwrap(Session.class);
		//Title title = (Title) session.get(Title.class, titleId);
		Title title = titleRepository.findOne(titleId);
		title.setIncorrect(1);
		titleRepository.save(title);

	}

/**/

	

	


	@Transactional
	@Override
	public void deleteTitle(int titleId) {
		//Session session = em.unwrap(Session.class);
		Title title = titleRepository.findOne(titleId);
		titleRepository.delete(title);
		//session.delete(title);

	}

	@Override
	public HashMap<String, Number> getCountsForParams(String genre, String type, String year) {

		return titleRepository.getCountsForParams(genre, type, year);
	}

	@Override
	public List<Object[]> getQuickSearchResults(String word) {
		return titleRepository.getQuickSearchResults(word);
	}

	@Override
	public List<Title> getResults(String type, int page, int perPage, String genre, String year) {
		return titleRepository.getResults(type, page, perPage, genre, year);
	}

	@Override
	public Number getTotalEntities() {
		return titleRepository.getTotalEntities();
	}

	@Override
	public Title getTitle(int IDtitle) {
		return titleRepository.findOne(IDtitle);
	}

	@Override
	public List<Duplicate> getDuplicates() {

		List<Duplicate> duplicates = new ArrayList();
		List<Object[]> list = titleRepository.getDuplicates();
		for(Object[] obj : list){
			duplicates.add(new Duplicate((int) obj[1], (String) obj[3], (String) obj[2], (long) obj[0]));
		}

		return duplicates;
	}

	@Override
	public List<Title> findByImdbTitle(String title) {
		return titleRepository.findByImdbTitle(title);
	}


}
