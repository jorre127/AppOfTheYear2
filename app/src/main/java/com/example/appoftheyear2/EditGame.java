package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Ref;
import java.util.ArrayList;

public class EditGame extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    public Button editGameButton;
    public EditText nameInput;
    public EditText genreInput;
    public String dateInput;
    public EditText scoreInput;
    private String genreSelection = "";
    private String statusSelection = "";
    private Spinner statusInput;
    private Spinner genreInnput;
    private EditText hoursInput;
    private EditText minutesInput;
    private EditText secondsInput;
    boolean dateselected = false;
    private float playtimeTotalHours;
    private int genrePosition;
    private int statusPosition;
    public static boolean Refresh = false;
    String[]genreOptions;
    String[]array;
    Intent intent;
    int position = -1;
    int ArrayPosition;
    public static gameAdapter gameAdapter;

    public  EditGame(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (MainActivity.Darkmode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);



        editGameButton = findViewById(R.id.EditGameButton);
        nameInput = findViewById(R.id.editGameName);
        scoreInput = findViewById(R.id.ScoreEdit);
        genreInnput = findViewById(R.id.spinner);
        statusInput = findViewById(R.id.statusSpinner);
        hoursInput = findViewById(R.id.timeplayHourEdit);
        minutesInput = findViewById(R.id.timeplayMinutesEdit);
        secondsInput = findViewById(R.id.timeplaySecondsEdit);


        genreOptions = new String[]{"Adventure", "Action", "JRPG", "Fighting", "Stealth", "Shooter", "Platformer"};
        String[] statusOptions = new String[]{"Wishlist", "Completed", "Backlog", "Dropped", "Playing"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, R.layout.customspinnerdropdown, statusOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.customspinnerdropdown, genreOptions);
        adapter.setDropDownViewResource(R.layout.customspinnerdropdown);
        statusAdapter.setDropDownViewResource(R.layout.customspinnerdropdown);

        intent = getIntent();
        nameInput.setText(intent.getStringExtra("currentGameName"));
        genreSelection = (intent.getStringExtra("currentGameGenre"));


        final DatePickerTimeline datePickerTimeline = findViewById(R.id.dateEdit);

        array = new String[3];
        String stringDate = intent.getStringExtra("Date");
        array = stringDate.split("/");

        dateselected = false;
        datePickerTimeline.setInitialDate(Integer.valueOf(array[2]), Integer.valueOf(array[1]), Integer.valueOf(array[0]));
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                dateInput =Integer.toString(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                dateselected = true;
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });


        for (int i = 0;i<genreOptions.length;i++){

            if(genreSelection.equals(genreOptions[i])){
                genrePosition = i;
            }

        }


        statusSelection = (intent.getStringExtra("currentGameStatus"));

        for (int i = 0;i<statusOptions.length;i++){

            if(statusSelection.equals(statusOptions[i])){
                statusPosition = i;
            }

        }
        int hoursValue;
        float minutesValue;
        float secondsValue;
        float timePlayed;
        timePlayed = intent.getFloatExtra("timePlayed",0);
        hoursValue = (int)timePlayed;
        hoursInput.setText(Integer.toString(hoursValue));
        minutesValue = (timePlayed-hoursValue)*60;
        minutesInput.setText(Integer.toString((int)minutesValue));
        secondsValue = Math.round(((minutesValue -(int)minutesValue)*60));
        secondsInput.setText(Integer.toString((int)secondsValue));

        scoreInput.setText(String.valueOf(intent.getIntExtra("currentGameScore",8)));
        position = intent.getIntExtra("position",0);
        ArrayPosition = intent.getIntExtra("currentGamePosition",0);

        genreInnput.setAdapter(adapter);
        genreInnput.setOnItemSelectedListener(this);

        statusInput.setAdapter(statusAdapter);
        statusInput.setOnItemSelectedListener(this);

        statusInput.setSelection(statusPosition);
        genreInnput.setSelection(genrePosition);




        editGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nameInput.getText().toString().equals("") && !minutesInput.getText().toString().equals("") && !hoursInput.getText().toString().equals("") && !secondsInput.getText().toString().equals("") && !scoreInput.getText().toString().equals("")) {
                    playtimeTotalHours = Float.valueOf(hoursInput.getText().toString()) + Float.valueOf((Float.valueOf(minutesInput.getText().toString())/60)) + Float.valueOf(((Float.valueOf(secondsInput.getText().toString())/60)/60));

                    ListFragment listFragment = new ListFragment();

                    Game game = listFragment.gameList.get(ArrayPosition);

                    game.Name = nameInput.getText().toString();
                    game.Genre = genreSelection;
                    game.Score = Integer.parseInt(scoreInput.getText().toString());
                    game.Status = statusSelection;
                    game.HoursPlayed = playtimeTotalHours;
                    game.GameDate = dateInput;
                    if(dateselected == false){
                        game.GameDate = array[0]+"/"+array[1]+"/"+array[2];
                    }

                    gameAdapter.notifyDataSetChanged();


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




