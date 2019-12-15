package com.example.appoftheyear2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

public class gameAdapter extends RecyclerView.Adapter<gameAdapter.gameViewHolder> implements Dialog.DialogListener  {

    public static ArrayList<Game> gameList;
    public Context context;
    int position;



    @Override
    public void ApplyNewGame(Game editedGame) {
        gameList.remove(position);
        gameList.add(editedGame);
        notifyDataSetChanged();
    }
    public static class gameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView gameNameView;
        public TextView gameGenreView;
        public ImageButton deleteButtton;
        public ImageButton editButton;
        final gameAdapter mAdapter;
        public static ArrayList<Game> gameListt;
        Context mContext;

        public gameViewHolder(@NonNull View itemView, gameAdapter gameAdapterIn, Context mContextIn) {
            super(itemView);
            gameNameView = itemView.findViewById(R.id.gameName);
            gameGenreView = itemView.findViewById(R.id.genre);
            deleteButtton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            this.mAdapter = gameAdapterIn;
            gameListt = mAdapter.gameList;
            mContext = mContextIn;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Intent i = new Intent(mContext, Gamedetails.class);
            Game currentGame = gameListt.get(mPosition);
            i.putExtra("currentGameName", currentGame.Name);
            i.putExtra("currentGameGenre", currentGame.Genre);
            i.putExtra("currentGameScore", currentGame.Score);
            i.putExtra("position", getLayoutPosition());
            mContext.startActivity(i);

        }
    }

    @NonNull
    @Override
    public gameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, null);
        context = parent.getContext();

        return new gameViewHolder(v, this, context);
    }



    public gameAdapter(ArrayList<Game> gameListIn){
        gameList = gameListIn;
    }

    @Override
    public void onBindViewHolder(@NonNull gameViewHolder holder, final int position) {
        final Game currentGame = gameList.get(position);
        this.position = position;



        if (currentGame != null) {
            holder.gameNameView.setText(currentGame.getName());
            holder.gameGenreView.setText(currentGame.getGenre());

            if (holder.deleteButtton != null) {
                holder.deleteButtton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Deleting This Game");
                        builder.setMessage("Are you sure you want to delete this game from your list?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gameList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("No", null);
                        builder.show();
                    }
                });
            }
            EditGame.gameAdapter = this;
            if (holder.editButton != null){
                holder.editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,EditGame.class);
                        intent.putExtra("position", position);
                        intent.putExtra("currentGameName", currentGame.getName());
                        intent.putExtra("currentGameGenre", currentGame.getGenre());
                        intent.putExtra("currentGameScore", currentGame.getScore());
                        intent.putExtra("currentGamePosition", gameList.indexOf(currentGame));
                        ((Activity)context).startActivityForResult(intent, 1);

                    }
                });
            }
        }


    }

    

    @Override
    public int getItemCount() {
        return gameList.size();
    }



}

