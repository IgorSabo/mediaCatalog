/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.catalog.business.utils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gile
 */
public class UpdateIds {
    static Connection con=null;
    static Statement stmt=null;

    public UpdateIds() {
        createConnection();
    }
    
    
            
    
        public static void createConnection() {
        try {
            new loader().init();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://" + loader.host + "/" + loader.database + "?characterEncoding=UTF-8";
            con = DriverManager.getConnection(url, loader.username, loader.password);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        createConnection();
        updateGenre();
   
    }
    private static void updateIds()
    {
    	HashMap<String, String> mapa=new HashMap<String, String>();
        try
        {
            String query="select IDName, path from rawnames;";
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next())
            {
                mapa.put(rs.getString(1), rs.getString(2));

            }
            rs.close();
            PreparedStatement pstmt=con.prepareStatement("update title set IDfilm=? where location=?");
            for(Map.Entry<String, String> entry: mapa.entrySet())
            {
                System.out.println(entry.getKey()+" "+entry.getValue());
                pstmt.setObject(1,entry.getKey());
                pstmt.setObject(2,entry.getValue());
                pstmt.execute();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    private static void updateGenre()
    {
    	HashMap<String, String> mapa=new HashMap<String, String>();
        try
        {
            String query="select IDfilm, genre from title;";
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next())
            {
                mapa.put(rs.getString(1), rs.getString(2));

            }
            rs.close();
            PreparedStatement pstmt=con.prepareStatement("update title set genre=? where IDfilm=?");
            for(Map.Entry<String, String> entry: mapa.entrySet())
            {
                if(entry.getValue().contains("-"))
                {
                    System.out.println(entry.getKey()+" "+entry.getValue());
                	 pstmt.setObject(1,entry.getKey());
                	 pstmt.setObject(2,entry.getValue().replace("-", ""));
                	 pstmt.execute();
                }      
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
