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

import java.math.RoundingMode;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Gamedetails extends AppCompatActivity {



    TextView gameNameText;
    TextView gameGenreText;
    TextView gameScoreText;
    TextView gameStatusText;
    TextView gameHoursPlayed;
    TextView gameReleaseText;
    RecyclerView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.Darkmode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_gamedetails);
        gameNameText = findViewById(R.id.GameNameDetail);
        gameGenreText = findViewById(R.id.GenreDetail);
        gameScoreText = findViewById(R.id.ScoreDetail);
        gameStatusText = findViewById(R.id.gameStatusText);
        gameHoursPlayed = findViewById(R.id.gameHoursPlayed);
        gameReleaseText = findViewById(R.id.releaseDateDetail);
        gameList = findViewById(R.id.RecyclerViewOfGame);

        DecimalFormat format = new DecimalFormat("##.00");
        format.setRoundingMode(RoundingMode.CEILING);

        String newGameNameText = getIntent().getStringExtra("currentGameName");
        String newGenreText = getIntent().getStringExtra("currentGameGenre");
        int newScoreText = getIntent().getIntExtra("currentGameScore", 0);
        float newHoursPlayed = getIntent().getFloatExtra("currentGameHoursPlayed",0);
        String newStatusText = getIntent().getStringExtra("currentGameStatus");
        String newHoursPlayedString = String.valueOf(newHoursPlayed);
        gameNameText.setText(newGameNameText);
        gameGenreText.setText(newGenreText);
        gameScoreText.setText(String.valueOf(newScoreText));
        gameStatusText.setText(newStatusText);
        gameHoursPlayed.setText(String.valueOf(format.format(newHoursPlayed)));
        gameReleaseText.setText(getIntent().getStringExtra("currentGameRelease"));
    }
}
