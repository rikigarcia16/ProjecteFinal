package com.example.proiect3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadContactManager {

    private static DownloadContactManager instance;
    private DownloadContactManager(){

    }

    public static DownloadContactManager getInstance(){
        if(instance==null){
            instance=new DownloadContactManager();
        }
        return instance;
    }

    private ArrayList<String> parseJSON(String result){
        ArrayList<String> list = new ArrayList<>();
        try {

                    JSONObject despreObject = new JSONObject(result);
//                    JSONObject aplicatieObject = despreObject.getJSONObject("aplicatie");
//                    String denumire=aplicatieObject.getString("denumire");
//                    list.add(denumire);

                            JSONArray arrayDezvolatori = despreObject.getJSONArray("dezvoltatori");
                            JSONObject firstDezvoltator = arrayDezvolatori.getJSONObject(0);

                            String numeDezvoltator = firstDezvoltator.getString("nume");
                            String prenumeDezvoltator= firstDezvoltator.getString("prenume");
                            String grupaDezvoltator= firstDezvoltator.getString("grupa");
                            list.add(numeDezvoltator);


        } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("rez","error");
        }
        return list;

    }

    public void getDespre(final IContactResult listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://run.mocky.io/v3/714e0f52-0dca-49b9-918a-d3ea9cf2d652");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream stream = connection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(stream);
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder result = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    streamReader.close();
                    stream.close();

                    Log.v("remote",result.toString());
                    listener.onSuccess(parseJSON(result.toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onFailure(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailure(e);
                }
            }
        }).start();
    }
}
