package com.example.appoftheyear2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import yuku.ambilwarna.AmbilWarnaDialog;


public class SettingsFragment extends Fragment {

    DayNightSwitch dayNightSwitch;
    View background_view;
    Activity activity;
    public static boolean Darkmode = false;

    ConstraintLayout constraintLayout;
    Button button;
    int DefaultColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings,container,false  );
        activity = getActivity();

        //DarkMode
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            Darkmode = false;
        }
        else{
           Darkmode = true;
        }

        dayNightSwitch = view.findViewById(R.id.darkmode_switch);
        background_view = view.findViewById(R.id.background_view);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            dayNightSwitch.setIsNight(true);
        }

        dayNightSwitch.setDuration(450);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if(is_night){
                    Toast.makeText(activity, "DarkMode On", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();

                }
                else{
                    Toast.makeText(activity, "DarkMode Off", Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        // Button for accent color picker
        constraintLayout = view.findViewById(R.id.settingsLayout);
        button = view.findViewById(R.id.btnChoose);
        DefaultColor = ContextCompat.getColor(activity,R.color.colorPrimary);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenColorPicker(false);
            }
        });

        return view;
    }

    public void restartApp(){
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
            }
        });
        ambilWarnaDialog.show();
    }
}
