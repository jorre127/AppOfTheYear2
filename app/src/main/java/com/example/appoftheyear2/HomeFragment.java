package com.example.appoftheyear2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static ArrayList<Game> gameList =  new ArrayList<>();
    Activity mActivity;
    public static String name = "                      Home";
    TextView scoreAverage;
    TextView TotalHours;
    int allscores = 0;
    View mView;
    PieChart pieChart;
    PieChart statusPieChart;
    ArrayList<String> genreList = new ArrayList<>();
    float adventureTotal = 0;
    float actionTotal = 0;
    float JRPGTotal = 0;
    float PlatformTotal = 0;
    float shooterTotal = 0;
    float stealthTotal = 0;
    float fightingTotal = 0;
    float allGenresTotal = 0;
    float totalHours = 0;
    float allStatusesTotal = 0;
    float wishlistTotal = 0;
    float playingTotal = 0;
    float backlogTotal = 0;
    float completedTotal = 0;
    float droppedTotal = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false  );
        mView = view;
        loadData();
        for (Game game: gameList
        ) {
            switch (game.Status){
                case "Wishlist":
                    wishlistTotal++;
                    break;
                case "Playing":
                    playingTotal++;
                    break;
                case "Backlog":
                    backlogTotal++;
                    break;
                case "Completed":
                    completedTotal++;
                    break;
                case "Dropped":
                    droppedTotal++;
                    break;
            }
            totalHours+= game.HoursPlayed;
            genreList.add(game.getGenre());
            allscores+= game.getScore();
            allStatusesTotal++;
        }
        if(gameList.size()!= 0) {
            allscores /= gameList.size();
        }
        scoreAverage = mView.findViewById(R.id.scoreAmount);
        TotalHours = mView.findViewById(R.id.hoursPlayedAmount);
        scoreAverage.setText(String.valueOf(allscores));
        TotalHours.setText(String.valueOf(totalHours));


        for (String genre: genreList
             ) {
            switch (genre.toLowerCase()){
                case "adventure":
                    adventureTotal++;
                break;
                case "action":
                    actionTotal++;
                    break;
                case "jrpg":
                    JRPGTotal++;
                    break;
                case "platformer":
                    PlatformTotal++;
                    break;
                case "shooter":
                    shooterTotal++;
                    break;
                case "stealth":
                    stealthTotal++;
                    break;
                case "fighting":
                    fightingTotal++;
                    break;
            }
        }

        allGenresTotal = genreList.size();
         adventureTotal/=allGenresTotal;
         actionTotal/=allGenresTotal;
         JRPGTotal/=allGenresTotal;
         PlatformTotal/=allGenresTotal;
         shooterTotal/=allGenresTotal;
         stealthTotal/=allGenresTotal;
         fightingTotal/=allGenresTotal;

         completedTotal/=allStatusesTotal;
        droppedTotal/=allStatusesTotal;
        wishlistTotal/=allStatusesTotal;
        backlogTotal/=allStatusesTotal;
        playingTotal/=allStatusesTotal;

        pieChart = mView.findViewById(R.id.genrePieChart);
        statusPieChart = mView.findViewById(R.id.statusPieChart);
        pieChart.setUsePercentValues(true);
        statusPieChart.setUsePercentValues(true);
        List<PieEntry> genres = new ArrayList<>();
        List<PieEntry> statuses = new ArrayList<>();
        if (completedTotal != 0){
            statuses.add(new PieEntry(completedTotal, "Completed"));
        }
        if (droppedTotal != 0){
            statuses.add(new PieEntry(droppedTotal, "Dropped"));
        }
        if(wishlistTotal != 0){
            statuses.add(new PieEntry(wishlistTotal, "Wishlist"));
        }
        if(backlogTotal != 0){
            statuses.add(new PieEntry(backlogTotal, "Backlog"));
        }
        if (playingTotal != 0){
            statuses.add(new PieEntry(playingTotal, "Playing"));
        }

        if (adventureTotal != 0){
            genres.add(new PieEntry(adventureTotal, "Adventure"));
        }
        if (JRPGTotal != 0) {

            genres.add(new PieEntry(JRPGTotal, "JRPG"));
        }
        if (actionTotal != 0) {
            genres.add(new PieEntry(actionTotal, "Action"));
        }
        if (PlatformTotal != 0) {
            genres.add(new PieEntry(PlatformTotal, "Platformer"));
        }
        if (shooterTotal != 0) {
            genres.add(new PieEntry(shooterTotal, "Shooter"));
        }
        if(stealthTotal != 0){
            genres.add(new PieEntry(stealthTotal, "Stealth"));
        }
        if (fightingTotal != 0) {
            genres.add(new PieEntry(fightingTotal, "Fighting"));
        }


        PieDataSet pieDataSet = new PieDataSet(genres, "");
        PieDataSet statusPieDataSet = new PieDataSet(statuses, "");
        PieData pieData = new PieData(pieDataSet);
        PieData statusPieData = new PieData(statusPieDataSet);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        statusPieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieChart.setData(pieData);
        statusPieChart.setData(statusPieData);
        pieChart.animateXY(1400, 1400);
        statusPieChart.animateXY(1400,1400);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        statusPieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(false);
        statusPieChart.setDrawHoleEnabled(false);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        Legend l2 = statusPieChart.getLegend();
        l2.setEnabled(false);
        return view;

    }
    private void loadData(){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("gameList", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        gameList = gson.fromJson(json, type);

        if (gameList == null){
            gameList = new ArrayList<>();
        }
    }

}
