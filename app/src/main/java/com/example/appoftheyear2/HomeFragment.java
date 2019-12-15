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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static ArrayList<Game> gameList =  new ArrayList<>();
    Activity mActivity;
    public static String name = "                      Home";
    TextView scoreAverage;
    int allscores = 0;
    View mView;

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
            allscores+= game.getScore();
        }
        if(gameList.size()!= 0) {
            allscores /= gameList.size();
        }
        scoreAverage = mView.findViewById(R.id.scoreAmount);

        scoreAverage.setText(String.valueOf(allscores));
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
