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
        String coupled = extras.getString("activity");
        Setter(coupled);
        }

    private void Setter(String coupled){
        if (coupled.equals("add_movie")) {
            listtitle = extras.getString("listtitle");

            // write to sharedpreferences
            prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // save preferences
            editor.putString(listtitle, listtitle);
            editor.commit();
        }
        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        // find the list view in xml and create movie title array
        moview = (ListView) findViewById(R.id.listView);
        String[] films = {};
        arrayList = new ArrayList<>(Arrays.asList(films));


        adapter = new ArrayAdapter<String>(this, R.layout.item_in_list, R.id.textview, arrayList);

        // add keys to list
        Map<String, ?> keys = prefs.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            arrayList.add(entry.getKey());
        }

        // sorting the listview
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        // setting the listview
        moview.setAdapter(adapter);

        // when a list item is clicked -> remove from listview and sharedpreference
        moview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // selected item string
                String film = (String) adapterView.getItemAtPosition(i);

                // remove from listview
                adapter.remove(adapter.getItem(i));

                // remove from sharedpreference
                prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(film);
                editor.apply();

                Toast toast = Toast.makeText(WatchList.this, "Film removed from list!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // saveguard for screen rotation
        outState.putString("activity", "main");
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // obtaining string
        String from_activity = inState.getString("activity");
        // setting the listview
        Setter(from_activity);
    }
}
