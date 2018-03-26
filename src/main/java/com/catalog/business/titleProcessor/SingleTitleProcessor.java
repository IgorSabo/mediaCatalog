package com.catalog.business.titleProcessor;

import com.catalog.business.utils.CreateEntities;
import com.catalog.model.entities.RawNames;
import com.catalog.model.entities.Title;
import com.catalog.service.NotInsertedService;
import com.catalog.service.TitleService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Gile on 2/11/2018.
 */

@SuppressWarnings("Duplicates")
public class SingleTitleProcessor implements Callable<Map<RawNames, Title>> {


    @Value( "${apikey}" )
    private String apiKey;

    @Value( "${apiUrl}" )
    private String apiUrl;

    private RawNames rawName;
    String idTitle = "";
    private String year = "";
    private String quality = "";
    private String path = "";
    private HashMap<String, String> typeMap = new HashMap<String, String>();
    private HashMap<String, String> genreMap = new HashMap<String, String>();
    private double progressIncrement;
    private HttpSession session;

    public SingleTitleProcessor(RawNames rawName, HashMap<String, String> typeMap, HashMap<String, String> genreMap, String apiKey, String apiUrl, HttpSession session, double progressIncrement ) {
        this.rawName = rawName;
        this.typeMap = typeMap;
        this.genreMap = genreMap;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.session = session;
        this.progressIncrement = progressIncrement;
    }

    @Override
    public Map<RawNames, Title> call() throws Exception {

        idTitle = String.valueOf(rawName.getIDname());
        path = rawName.getLocation().toLowerCase();
        String name = rawName.getName().toLowerCase().replaceAll("\\.", " ").replaceAll("_", " ").replaceAll("\\[", " ").replaceAll("\\]", " ").replaceAll("\\s+", " ").replaceAll("\\^", "").replaceAll("-", " ");
        System.out.println(name);

        //extract year
        year = TitleProcessorUtils.extractYear(name);

        //extract quality
        quality = TitleProcessorUtils.extractQuality(name);

        //process name and return result
        return findMatchForName(name);
    }

    public Map<RawNames, Title> findMatchForName(String name) {

        Map<RawNames, Title> res = new HashMap<>();
        Title title = null;

        String[] words = name.split(" ");
        int size = words.length;
        while (size >= 0) {
            String tmpName = "";
            for (int i = 0; i < size; i++) {
                tmpName += words[i] + " ";
            }
            if ((title = processName(tmpName.trim())) != null) {

                break;
            }
            size--;
        }

        res.put(rawName, title);
        return res;
    }


    public Title processName(String name) {

        Title title = null;

        String IMDBName = name;
        //System.out.println("Processing name: "+name);
        String url;
        if (!year.equals("")) {
            url = apiUrl + IMDBName + "&y=" + year + "&apikey="+apiKey;
        } else {
            url = apiUrl + IMDBName + "&apikey="+apiKey;
        }
        String tmp = TitleProcessorUtils.getHtml(fixURL(url));

        JSONParser parser = new JSONParser();

        try {

            JSONObject obj = (JSONObject) parser.parse(tmp);

            System.out.println( "Status: "+obj.get("Response").toString()+" Response: "+obj.toJSONString() );

            if ( obj.get("Response").toString().equals("True")) {

                String foundYear = (String) obj.get("Year");
                String picture = (String) obj.get("Poster");
                String genre = (String) obj.get("Genre");
                String type = (String) obj.get("Type");
                String imdbLink = "http://www.imdb.com/title/" + (String) obj.get("imdbID") + "/?ref_=fn_al_tt_1";
                String description = (String) obj.get("Plot");
                String actors = (String) obj.get("Actors");
                Float imdbRating = null;
                try {
                    imdbRating = (!obj.get("imdbRating").equals("N/A") ? Float.valueOf((String) obj.get("imdbRating")) : null);
                } catch (Exception e) {
                }

                //System.out.println("Godina: " + year + "\nSlika: " + slika + "\nzanr: " + zanr + "\ntip: " + type + "\nimdbLink: " + imdbLink + "\nopis: " + description + "\nglumci: " + actors+"");

                if (foundYear != null) {

                    title = new Title();
                    title.setIDfilm(Integer.valueOf(idTitle));
                    title.setActors(actors);
                    title.setRawName((String) obj.get("Title"));
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
                }
            }


        } catch (Exception pe) {
            pe.printStackTrace();
        }
        return title;

    }

    protected String fixURL(String url) {
        if (url != null) {
            return url.replaceAll(" ", "%20").replaceAll("&amp;", "&").replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "%27");
        } else {
            return "";
        }
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
}
