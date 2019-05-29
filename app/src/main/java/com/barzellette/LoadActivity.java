package com.barzellette;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadActivity extends AppCompatActivity implements DownloadAsyncTask.DownloadasyncHandler,AdapterView.OnItemClickListener {
    private String[] category;
    private DownloadAsyncTask downloadCategory;
    private String[] downloadParameter={"https://jokeapi.p.rapidapi.com/categories?format=json","GET","ba028d3c4bmsh1636c6aa9a3c5a6p1aba0ejsnbbbbc46c26a1"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        //connessione http tramite la classe ad ok
        downloadCategory = new DownloadAsyncTask(this);
        downloadCategory.execute(downloadParameter);
    }


    @Override
    public void onDownlaodCompete(String download) {
        try { //prendo le categorie dal file JSON
            JSONObject cat = new JSONObject(download);
            JSONArray catArray = cat.getJSONArray("categories");
            List<String> catList = new ArrayList<>();
            //popolo l'array list delle categorie
            for(int i =0;i<catArray.length();i++){
                catList.add((String) catArray.get(i));
                Log.d("CAT-DOWN",catArray.get(i).toString());
            }
            //Riempio la stringa delle categorie
            category =catList.toArray(new String[catList.size()]);
            selectCategorySetup();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void selectCategorySetup(){
        //riempimento della list view
        setContentView(R.layout.activity_load_select_category);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,category);
        ListView listView = findViewById(R.id.listViewCategory);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //gestione del click sulla list view
            Log.d("ITEM-CLICK",category[position]);
        Intent intent = new Intent(this,ShowJokeActivity.class);
        intent.putExtra("category",category[position]);
        startActivity(intent);
    }
}
