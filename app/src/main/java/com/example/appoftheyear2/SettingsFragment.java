package com.example.appoftheyear2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;


public class SettingsFragment extends Fragment {

    public static DayNightSwitch dayNightSwitch;
    View background_view;

    public static boolean recreate = false;
    Activity activity;

    public static String name = "                     Settings";
    Button button;
    public static int DefaultColor = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings,container,false  );
        activity = getActivity();
        //DarkMode

        dayNightSwitch = view.findViewById(R.id.darkmode_switch);
        background_view = view.findViewById(R.id.background_view);

        if(MainActivity.Darkmode == true){
            dayNightSwitch.setIsNight(true);
        }

        dayNightSwitch.setDuration(450);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if(is_night){
                    Toast.makeText(activity, "DarkMode On", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    MainActivity.Darkmode = true;
                    saveData();
                    restartApp();

                }
                else{
                    Toast.makeText(activity, "DarkMode Off", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    MainActivity.Darkmode = false;
                    saveData();
                    restartApp();
                }
            }
        });

        // Button for accent color picker
        button = view.findViewById(R.id.btnChoose);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenColorPicker(false);
            }
        });

        return view;
    }

    public  void restartApp(){
        activity.recreate();
    }
    private void OpenColorPicker (boolean AlphaSupport){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(activity, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(activity, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                DefaultColor = color;
                saveData();
                restartApp();
            }
        });
        ambilWarnaDialog.show();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkMode", MainActivity.Darkmode);
        editor.putInt("Color",DefaultColor);
        editor.apply();
    }
}
