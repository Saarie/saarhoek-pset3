package com.example.saar.saarhoek_pset3;

/**
 * Created by Saar on 18-11-2016.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FilmView extends AppCompatActivity {
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmview);

        Bundle extras = getIntent().getExtras();
        String filmview = extras.getString("information");
        setInfo(filmview);
    }

    private void setInfo(String infoString) {
        try {
            JSONObject specs = new JSONObject(infoString);
            // get the web data
            title = specs.getString("Title");
            String year = specs.getString("Year");
            String director = specs.getString("Director");
            String actors = specs.getString("Actors");
            String plot = specs.getString("Plot");
            String score = specs.getString("imdbRating");
            String poster = specs.getString("Poster");

            // identify textviews
            TextView titleText = (TextView) findViewById(R.id.title);
            TextView yearText = (TextView) findViewById(R.id.year);
            TextView plotText = (TextView) findViewById(R.id.plot);
            TextView actorsText = (TextView) findViewById(R.id.actors);
            TextView directorsText = (TextView) findViewById(R.id.director);
            TextView scoreText = (TextView) findViewById(R.id.score);
            ImageView posterView = (ImageView) findViewById(R.id.posterview);

            ImgSetter setter = new ImgSetter();
            try {
                Bitmap wallsticker = setter.execute(poster).get();
                posterView.setImageBitmap(wallsticker);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            // and set obtained information into layout
            titleText.setText(title);
            yearText.setText(year);
            plotText.setText(plot);
            actorsText.setText(Html.fromHtml("<b> Starring: </b> <br>" + actors));
            directorsText.setText(Html.fromHtml("<b> Directed by: </b> <br>" + director));
            scoreText.setText(Html.fromHtml("<b> IMDb rating: </b> <br>" + score));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void listed (View view) {
        Intent listed = new Intent(this, WatchList.class);

        // title for list
        listed.putExtra("listtitle", title);

        // string determens previous activity
        listed.putExtra("activity", "add_movie");
        startActivity(listed);
        finish();

        Toast toast = Toast.makeText(this, "Movie added!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
