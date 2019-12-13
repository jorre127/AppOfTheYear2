package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditGame extends AppCompatActivity {
    public Button addGameButton;
    public EditText nameInput;
    public EditText genreInput;
    public EditText scoreInput;

    private SettingsFragment settingsFragment = new SettingsFragment();

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

        Intent intent = getIntent();
        nameInput.setText(intent.getStringExtra("currentGameName"));
        genreInput.setText(intent.getStringExtra("currentGameGenre"));
        scoreInput.setText(String.valueOf(intent.getIntExtra("currentGameScore",8)));
        final int position = intent.getIntExtra("position",0);
        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameInput.getText().toString().equals("") && !genreInput.getText().toString().equals("")) {
                    Bundle returnIntent = new Bundle();
                    returnIntent.putString("position", String.valueOf(position));
                    returnIntent.putString("nameInput", nameInput.getText().toString());
                    returnIntent.putString("genreInput", genreInput.getText().toString());
                    returnIntent.putString("scoreInput", scoreInput.getText().toString());
                    ListFragment fragobj = new ListFragment();
                    fragobj.setArguments(returnIntent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
