package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Gamedetails extends AppCompatActivity {

    TextView gameNameText;
    TextView gameGenreText;
    TextView gameScoreText;
    RecyclerView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamedetails);
        gameNameText = findViewById(R.id.GameNameDetail);
        gameGenreText = findViewById(R.id.GenreDetail);
        gameScoreText = findViewById(R.id.ScoreDetail);
        gameList = findViewById(R.id.RecyclerViewOfGame);

        String newGameNameText = getIntent().getStringExtra("currentGameName");
        String newGenreText = getIntent().getStringExtra("currentGameGenre");
        int newScoreText = getIntent().getIntExtra("currentGameScore", 0);

        gameNameText.setText(newGameNameText);
        gameGenreText.setText(newGenreText);
        gameScoreText.setText(String.valueOf(newScoreText));

    }
}