package com.example.appoftheyear2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private SettingsFragment settingsFragment = new SettingsFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private ListFragment listFragment = new ListFragment();
    private SearchFragment searchFragment = new SearchFragment();
    public ArrayList<Game> gameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        // Enables Darkmode
        super.onCreate(savedInstanceState);


            if (SettingsFragment.Darkmode) {
                setTheme(R.style.DarkTheme);
            } else {
                setTheme(R.style.AppTheme);
            }

        setContentView(R.layout.activity_main);
        // Slide over menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if(settingsFragment!=null && settingsFragment.DefaultColor!=0)
        toolbar.setBackgroundColor(settingsFragment.DefaultColor);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header =navigationView.getHeaderView(0);

        // setting colors for header and background

        int[] colors;
        if(SettingsFragment.Darkmode){
            colors = new int[] {
                    Color.WHITE,
                    Color.WHITE,
                    Color.WHITE,
                    SettingsFragment.DefaultColor,
            };
        }
        else{
            colors = new int[] {
                    Color.BLACK,
                    Color.BLACK,
                    Color.BLACK,
                    SettingsFragment.DefaultColor,
            };
        }
        int[][] states = new int[][] {
                new int[] {android.R.attr.state_selected}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed},  // pressed
                new int[] { -android.R.attr.state_selected}  // pressed
        };


        ColorStateList myList = new ColorStateList(states, colors);
        if(settingsFragment!=null && settingsFragment.DefaultColor!=0) {
            header.setBackgroundColor(SettingsFragment.DefaultColor);
            navigationView.setItemTextColor(myList);
            navigationView.setItemIconTintList(myList);
        }

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_settings,null);
        Switch Switch = view.findViewById(R.id.notificationsSwitch);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_Home);
        }
        loadData();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_Settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                getSupportActionBar().setTitle(SettingsFragment.name); //string is custom name you want
                break;
            case R.id.nav_Search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                getSupportActionBar().setTitle(SearchFragment.name); //string is custom name you want
                break;
            case R.id.nav_Home:
                loadData();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                getSupportActionBar().setTitle(HomeFragment.name); //string is custom name you want
                HomeFragment.gameList = gameList;
                break;
            case R.id.nav_List:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListFragment()).commit();
                getSupportActionBar().setTitle(ListFragment.name); //string is custom name you want
                gameList = ListFragment.gameList;
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        loadData();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void ApplyNewGame(Game editedGame) {

    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("gameList", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        gameList = gson.fromJson(json, type);

        if (gameList == null){
            gameList = new ArrayList<>();
        }
    }

}
