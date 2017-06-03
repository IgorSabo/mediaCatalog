/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.business.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Gile
 */
public class DBConnection {
    private static DBConnection instance=null;
    private Connection con=null;
    private SessionFactory sessionFactory=null;
    
    private DBConnection()
    {  
        initConnection();
        initSessionFactory();
    }

    public Connection getCon() {
        try
        {
            if(con == null || con.isClosed())
                initConnection();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
        
        return con;
    }

    public SessionFactory getSessionFactory() {
        try
        {
            if(sessionFactory==null)
                initSessionFactory();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sessionFactory;
    }
    
    
    
    public synchronized static DBConnection getInstance()
    {
        try
        {
            if(instance==null)
            {
                instance= new DBConnection();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return instance;
    }
    public final void initConnection()
    {
        try
        {
           new loader().init();
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           String url="jdbc:mysql://"+loader.host+"/"+loader.database+"?characterEncoding=UTF-8";
           con=DriverManager.getConnection(url,loader.username,loader.password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public final void initSessionFactory()
    {
        try
        {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
