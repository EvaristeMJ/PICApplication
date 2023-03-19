package com.example.picapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picapplication.R;
import com.example.picapplication.database.Game;

import java.util.List;

public class UserGameAdapter extends RecyclerView.Adapter<UserGameAdapter.GameViewHolder>{
    private List<Game> games;

    public UserGameAdapter(List<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new GameViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.game_creation_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.bindGame(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        ImageView gameIcon;
        TextView gameName,gamePitch;
        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_game_creation_item);
            gameIcon = itemView.findViewById(R.id.gameIcon);
            gameName = itemView.findViewById(R.id.gameName);
            gamePitch = itemView.findViewById(R.id.gamePitch);
        }
        void bindGame(final Game game){
            gameIcon.setImageResource(game.getImage());
            gameName.setText(game.getGameName());
            gamePitch.setText(game.getGamePitch());
        }
    }
}
