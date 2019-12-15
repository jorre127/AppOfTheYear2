package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Addgame extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button addGameButon;
    private EditText nameInput;
    private EditText genreInput;
    private EditText scoreInput;
    private Spinner genreInnput;
    private String genreSelection = "";

    private SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SettingsFragment.Darkmode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_addgame);

        addGameButon = findViewById(R.id.addGameButton);
        nameInput = findViewById(R.id.editGameName);
        scoreInput = findViewById(R.id.ScoreEdit);
        genreInnput = findViewById(R.id.spinner);
        String[] genreOptions = new String[]{"Adventure", "Action", "JRPG", "Fighting", "Stealth", "Shooter", "Platformer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genreOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreInnput.setAdapter(adapter);
        genreInnput.setOnItemSelectedListener(this);


        addGameButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameInput.getText().toString().equals("")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("nameInput", nameInput.getText().toString());
                    returnIntent.putExtra("genreInput", genreSelection);
                    returnIntent.putExtra("scoreInput", scoreInput.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);

                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                genreSelection = "Adventure";
                break;
            case 1:
                genreSelection = "Action";
                break;
            case 2:
                genreSelection = "JRPG";
                break;
            case 3:
                genreSelection = "Fighting";
                break;
            case 4:
                genreSelection = "Stealth";
                break;
            case 5:
                genreSelection = "Shooter";
                break;
            case 6:
                genreSelection = "Platformer";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
