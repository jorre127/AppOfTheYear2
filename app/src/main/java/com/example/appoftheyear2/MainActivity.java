package com.example.appoftheyear2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.provider.Settings;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;
    public SettingsFragment settingsFragment;
    public static boolean json;
    public static boolean notificationSetting = true;
    public ArrayList<Game> gameList;
    public static boolean Darkmode;
    private DrawerLayout drawer;
    private HomeFragment homeFragment = new HomeFragment();
    private ListFragment listFragment = new ListFragment();
    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    public String gameName;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    String getCurrentDateTime = sdf.format(c.getTime());


    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("nNotification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

private NotificationCompat.Builder getNotificationBuilder(){

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("A Game Has Been Released!")
                .setContentText(gameName +" has been released")
                .setSmallIcon(R.drawable.ic_android);
        return notifyBuilder;
    }

    public void sendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    };

    public void updateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.ic_release);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"));
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void cancelNotification(){
        mNotifyManager.cancel(NOTIFICATION_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Enables Darkmode
        super.onCreate(savedInstanceState);

        loadDataDarkmode();
            if (Darkmode) {
                setTheme(R.style.DarkTheme);
            } else {
                setTheme(R.style.AppTheme);
            }

        setContentView(R.layout.activity_main);
        // Slide over menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(settingsFragment.DefaultColor!=0)
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
        if(Darkmode){
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
        if(settingsFragment.DefaultColor!=0) {
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
        if (notificationSetting == true && gameList != null ) {
            for (int i = 0; i < gameList.size(); i++) {
                Game getMyTime = gameList.get(i);
                String releaseDate = getMyTime.GameDate;
                gameName = getMyTime.Name;
                Log.d("getCurrentDateTime", getCurrentDateTime);
                if (releaseDate.compareTo(getCurrentDateTime) == 0) {
                    createNotificationChannel();
                    sendNotification();
                } else {
                    Log.d("Return", "getMyTime older than getCurrentDateTime ");
                }
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_Settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                getSupportActionBar().setTitle(SettingsFragment.name); //string is custom name you want
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

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    }

    public void loadDataDarkmode(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        Darkmode = sharedPreferences.getBoolean("darkMode", false);
        SettingsFragment.DefaultColor = sharedPreferences.getInt("Color", 0);
        notificationSetting = sharedPreferences.getBoolean("Notification",false);

    }

}
