package com.catalog.business.utils;


import com.catalog.model.entities.*;
import com.catalog.service.NotInsertedService;
import com.catalog.service.TitleService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class CreateEntities {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TitleService titleService;
    
    @Autowired
    private NotInsertedService notInsertedService;

    String match = "";
    HashMap<String, String> typeMap = new HashMap<String, String>();
    HashMap<String, String> genreMap = new HashMap<String, String>();
    String year = "";
    String quality = "";

    public CreateEntities() {
        // TODO Auto-generated constructor stub
    }


    public String extractYear(String name) {
        String year = "";
        Pattern yearPattern = Pattern.compile(".+?(\\d{4}).+?");
        Matcher m = yearPattern.matcher(name);
        if (m.find()) {
            if (!m.group(1).equals("1080")) {
                year = m.group(1);
            }
        }

        return year;
    }

    public String extractQuality(String name) {
        String quality = "";
        if (name.toLowerCase().contains("720p")) {
            quality = "720p";
        }
        if (name.toLowerCase().contains("1080p")) {
            quality = "1080p";
        }
        if (name.toLowerCase().contains("dvdrip") || name.toLowerCase().contains("dvd rip") || name.toLowerCase().contains("dvd-rip")) {
            quality = "DVD rip";
        }
        if (name.toLowerCase().contains("2160p")) {
            quality = "2160p";
        }

        return quality;
    }

    public int returnIDType(String type) {
        int IDType = 3;
        for (Map.Entry<String, String> entry : typeMap.entrySet()) {
            if (entry.getValue().toLowerCase().equals(type)) {
                IDType = Integer.valueOf(entry.getKey());
                break;
            }
        }


        return IDType;
    }

    public int returnIDGenre(String genre) {
        int IDgenre;
        if (genreMap.get(genre) != null) {
            IDgenre = Integer.valueOf(genreMap.get(genre));
        } else {
            IDgenre = 18;
        }
        return IDgenre;
    }

    @Transactional
    public void initTypes() {
        System.out.println("Initializing types...");
        try {
            Session session = em.unwrap(Session.class);
            //Transaction transaction = (Transaction) session.beginTransaction();
            ArrayList<Type> types = (ArrayList<Type>) session.createCriteria(Type.class).list();
            //transaction.commit();
            for (Type tmp : types) {
                typeMap.put(String.valueOf(tmp.getIDtype()), tmp.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }

    @Transactional
    public void initGenres() {
        System.out.println("Initializing genres...");
        Session session = em.unwrap(Session.class);
        //Transaction transaction = (Transaction) session.beginTransaction();
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

    protected String getHtml(String url) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
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

    protected String fixURL(String url) {
        if (url != null) {
            return url.replaceAll(" ", "%20").replaceAll("&amp;", "&").replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "%27");
        } else {
            return "";
        }
    }

    public String findMatchForNameAlt(String idTitle, String name, String path) {

        String[] words = name.split(" ");
        int size = words.length;
        while (size >= 0) {

            String tmpName = "";
            for (int i = 0; i < size; i++) {
                tmpName += words[i] + " ";
            }
            if (processName(idTitle, tmpName.trim(), path) == true) {
                break;
            }
            size--;
        }

        return match;
    }

    @Transactional
    public boolean processName(String idTitle, String name, String path) {

        boolean found = false;


        String IMDBName = name;

        String url;
        if (!year.equals("")) {
            url = "http://www.omdbapi.com/?t=" + IMDBName + "&y=" + year + "&apikey=8d51e39d";
        } else {
            url = "http://www.omdbapi.com/?t=" + IMDBName + "&apikey=8d51e39d";
        }
        String tmp = getHtml(fixURL(url));

        JSONParser parser = new JSONParser();

        try {

            JSONObject obj = (JSONObject) parser.parse(tmp);

            if( ( obj.get("Response")).equals("True") ){

                String foundYear = (String) obj.get("Year");
                String picture = (String) obj.get("Poster");
                String genre = (String) obj.get("Genre");
                String type = (String) obj.get("Type");
                String imdbLink = "http://www.imdb.com/title/" + (String) obj.get("imdbID") + "/?ref_=fn_al_tt_1";
                String description = (String) obj.get("Plot");
                String actors = (String) obj.get("Actors");
                Float imdbRating = null;
                try{
                    imdbRating = ( !obj.get("imdbRating").equals("N/A") ? Float.valueOf((String) obj.get("imdbRating")) : null );
                }
                catch(Exception e){}

                //System.out.println("Godina: " + year + "\nSlika: " + slika + "\nzanr: " + zanr + "\ntip: " + type + "\nimdbLink: " + imdbLink + "\nopis: " + description + "\nglumci: " + actors+"");

                if (foundYear != null) {
                    match = (String) obj.get("Title");
                    //insert into database
                    Title title = new Title();
                    title.setIDfilm(Integer.valueOf(idTitle));
                    title.setActors(actors);
                    title.setRawName(match);
                    title.setImdbTitle(IMDBName);
                    System.out.println("Nadjeno je: " + type + "a u mapi imamo: ");

                    title.setIDtype(returnIDType(type));
                    title.setGenre(genre.replace("-", ""));
                    title.setYear(foundYear);
                    title.setIMDBlink(imdbLink);
                    title.setLocation(path);
                    title.setDescription(description);
                    title.setQuality(quality);
                    title.setActors(actors);
                    title.setPicture(picture);
                    title.setLastAdded(1);
                    title.setImdbRating(imdbRating);
                    title.setApiResponse(tmp);
                    titleService.createTitle(title); //createTitle(title)
                    found = true;
                }
            }


        } catch (Exception pe) {
            pe.printStackTrace();
        }
        return found;

    }

    @Transactional
    public void createTitlesAlt(ArrayList<RawNames> list) {
        try {
            initTypes();
            initGenres();

            BufferedWriter bw = new BufferedWriter(new FileWriter("notFound.txt", true));
            for (RawNames tmp : list) {
                match = "";
                String idTitle = String.valueOf(tmp.getIDname());
                String name = tmp.getName().toLowerCase().replaceAll("\\.", " ").replaceAll("_", " ").replaceAll("\\[", " ").replaceAll("\\]", " ").replaceAll("\\s+", " ").replaceAll("\\^", "").replaceAll("-", "");
                System.out.println(name);
                String path = tmp.getLocation().toLowerCase();

                //extract year
                year = extractYear(name);

                //extract quality
                quality = extractQuality(name);

                String foundAs = findMatchForNameAlt(idTitle, name, path);
                System.out.println("Found as: " + match + "\n");
                if (foundAs.equals("")) {
                	NotInserted test = null;
                	test = notInsertedService.getOne(Integer.valueOf(idTitle));
                	if(test == null)
                	{
                    	//insert into not inserted
                    	NotInserted noIns = new NotInserted();
                    	noIns.setIDfilm(Integer.valueOf(idTitle));
                    	noIns.setLocation(path);
                    	noIns.setName(tmp.getName());
                    	noIns.setType(tmp.getType());
                    	notInsertedService.save(noIns);
                	}
                }

            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
