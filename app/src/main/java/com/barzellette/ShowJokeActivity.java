package com.barzellette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowJokeActivity extends AppCompatActivity implements DownloadAsyncTask.DownloadasyncHandler, View.OnClickListener {
    private DownloadAsyncTask jokeDonwload;
    //parametri JSON e http
    private String[] downloadParameters={"https://jokeapi.p.rapidapi.com/category/{category_name}?format=json","GET", "ba028d3c4bmsh1636c6aa9a3c5a6p1aba0ejsnbbbbc46c26a1"};
    private TextView textViewJoke,textViewCategory;
    private Button buttonUpdate,buttonShare;
    private Joke joke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_joke);
        downloadJoke();
        buttonUpdate = findViewById(R.id.buttonNewJoke);
        buttonShare = findViewById(R.id.buttonCondividi);
        textViewJoke = findViewById(R.id.textViewJoke);
        textViewCategory = findViewById(R.id.textViewCategory);
        buttonShare.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
    }

    @Override
    public void onDownlaodCompete(String download) {
        joke = new Joke(download);
        textViewJoke.setText(joke.toString());
        textViewCategory.setText(joke.getCategory());
        findViewById(R.id.progressBarLoad).setEnabled(false);
        findViewById(R.id.progressBarLoad).setVisibility(View.INVISIBLE);
    }

    private void downloadJoke(){
        //riprendo i paramtri delle categorie dalla view precedente tramite il put e get extras
        Intent intent = getIntent();
        String category= intent.getStringExtra("category");
        downloadParameters[0]=downloadParameters[0].replace("{category_name}",category);
        jokeDonwload = new DownloadAsyncTask(this);
        jokeDonwload.execute(downloadParameters);
        findViewById(R.id.progressBarLoad).setEnabled(true);
        findViewById(R.id.progressBarLoad).setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonCondividi){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, joke.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }
        else if(v.getId()==R.id.buttonNewJoke){
            downloadJoke();
        }
    }
}
