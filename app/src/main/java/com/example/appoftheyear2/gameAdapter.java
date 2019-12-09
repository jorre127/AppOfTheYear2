package com.example.appoftheyear2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gameAdapter extends RecyclerView.Adapter<gameAdapter.gameViewHolder> {

    private ArrayList<Game> gameList;

    public static class gameViewHolder extends RecyclerView.ViewHolder{
        public TextView gameNameView;
        public TextView gameGenreView;

        public gameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameView = itemView.findViewById(R.id.gameName);
            gameGenreView = itemView.findViewById(R.id.genre);
        }
    }

    @NonNull
    @Override
    public gameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, null);
        return new gameViewHolder(v);
    }



    public gameAdapter(ArrayList<Game> gameListIn){
        gameList = gameListIn;
    }

    @Override
    public void onBindViewHolder(@NonNull gameViewHolder holder, int position) {
        Game currentGame = gameList.get(position);

        holder.gameNameView.setText(currentGame.getName());
        holder.gameGenreView.setText(currentGame.getGenre());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
