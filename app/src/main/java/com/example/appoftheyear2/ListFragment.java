package com.example.appoftheyear2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private View mView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder;
        final View view = inflater.inflate(R.layout.fragment_list,container,false  );
        mView = view;
        builder = new AlertDialog.Builder(mView.getContext());
        final ListView listView = view.findViewById(R.id.ListView);
        String[] games = {"Monster Hunter: World", "Persona 5", "Pokemon Sword"};
        gameList = new ArrayList<>(Arrays.asList(games));
        adapter = new ArrayAdapter<>(mActivity, R.layout.list_item, R.id.textItem, gameList);
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int listPos = position;

                builder.setMessage("Are you sure you want to delete this item?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                gameList.remove(listPos);
                                adapter.notifyDataSetChanged();

                                Toast.makeText(mView.getContext(),"Game Deleted From List!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });


                AlertDialog alert = builder.create();
                alert.setTitle("Warning");
                alert.show();
                return true;
            }
        });
        textInput = view.findViewById(R.id.ListAddInput);
        Button addButton = view.findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGame = textInput.getText().toString();
                if (!newGame.equals("")) {
                    gameList.add(newGame);
                    Context context = mView.getContext();
                    CharSequence text = "Game Added!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                ((EditText) mView.findViewById(R.id.ListAddInput)).setText("");
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

}
