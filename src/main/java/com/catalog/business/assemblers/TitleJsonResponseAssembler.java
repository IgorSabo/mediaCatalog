package com.catalog.business.assemblers;

import com.catalog.model.entities.TitleJsonResponse;
import com.catalog.model.entities.TitleRating;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gile on 4/1/2018.
 */
@Component
public class TitleJsonResponseAssembler {

    public TitleJsonResponse assembleTitleJsonResponseFromJson(String json){

        TitleJsonResponse response = new TitleJsonResponse();
        JSONParser parser = new JSONParser();


        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(json);
            if( ( obj.get("Response")).equals("True") ){
                response.setMetascore((String) obj.get("Metascore"));
                response.setBoxOffice((String) obj.get("BoxOffice"));
                response.setWebSite((String) obj.get("Website"));
                response.setImdbRating((String) obj.get("imdbRating"));
                response.setImdbVotes((String)obj.get("imdbVotes"));

//                JSONArray array = (JSONArray) obj.get("Ratings");
//                Set<TitleRating> ratings = new HashSet<>();
//                for( int i=0; i<array.size(); i++ ){
//
//                    TitleRating rating = new TitleRating();
//                    JSONObject jsonRating = (JSONObject) array.get(i);
//                    rating.setSource((String)jsonRating.get("Source"));
//                    rating.setValue((String)jsonRating.get("Value"));
//                    ratings.add(rating);
//                }
//                response.setRatings(ratings);

                response.setRunTime(obj.get("Runtime").toString().split(" ")[0].trim());
                response.setLanguage((String) obj.get("Language"));
                response.setRated((String) obj.get("Rated"));
                response.setProduction((String) obj.get("Production"));
                response.setReleased((String) obj.get("Released"));
                response.setImdbId((String) obj.get("imdbID"));
                response.setPlot((String) obj.get("Plot"));
                response.setDirector((String) obj.get("Director"));
                response.setTitle((String) obj.get("Title"));
                response.setActors((String) obj.get("Actors"));
                response.setType((String) obj.get("Type"));
                response.setAwards((String) obj.get("Awards"));
                response.setDvd((String) obj.get("DVD"));
                response.setYear((String) obj.get("Year"));
                response.setPoster((String) obj.get("Poster"));
                response.setCountry((String) obj.get("Country"));
                response.setGenre((String) obj.get("Genre"));
                response.setWriter((String) obj.get("Writer"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }

}
