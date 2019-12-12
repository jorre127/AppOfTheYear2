package com.example.appoftheyear2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;


public class ListFragment extends Fragment {

    private ArrayList<Game> gameList =  new ArrayList<>();

    private Activity mActivity;
    private View mView;
    private RecyclerView gameRecyclerView;
    private RecyclerView.Adapter gameRecycleAdapter;
    private RecyclerView.LayoutManager gameLayourManager;
    private ArrayAdapter<Game> gameAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Game newGame = new Game(data.getStringExtra("nameInput"), data.getStringExtra("genreInput"), Integer.parseInt(data.getStringExtra("scoreInput")));
                gameList.add(newGame);
                saveData();

                Toast.makeText(mView.getContext(),"Game Added!", Toast.LENGTH_LONG).show();
                gameRecycleAdapter.notifyDataSetChanged();
            }
        }
    }

    private void saveData(){
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameList);
        editor.putString("gameList", json);
        editor.apply();
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
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gameRecyclerView = view.findViewById(R.id.RecyclerViewOfGame);
        gameLayourManager = new LinearLayoutManager(mView.getContext());
        gameRecycleAdapter = new gameAdapter(gameList);

        gameRecyclerView.setLayoutManager(gameLayourManager);
        gameRecyclerView.setAdapter(gameRecycleAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadData();

        final AlertDialog.Builder builder;
        final View view = inflater.inflate(R.layout.fragment_list,container,false  );
        mView = view;
        gameAdapter  = new ArrayAdapter<>(mActivity, R.layout.game_item, R.id.gameName, gameList);

        builder = new AlertDialog.Builder(mView.getContext());

        ImageButton activityButton = view.findViewById(R.id.ActivityButton);
        activityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(getContext(), Addgame.class),1);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        saveData();
    }
}
