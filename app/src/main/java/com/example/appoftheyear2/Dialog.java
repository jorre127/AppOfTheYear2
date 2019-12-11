package com.example.appoftheyear2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    public EditText editGamename;
    public EditText editGamegenre;
    public EditText editGamescore;
    public Game editedGame;
    DialogListener listener;


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Edit Game")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gamename = editGamename.getText().toString();
                        String gamegenre = editGamegenre.getText().toString();
                        String gamescore = editGamescore.getText().toString();

                        editedGame = new Game(gamename, gamegenre, Integer.valueOf(gamescore));
                        listener.ApplyNewGame(editedGame);
                    }
                });

        editGamename = view.findViewById(R.id.edit_Gamename);
        editGamegenre = view.findViewById(R.id.edit_gamegenre);
        editGamescore = view.findViewById(R.id.edit_gamescore);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface DialogListener{
        void ApplyNewGame(Game editedGame);
    }

}
