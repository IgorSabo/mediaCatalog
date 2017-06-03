/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.business.utils;

import com.catalog.model.entities.Title;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author Gile
 */
public class JSONcreator {

    public static JSONArray createJSON(ArrayList<Title> list)
    {
        JSONObject inner;
        JSONArray array= new JSONArray();
        for(Title title : list)
        {
            inner= new JSONObject();
            inner.put("IDfilm", title.getIDfilm());
            inner.put("title", title.getImdbTitle());
            inner.put("genre", title.getGenre());
            inner.put("year", title.getYear());
            inner.put("IMDBlink", title.getIMDBlink());
            inner.put("location", title.getLocation());
            inner.put("quality", title.getQuality());
            inner.put("picture", title.getPicture());
            array.add(inner);
        }
        return array;
    }
    
    public static JSONObject createJSON(Title title)
    {
        JSONObject inner;
        inner= new JSONObject();
        inner.put("rawName", title.getRawName());
        inner.put("imdbName", title.getImdbTitle());
        inner.put("genre", title.getGenre());
        inner.put("year", title.getYear());
        inner.put("imdbLink", title.getIMDBlink());
        inner.put("location", title.getLocation());
        inner.put("plot", title.getDescription());
        inner.put("quality", title.getQuality());
        inner.put("actors", title.getActors());
        inner.put("picture", title.getPicture());
        return inner;
    }
}
