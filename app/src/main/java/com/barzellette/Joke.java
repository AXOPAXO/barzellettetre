package com.barzellette;

import org.json.JSONException;
import org.json.JSONObject;

public class Joke {
    private String category;
    private String jokeText;
    private static final String[] JOKE_TYPE={"twopart","single"};
    public Joke(String jokeData){
        try {
            JSONObject obj = new JSONObject(jokeData);
            if(obj.getString("type").equals(JOKE_TYPE[0])){
                jokeText="-"+obj.getString("setup")+"\n-"+obj.getString("delivery");
            }
            else if(obj.getString("type").equals(JOKE_TYPE[1])){
                jokeText=obj.getString("joke");
            }
            category = obj.getString("category");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getCategory() {
        return category;
    }
    @Override
    public String toString() {
        return jokeText;
    }
}
