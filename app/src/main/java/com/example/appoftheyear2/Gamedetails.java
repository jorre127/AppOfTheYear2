package com.example.appoftheyear2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Gamedetails extends AppCompatActivity {

    TextView gameNameText;
    TextView gameGenreText;
    TextView gameScoreText;
    TextView gameStatusText;
    TextView gameHoursPlayed;
    RecyclerView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamedetails);
        gameNameText = findViewById(R.id.GameNameDetail);
        gameGenreText = findViewById(R.id.GenreDetail);
        gameScoreText = findViewById(R.id.ScoreDetail);
        gameStatusText = findViewById(R.id.gameStatusText);
        gameHoursPlayed = findViewById(R.id.gameHoursPlayed);
        gameList = findViewById(R.id.RecyclerViewOfGame);


        String newGameNameText = getIntent().getStringExtra("currentGameName");
        String newGenreText = getIntent().getStringExtra("currentGameGenre");
        int newScoreText = getIntent().getIntExtra("currentGameScore", 0);
        float newHoursPlayed = getIntent().getFloatExtra("currentGameHoursPlayed",0);
        String newStatusText = getIntent().getStringExtra("currentGameStatus");

        gameNameText.setText(newGameNameText);
        gameGenreText.setText(newGenreText);
        gameScoreText.setText(String.valueOf(newScoreText));
        gameStatusText.setText(newStatusText);
        gameHoursPlayed.setText(String.valueOf(newHoursPlayed));
    }
}
