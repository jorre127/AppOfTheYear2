package com.example.appoftheyear2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class gameAdapter extends RecyclerView.Adapter<gameAdapter.gameViewHolder> {

    public ArrayList<Game> gameList;
    Context context;

    public static class gameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView gameNameView;
        public TextView gameGenreView;
        final gameAdapter mAdapter;
        ArrayList<Game> gameListt;
        Context mContext;

        public gameViewHolder(@NonNull View itemView, gameAdapter gameAdapterIn, Context mContextIn) {
            super(itemView);
            gameNameView = itemView.findViewById(R.id.gameName);
            gameGenreView = itemView.findViewById(R.id.genre);
            this.mAdapter = gameAdapterIn;
            gameListt = mAdapter.gameList;
            mContext = mContextIn;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Game element = new Game("Aangepast", "Item", 0);
            this.gameListt.set(mPosition, element);
            mAdapter.notifyDataSetChanged();
            mContext.startActivity(new Intent(mContext, Addgame.class));
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
    public void onBindViewHolder(@NonNull gameViewHolder holder, int position) {
        final Game currentGame = gameList.get(position);

        if (currentGame != null) {
            holder.gameNameView.setText(currentGame.getName());
            holder.gameGenreView.setText(currentGame.getGenre());
        }

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

}
