package com.catalog.business.assemblers;

import com.catalog.model.entities.TitleRating;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gile on 4/7/2018.
 */
@Component
public class TitleRatingsAssembler {

    public Set<TitleRating> assembleRatingsForTitle(String json){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        Set<TitleRating> ratings = new HashSet<>();
        try{
            obj = (JSONObject) parser.parse(json);
            JSONArray array = (JSONArray) obj.get("Ratings");

            for( int i=0; i<array.size(); i++ ){

                TitleRating rating = new TitleRating();
                JSONObject jsonRating = (JSONObject) array.get(i);
                rating.setSource((String)jsonRating.get("Source"));
                rating.setValue((String)jsonRating.get("Value"));
                ratings.add(rating);
            }
        }
        catch(ParseException e){
            e.printStackTrace();
        }

        return ratings;
    }
}
