package com.example.saar.saarhoek_pset3;

/**
 * Created by Saar on 18-11-2016.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FilmGet extends AppCompatActivity {

    private String filmtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmget);

        // get the user-inputted film title
        Bundle extras = getIntent().getExtras();
        filmtitle = extras.getString("film");
        new getfilm().execute(filmtitle);
    }

    private class getfilm extends AsyncTask<String, Void, String> {

        private HttpURLConnection web;

        // get info from the web
        @Override
        protected String doInBackground (String... params) {
            try {
                URL filmlink = new URL("http://www.omdbapi.com/?t=" + params[0] + "&plot=full&r=json");

                try {
                    web = (HttpURLConnection) filmlink.openConnection();

                    // get information from the JSON object
                    BufferedReader analyst = new BufferedReader(new InputStreamReader(web.getInputStream()));
                    StringBuilder exposition = new StringBuilder();
                    String part;

                    // while we can still analyse, expose! (with new line)
                    while ((part = analyst.readLine()) != null) {
                        exposition.append(part + "\n");
                    }

                    analyst.close();

                    // expositions are to be seen ;)
                    return exposition.toString();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
                // tick goes the clock, browsing time's up
                finally {
                    web.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // if anything goes wrong (catch), return NULL
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject informant = null;
            String answer = null;

            // attempt to find the film and assign it
            try {
                informant = new JSONObject(result);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                answer = informant.getString("Response");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            // determine if we indeed found it
            if (!(answer.equals("False"))){
                Intent succes = new Intent(FilmGet.this, FilmView.class);
                succes.putExtra("information", result);
                startActivity(succes);
                finish();
            }

            else {
                Toast failure = Toast.makeText(FilmGet.this, "Failed to find. Spellcheck!", Toast.LENGTH_SHORT);
                failure.show();

                // we don't want to wallow, so we go back
                Intent goback = new Intent(FilmGet.this, MainActivity.class);
                startActivity(goback);
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
