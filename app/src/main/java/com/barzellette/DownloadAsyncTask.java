package com.barzellette;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class DownloadAsyncTask extends AsyncTask<String,String,String> {
    protected DownloadasyncHandler handler;

    public DownloadAsyncTask(DownloadasyncHandler handler) {
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection connection = null;
        try {
             url = new URL(strings[0]);
             connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod(strings[1]);
             connection.setRequestProperty("X-RapidAPI-Key",strings[2]);
            int status = connection.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null){
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(handler!=null){
            handler.onDownlaodCompete(s);
        }
    }

    public interface DownloadasyncHandler{
        void onDownlaodCompete(String download);
    }
}
