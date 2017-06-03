package com.catalog.business.utils;


import com.catalog.model.entities.*;
import com.catalog.service.MediaFolderService;
import com.catalog.service.RawNamesService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gile
 */

@Component
public class FillDatabase 
{
    ArrayList<String> filePaths=new ArrayList<String>();
    ArrayList<Object> existingRecordsList = new ArrayList<Object>();
    boolean recordsExist=true;
    
	@PersistenceContext
    private EntityManager em;
    
    @Autowired
    private RawNamesService rawNamesService;

    @Autowired
    private MediaFolderService mediaFolderService;
    
    public FillDatabase() 
    {

    }

    @Transactional
    public boolean checkIfRecordsExist()
    {
        boolean var=true;
        try
        {
        	Session session = em.unwrap(Session.class);
        	/*Transaction tr= session.getTransaction();
        	tr.begin();*/
        	
            //set all lastAdded flags to 0
        	
            String query="update RawNames set lastAdded=0";
            int updatedRawNames=em.createQuery(query).executeUpdate();
            //int updatedRawNames=session.createQuery(query).executeUpdate();
            
            query="update Title set lastAdded=0";
            int updatedTitle=em.createQuery(query).executeUpdate();
            //int updatedTitle=session.createQuery(query).executeUpdate();
           
            System.out.println("updatedRawNames="+updatedRawNames+" ,updatedTitle="+updatedTitle);
            //tr.commit();
            
            ArrayList<RawNames> list = (ArrayList<RawNames>) session.createCriteria(RawNames.class).list();
            if(list.size()>0)
            {
                var=true;
            }
            else
            {
                var=false;
            }
            
        }
        catch(Exception e)
        {
        	
            e.printStackTrace(); 
        }
        
        return var; 
    }
    @Transactional
    public void getNames()
    {
        try
        {        	
            //any existing records
            recordsExist = checkIfRecordsExist();
            
            if(recordsExist)
            {
                System.out.println("Loading existing records...");
                existingRecordsList=fillList();
                System.out.println("Done.");
            }

            List<MediaFolder> listOfFolders = mediaFolderService.getAllMediaFolders();
            
            //adding new movies and series to database
            int cnt=0;
            for(MediaFolder path:listOfFolders)
            {
                File folder=new File(path.getPath());
                File[] listOfFiles=folder.listFiles();
                for(int i=0;i<listOfFiles.length;i++)
                {
                    if(listOfFiles[i].isFile())
                    {
                        	if(!isInTheDatabase(listOfFiles[i]))
                            {
                            	System.out.println("File: "+listOfFiles[i].getName());
                                System.out.println("****************Adding "+listOfFiles[i].getName()+" to database*******************\n");
                                insertIntoDatabase(listOfFiles[i].getName(),"file", listOfFiles[i].getAbsolutePath());
                                cnt++;
                            }    
                    }
                    if(listOfFiles[i].isDirectory())
                    {
                        
                        if(!isInTheDatabase(listOfFiles[i]))
                        {
                        	System.out.println("Folder: "+listOfFiles[i].getName());
                            System.out.println("****************Adding "+listOfFiles[i].getName()+" to database*******************\n");
                            insertIntoDatabase(listOfFiles[i].getName(),"folder", listOfFiles[i].getAbsolutePath());
                            cnt++;
                        }
                    }
                }
            }/**/
            
            //adding new games to database
            System.out.println("Total of "+cnt+" entries added to raw names");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /*public static void main(String[] args)
    {
        new FillDatabase().getNames();
    }*/
    
    @Transactional
    public void insertIntoDatabase(String name, String type, String path)
    {
        try
        { 
        	RawNames rawName= new RawNames();
        	rawName.setType(type);
        	rawName.setLocation(path);
        	rawName.setName(name);
        	rawName.setLastAdded(1);
            rawNamesService.save(rawName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Transactional
    public ArrayList<Object> fillList()
    {
    	Session session = em.unwrap(Session.class);
    	
    	Criteria cr = session.createCriteria(RawNames.class)
    		    .setProjection(Projections.projectionList()
    		      .add(Projections.property("location"))
    		      .add(Projections.property("name")));


    		  ArrayList<Object> plist = (ArrayList<Object>) cr.list();/**/
    		  
    	
    	
    	/*ArrayList<String> list = (ArrayList<String>) session.createCriteria(RawNames.class).setProjection(Projections.property("name")).list();
        ArrayList<String> tmp= new ArrayList<String>();*/

        
        return plist;
    }
    private boolean isInTheDatabase(File f)
    {
    	boolean result=false;
    	
    	  Iterator it=existingRecordsList.iterator();
    	  System.out.println("Checking for "+f.getName());
		  while(it.hasNext())
		  {
			  Object[] array=(Object[]) it.next();
			  //System.out.println("In database > name: "+array[1]+"\n location:"+array[0]);
			  if(/*array[1].toString().contains(f.getName()) ||*/ array[0].toString().toLowerCase().equals(f.getAbsolutePath().toLowerCase()))
	          {
				  	//System.out.println("name: "+array[1]+"\n location:"+array[0]);
				  System.out.println("Found in database, moving on...\n\n");
	        		result=true;
	        		break;
	          }
			  
		  }
    	return result;
    }
    
    
}
