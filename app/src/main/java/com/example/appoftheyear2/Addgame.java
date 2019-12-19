package com.example.appoftheyear2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appoftheyear2.R;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Addgame extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button addGameButon;
    private EditText nameInput;
    private EditText scoreInput;
    private String dateInput;
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

        if (MainActivity.Darkmode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_addgame);

        final DatePickerTimeline datePickerTimeline = findViewById(R.id.dateEdit_Add);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String getCurrentDateTime = sdf.format(c.getTime());
        char[] dateCharArr = getCurrentDateTime.toCharArray();
        int currentYear = Integer.valueOf(dateCharArr[6] + dateCharArr[7] + dateCharArr[8] + dateCharArr[9]);
        int currentMonth = Integer.valueOf(dateCharArr[3] + dateCharArr[4]);
        int currentDay = Integer.valueOf(dateCharArr[0] + dateCharArr[1]);
        datePickerTimeline.setInitialDate(currentYear, currentMonth, currentDay);
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {

                dateInput =Integer.toString(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });

        Date[] dates = {Calendar.getInstance().getTime()};
        //datePickerTimeline.deactivateDates(dates);


        addGameButon = findViewById(R.id.addGameButton_Add);
        nameInput = findViewById(R.id.editGameName_Add);
        scoreInput = findViewById(R.id.ScoreEdit_Add);
        genreInnput = findViewById(R.id.spinner_Add);
        statusInput = findViewById(R.id.statusSpinner_Add);
        hoursInput = findViewById(R.id.timeplayHourEdit_Add);
        minutesInput = findViewById(R.id.timeplayMinutesEdit_Add);
        secondsInput = findViewById(R.id.timeplaySecondsEdit_Add);

        String[] genreOptions = new String[]{"Adventure", "Action", "JRPG", "Fighting", "Stealth", "Shooter", "Platformer"};
        String[] statusOptions = new String[]{"Wishlist", "Completed", "Backlog", "Dropped", "Playing"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, R.layout.customspinnerdropdown, statusOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.customspinnerdropdown, genreOptions);
        adapter.setDropDownViewResource(R.layout.customspinnerdropdown);
        statusAdapter.setDropDownViewResource(R.layout.customspinnerdropdown);

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
                    returnIntent.putExtra("releaseDate", dateInput);
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

        if (parent.getId() == R.id.spinner_Add){
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

        if (parent.getId() == R.id.statusSpinner_Add){

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
