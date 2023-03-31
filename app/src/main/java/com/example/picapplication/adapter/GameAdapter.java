package com.example.picapplication.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picapplication.R;
import com.example.picapplication.board.BoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.ui.GameScreenActivity;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder>{
    private List<Game> games;
    private PicBoardConnection picBoardConnection = new BoardConnection();
    private PicDatabase picDatabase = new DatabaseHelper();
    private ViewGroup parent;

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        return new GameViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.game_presentation_item,
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
        View playButton;
        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_game_presentation_item);
            gameIcon = itemView.findViewById(R.id.gameIcon);
            gameName = itemView.findViewById(R.id.gameName);
            gamePitch = itemView.findViewById(R.id.gamePitch);
            playButton = itemView.findViewById(R.id.playButton);
            setListeners();
        }
        void bindGame(final Game game){
            Bitmap bitmap = game.getImage();
            gameIcon.setImageBitmap(bitmap);
            gameName.setText(game.getGameName());
            gamePitch.setText(game.getGamePitch());
        }
        void setListeners(){
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!picBoardConnection.isConnected()){ // TODO change to isConnected
                        //picDatabase.startPlayingGame(picDatabase.getUserLogged(),games.get(getAdapterPosition()));
                        //picBoardConnection.loadGame(games.get(getAdapterPosition()));
                        // start Game Screen Activity
                        parent.getContext().startActivity(new Intent(parent.getContext(), GameScreenActivity.class));
                    }
                }
        });
    }
}
}
