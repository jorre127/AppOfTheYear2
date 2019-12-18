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

import java.lang.reflect.Array;

public class Addgame extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button addGameButon;
    private EditText nameInput;
    private EditText scoreInput;
    private EditText dateInput;
    private Spinner genreInnput;
    private String genreSelection = "";
    private String statusSelection = "";
    private Spinner statusInput;
    private EditText hoursInput;
    private EditText minutesInput;
    private EditText secondsInput;

    private float playtimeTotalHours;


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
        statusInput = findViewById(R.id.statusSpinner);
        dateInput = findViewById(R.id.dateEdit);
        hoursInput = findViewById(R.id.timeplayHourEdit);
        minutesInput = findViewById(R.id.timeplayMinutesEdit);
        secondsInput = findViewById(R.id.timeplaySecondsEdit);

        String[] genreOptions = new String[]{"Adventure", "Action", "JRPG", "Fighting", "Stealth", "Shooter", "Platformer"};
        String[] statusOptions = new String[]{"Wishlist", "Completed", "Backlog", "Dropped", "Playing"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genreOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genreInnput.setAdapter(adapter);
        genreInnput.setOnItemSelectedListener(this);

        statusInput.setAdapter(statusAdapter);
        statusInput.setOnItemSelectedListener(this);



        addGameButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameInput.getText().toString().equals("") && !minutesInput.getText().toString().equals("") && !hoursInput.getText().toString().equals("") && !secondsInput.getText().toString().equals("") && !scoreInput.getText().toString().equals("")) {
                    playtimeTotalHours = Float.valueOf(hoursInput.getText().toString()) + Float.valueOf((Float.valueOf(minutesInput.getText().toString())/60)) + Float.valueOf(((Float.valueOf(secondsInput.getText().toString())/60)/60));
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("nameInput", nameInput.getText().toString());
                    returnIntent.putExtra("genreInput", genreSelection);
                    returnIntent.putExtra("scoreInput", scoreInput.getText().toString());
                    returnIntent.putExtra("statusInput", statusSelection);
                    returnIntent.putExtra("hoursPlayed", playtimeTotalHours);
                    returnIntent.putExtra("releaseDate", dateInput.getText().toString());
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

        if (parent.getId() == R.id.spinner){
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

        if (parent.getId() == R.id.statusSpinner){

            switch (position){
                case 0:
                    statusSelection = "Wishlist";
                    break;
                case 1:
                    statusSelection = "Completed";
                    break;
                case 2:
                    statusSelection = "Backlog";
                    break;
                case 3:
                    statusSelection = "Dropped";
                    break;
                case 4:
                    statusSelection = "Playing";
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
