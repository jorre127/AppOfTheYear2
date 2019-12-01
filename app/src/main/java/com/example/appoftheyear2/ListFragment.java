package com.example.appoftheyear2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ListFragment extends Fragment {

    private ArrayList<String> gameList;
    private ArrayAdapter<String> adapter;
    private EditText textInput;
    private Activity mActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false  );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setContentView(R.layout.fragment_list);
        ListView listView = view.findViewById(R.id.ListView);
        String[] games = {"Monster Hunter: World", "Persona 5", "Pokemon Sword"};
        gameList = new ArrayList<>(Arrays.asList(games));
        adapter = new ArrayAdapter<>(mActivity, R.layout.list_item, R.id.textItem, gameList);
        listView.setAdapter(adapter);
        textInput = view.findViewById(R.id.ListAddInput);
        Button addButton = view.findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGame = textInput.getText().toString();
                if (!newGame.equals("")) {
                    gameList.add(newGame);
                }
                ((EditText) v.findViewById(R.id.ListAddInput)).setText("");
                adapter.notifyDataSetChanged();
            }
        });
    }


}
