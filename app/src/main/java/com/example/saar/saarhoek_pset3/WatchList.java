package com.example.saar.saarhoek_pset3;

/**
 * Created by Saar on 18-11-2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class WatchList extends AppCompatActivity {
    private String listtitle;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private ListView moview;
    private SharedPreferences prefs;
    private Bundle extras;
    public static final String PREFERENCES = "movies_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        // obtain movie title user wants to add to watch list
        extras = getIntent().getExtras();
        String from_activity = extras.getString("activity");
        Setter(from_activity);
        }

    private void Setter(String from_activity){
        if (from_activity.equals("add_movie")) {
            listtitle = extras.getString("listtitle");

            // write to sharedpreferences
            prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // save preferences (value doesn't matter)
            editor.putString(listtitle, listtitle);
            editor.commit();
        }
        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        // find the listview and create empty array which will contain movie titles
        moview = (ListView) findViewById(R.id.listView);
        String[] movies = {};

        // TODO
    }
}
