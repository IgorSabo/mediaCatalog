package com.catalog.business.utils;


import com.catalog.model.entities.*;
import com.catalog.service.CntByGenreService;
import com.catalog.service.CntByTypeService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

@Repository
public class CntTablesManipulator {
	
		@PersistenceContext
		private EntityManager em;

	 	@Autowired
	 	private CntByGenreService cntByGenreService;

	 	@Autowired
	 	private CntByTypeService cntByTypeService;

	    static HashMap<String, String> typeMap = new HashMap<String, String>();
	    static HashMap<String, String> genreMap = new HashMap<String, String>();

	    @Transactional
	    public void updateCountTables()
	    {
	    	try
	    	{
	        	initTypes();
	            initGenres();


	            Session session = em.unwrap(Session.class);
	            Number num;
	            Criteria criteria;
	            ArrayList<CntByGenre> listCntByGenre = (ArrayList<CntByGenre>) cntByGenreService.getCntForAllGenres();
	            ArrayList<CntByType> listCntByType = (ArrayList<CntByType>) cntByTypeService.getCntForAllTypes();
	            //update count table for genres
	            for(Entry<String, String> entry : genreMap.entrySet())
	            {
	            	criteria = session.createCriteria(Title.class).add( Restrictions.like("genre", "%"+entry.getKey()+"%"));
	            	num = (Number)criteria.setProjection(Projections.rowCount()).uniqueResult();
	            	System.out.println("Number of titles for "+entry.getKey()+": "+num.intValue());
	            	for(CntByGenre genreStats : listCntByGenre)
	            	{
	            		if(genreStats.getIDgenre()==Integer.valueOf(entry.getValue()))
	            		{
	            			if(genreStats.getTotal()!= num.intValue())
	            			{
	            				System.out.println("Found "+genreStats.getTotal()+" values in table for genre: "+entry.getKey()+", updating to "+num.intValue());
                                cntByGenreService.updateRecord(genreStats.getIDgenre(), num.intValue());
	            			}
	            		}
	            	}
	            }



	            //update count table for types
	            for(Entry<String, String> entry : typeMap.entrySet())
	            {
	            	criteria = session.createCriteria(Title.class).add( Restrictions.eq("IDtype", Integer.valueOf(entry.getKey())));
	            	num = (Number)criteria.setProjection(Projections.rowCount()).uniqueResult();
	            	System.out.println("Number of titles for "+entry.getValue()+": "+num.intValue());

	            	for(CntByType typeStats : listCntByType)
	            	{
	            		if(typeStats.getIDtype()==Integer.valueOf(entry.getKey()))
	            		{
	            			if(typeStats.getTotal()!=num.intValue())
	            			{
	            				System.out.println("Found "+typeStats.getTotal()+" values for type: "+entry.getValue()+", updating to "+num.intValue());
                                cntByTypeService.updateRecord(typeStats.getIDtype(), num.intValue());
	            			}
	            		}
	            	}
	            }
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }


	    @Transactional
	    public void initTypes() {
	        System.out.println("Initializing types...");
	        try {
	        	Session session = em.unwrap(Session.class);
	        	ArrayList<Type> types = (ArrayList<Type>) session.createCriteria(Type.class).list();
	        	//transaction.commit();
	        	for(Type tmp : types)
	            {
	                typeMap.put(String.valueOf(tmp.getIDtype()), tmp.getName());
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("Done.");
	    }

	    @Transactional
	    public  void initGenres() {
	        System.out.println("Initializing genres...");
	        Session session = em.unwrap(Session.class);
	        try {
	        	ArrayList<Genre> genres = (ArrayList<Genre>) session.createCriteria(Genre.class).list();
	        	//transaction.commit();
	            for (Genre tmp : genres) {
	                genreMap.put(tmp.getName(), String.valueOf(tmp.getIDgenre()));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

}
