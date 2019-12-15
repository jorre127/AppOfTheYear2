package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditGame extends AppCompatActivity {
    public Button addGameButton;
    public EditText nameInput;
    public EditText genreInput;
    public EditText scoreInput;
    Intent intent;
    int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_game);

        if (SettingsFragment.Darkmode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }


        addGameButton = findViewById(R.id.editGameButton);
        nameInput = findViewById(R.id.editInputGameName);
        genreInput = findViewById(R.id.editInputGameGenre);
        scoreInput = findViewById(R.id.editInputGameScore);

        intent = getIntent();
        nameInput.setText(intent.getStringExtra("currentGameName"));
        genreInput.setText(intent.getStringExtra("currentGameGenre"));
        scoreInput.setText(String.valueOf(intent.getIntExtra("currentGameScore",8)));
        position = intent.getIntExtra("position",0);



        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameInput.getText().toString().equals("") && !genreInput.getText().toString().equals("")) {

                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public Game getGame(){
        if (nameInput != null) {
            return new Game(nameInput.getText().toString(), genreInput.getText().toString(), Integer.valueOf(scoreInput.getText().toString()));
        }
        return null;
    }

    public int getPosition(){
        if (position != -1) {
            return position;
        }
        return -1;
    }
}

