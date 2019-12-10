package com.example.appoftheyear2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;



public class SettingsFragment extends Fragment {

    DayNightSwitch dayNightSwitch;
    View background_view;
    Activity activity;
    public static boolean Darkmode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings,container,false  );
        activity = getActivity();

        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            Darkmode = false;
        }
        else{
           Darkmode = true;
        }

        //DarkMode
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
        return view;
    }

    public void restartApp(){
        activity.recreate();
    }
}
