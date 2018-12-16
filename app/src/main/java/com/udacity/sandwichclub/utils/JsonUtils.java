package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try{
            JSONObject baseName = new JSONObject(json);
            JSONObject name = baseName.getJSONObject("name");

            JSONArray alsoKnownAs =  name.optJSONArray("alsoKnownAs");
            ArrayList<String> listOfAlternateNames = new ArrayList<>();
            for(int i=0;i<alsoKnownAs.length();i++){
                listOfAlternateNames.add(alsoKnownAs.getString(i));
            }

            JSONArray ingredients = baseName.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for(int i=0;i<ingredients.length();i++){
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(name.getString("mainName"),listOfAlternateNames,
                    baseName.getString("placeOfOrigin"),baseName.getString("description"),
                    baseName.getString("image"),ingredientsList);

        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
