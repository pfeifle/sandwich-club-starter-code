package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            sandwich = new Sandwich (   jsonSandwich.getJSONObject("name").getString("mainName"),
                                        Array2List(jsonSandwich.getJSONObject("name").getJSONArray("alsoKnownAs")),
                                        jsonSandwich.getString("placeOfOrigin"),
                                        jsonSandwich.getString("description"),
                                        jsonSandwich.getString("image"),
                                        Array2List(jsonSandwich.getJSONArray("ingredients"))
                                    );
        } catch (JSONException e) { // json format error
            return null;
        }
        return sandwich;
    }

    private static List<String> Array2List(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++)
            list.add(array.getString(i));
        return list;
    }
}


