package com.catalog.service.implementation;
import com.catalog.business.repository.TitleRepositoryCustom;
import com.catalog.business.utils.Duplicate;
import com.catalog.model.entities.Title;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Gile on 8/30/2016.
 */
@Repository
@Transactional
public class TitleRepositoryImpl implements TitleRepositoryCustom {
    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public List<Title> getResults(String type, int page, int perPage, String genre, String year) {
        Criteria criteria=null;
        Session session = em.unwrap(Session.class);
        criteria=session.createCriteria(Title.class);
        System.out.println("Nadjeni sledeci parametri: type="+type+", page="+page+", perPage="+perPage+", forYear: "+year);
        if(genre!=null && !genre.equals("null") && !genre.equals("undefined"))
        {
            System.out.println("Adding criteria for genre: "+genre);
            criteria.add(Restrictions.like("genre", "%"+genre+"%"));
        }
        if(year!=null && !year.equals("null") && !year.equals("undefined") && !year.equals("NotSelected"))
        {
            System.out.println("Adding criteria for year: "+year);
            criteria.add(Restrictions.like("year", "%"+year+"%"));
        }

        if(type==null)
        {
            //
        }
        else if(type.toLowerCase().equals("movie"))
        {
            criteria.add(Restrictions.eq("IDtype", 1));
        }
        else if(type.toLowerCase().equals("series"))
        {
            criteria.add(Restrictions.eq("IDtype", 2));
        }
        else if(type.toLowerCase().equals("game"))
        {
            criteria.add(Restrictions.eq("IDtype", 4));
        }
        else if(type.toLowerCase().equals("recentlyadded"))
        {
            criteria.add(Restrictions.eq("lastAdded", 1));
        }
        else if(type.toLowerCase().equals("mustwatch"))
        {
            criteria.add(Restrictions.eq("mustWatch", 1));
        }
        else if(type.toLowerCase().equals("favorite"))
        {
            criteria.add(Restrictions.eq("favorite", 1));
        }

        criteria.setFirstResult(page);
        criteria.setMaxResults(perPage);
        ArrayList<Title> list =(ArrayList<Title>)criteria.list();
        return list;
    }

    @Override
    @Transactional
    public List<Object[]> getQuickSearchResults(String word) {
        Session session = em.unwrap(Session.class);
        Criteria criteria=session.createCriteria(Title.class);
        ProjectionList plist = Projections.projectionList();
        plist.add(Projections.property("IDfilm"));
        plist.add(Projections.property("imdbTitle"));
        criteria.add(Restrictions.like("imdbTitle", "%"+word+"%"));
        criteria.setProjection(plist);

        ArrayList<Object[]> list = (ArrayList<Object[]>) criteria.list();

        System.out.println("Size of the results for word "+word+" is "+list.size());
        return list;
    }


    @Override
    @Transactional
    public Number getTotalEntities() {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Title.class);
        Number num = (Number)criteria.setProjection(Projections.rowCount()).uniqueResult();
        return num;
    }


    @Transactional
    @Override
    public HashMap<String, Number> getCountsForParams(String genre, String type, String year) {
        Session session = em.unwrap(Session.class);
        Criteria criteria=null;
        criteria=session.createCriteria(Title.class);


        // TODO Auto-generated method stub
        return null;
    }

    /*	@Transactional
	public void synchronizeTitles()
	{
		//napuni bazu sa novim imenima
		fillDatabase.getNames();
		ArrayList<RawNames> list = rawnamesDao.returnLastadded();
		if(list.size()>0)
		{
			createEntities.createTitlesAlt(list);
		}


		//updating count tables
		System.out.println("Updating count tables");
		cntTablesManipulator.updateCountTables();
	}*/

}
