package com.example.saar.saarhoek_pset3;

/**
 * Created by Saar on 18-11-2016.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FilmView extends AppCompatActivity {
    private String title;
    private String filmview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmview);

        Bundle extras = getIntent().getExtras();
        filmview = extras.getString("information");
        setInfo(filmview);
    }

    private void setInfo(String infoString) {
        try {
            JSONObject specs = new JSONObject(infoString);
            // get the text info
            title = specs.getString("Title");
            String year = specs.getString("Year");
            String director = specs.getString("Director");
            String actors = specs.getString("Actors");
            String plot = specs.getString("Plot");
            String score = specs.getString("imdbRating");
            String poster = specs.getString("Poster");

            // textfields
            TextView titleText = (TextView) findViewById(R.id.title);
            TextView yearText = (TextView)findViewById(R.id.year);
            TextView plotText = (TextView)findViewById(R.id.plot);
            TextView actorsText = (TextView)findViewById(R.id.actors);
            TextView directorsText = (TextView)findViewById(R.id.director);
            TextView scoreText = (TextView)findViewById(R.id.score);

            // and set
            titleText.setText(title);
            yearText.setText(year);
            plotText.setText(plot);
            actorsText.setText(Html.fromHtml("<b> Starring: </b> <br>" + actors));
            directorsText.setText(Html.fromHtml("<b> Directed by: </b> <br>" + director));
            scoreText.setText(Html.fromHtml("<b> IMDb rating: </b> <br>" + score));

            // poster get
            if (!poster.equals("N/A")) {
                new imgsetter((ImageView) findViewById(R.id.posterview)).execute(poster);
            }
        }
        catch (JSONException e) {
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

    class imgsetter extends AsyncTask<String, Void, Bitmap> {
        ImageView wallsticker;

        public imgsetter(ImageView bmImage){
            this.wallsticker = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            // the passed url
            String strimgurl = urls[0];
            URL imgurl = null;

            try {
                // making new url using the passed string url
                imgurl = new URL(strimgurl);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Bitmap posterbmp = null;
            try {
                // saving image url into end result
                posterbmp = BitmapFactory.decodeStream(imgurl.openConnection().getInputStream());
                return posterbmp;

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // if failed
            return null;
        }
        protected void onPostExecute(Bitmap result) {
            // setting the image to an imageview where getPosterImage() is called
            wallsticker.setImageBitmap(result);
        }
    }
}
