package com.example.saar.saarhoek_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void filmfind(View view) {
        EditText filmfind = (EditText)findViewById(R.id.searchField);
        String film = filmfind.getText().toString();
        filmfind.setText("");

        if (film.length() == 0) {
            Toast error = Toast.makeText(this, "Enter an identifier please!", Toast.LENGTH_SHORT);
            error.show();
        }

        else {
            film = film.replaceAll(" ", "+");

            Intent getfilm = new Intent(this, FilmGet.class);
            getfilm.putExtra("film", film);
            startActivity(getfilm);
        }
    }

    public void watchlist(View view) {
        Intent watchlist = new Intent(MainActivity.this, WatchList.class);
        watchlist.putExtra("activity", "main");
        startActivity(watchlist);
    }
}
