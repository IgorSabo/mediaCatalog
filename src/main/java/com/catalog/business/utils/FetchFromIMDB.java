/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.catalog.business.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Gile
 */
public class FetchFromIMDB {
    //guidebox api bd94e11911455b40df20c6437521220f9329e53f

    public static void main(String[] args){
                                                                              //d272326e467344029e68e3c4ff0b4059
        String tmp = getHtml("https://api.themoviedb.org/3/search/multi?api_key=38ccab0eb65556e3fcba91a87e7b5662&language=en-US&query=john+wick+chapter+2");
        System.out.println("Response is: "+tmp);
    }


    public static JSONObject fetch(String IMDBID){
        String IMDBUrl=""; 

        IMDBUrl="http://www.omdbapi.com/?i="+IMDBID+"&plot=full&r=json";
        
       
        String tmp = getHtml(fixURL(IMDBUrl));
        //get json
        JSONParser parser = new JSONParser();
        JSONObject outer= new JSONObject();
        JSONObject obj=null;
        try
        {
            obj = (JSONObject) parser.parse(tmp);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
        return obj;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    protected static String getHtml(String url) {
        try{Thread.sleep(1000);}
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        HttpClient httpClient = null;

        if (httpClient == null) {
            httpClient = new HttpClient();
        }
        BufferedReader br = null;

        BufferedWriter bw = null;

        StringBuffer html = new StringBuffer();
        String temp;
        GetMethod method;
        try {
            method = new GetMethod(url);
            method.setFollowRedirects(false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        HttpMethodParams params = new HttpMethodParams();

        params.setParameter(
                HttpMethodParams.USER_AGENT,
                "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.0.8) Gecko/2009032712 Ubuntu/8.10 (intrepid) Firefox/3.0.8");

        method.setParams(params);

        try {

            int statusCode = httpClient.executeMethod(method);

            if (statusCode == 200) {
                //System.out.println("Link is OK: " + url + " status is:"+ statusCode);
                br = new BufferedReader(new InputStreamReader(
                        method.getResponseBodyAsStream(), "UTF8"));
                while ((temp = br.readLine()) != null) {
                    html.append(temp).append("\n");
                }

                return html.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    protected static String fixURL(String url) {
        if (url != null) {
            return url.replaceAll(" ", "%20").replaceAll("&amp;", "&").replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "%27");
        } else {
            return "";
        }
    }
}
