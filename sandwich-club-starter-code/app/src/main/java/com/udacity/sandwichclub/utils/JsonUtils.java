package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwich = new JSONObject(json);
            JSONObject name = sandwich.getJSONObject("name");
            Sandwich sandwichModel = new Sandwich();
            String mainName = name.getString("mainName");
            List<String> alsoKnownAs = new ArrayList<String>();
            JSONArray jsonArray = name.getJSONArray("alsoKnownAs");

            for(int i =0; i < jsonArray.length(); i++){
                alsoKnownAs.add(jsonArray.getString(i));
            }

            String placeOfOrigin = sandwich.getString("placeOfOrigin");

            List<String> ingredients = new ArrayList<String>();
            jsonArray = sandwich.getJSONArray("ingredients");

            for(int i =0; i < jsonArray.length(); i++){
                ingredients.add(jsonArray.getString(i));
            }

            String image = sandwich.getString("image");
            String description = sandwich.getString("description");




            sandwichModel.setMainName(mainName);
            sandwichModel.setAlsoKnownAs(alsoKnownAs);
            sandwichModel.setPlaceOfOrigin(placeOfOrigin);
            sandwichModel.setIngredients(ingredients);
            sandwichModel.setImage(image);
            sandwichModel.setDescription(description);

            return sandwichModel;


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null ;
    }
}
