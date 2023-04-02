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
import com.example.picapplication.database.User;
import com.example.picapplication.ui.GameScreenActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<User> users;
    private PicBoardConnection picBoardConnection = new BoardConnection();
    private PicDatabase picDatabase = new DatabaseHelper();
    private ViewGroup parent;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.user_hub_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindUser(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        ImageView profileImage;
        TextView username;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_game_creation_item);
            profileImage = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.username);
            setListeners();
        }
        void bindUser(final User user){
            Bitmap bitmap = user.getProfilePicture();
            profileImage.setImageBitmap(bitmap);
            username.setText(user.getUsername());

        }
        void setListeners(){
            /*

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!picBoardConnection.isConnected()){ // TODO change to isConnected
                        picDatabase.startPlayingGame(picDatabase.getUserLogged(),games.get(getAdapterPosition()));
                        picBoardConnection.loadGame(games.get(getAdapterPosition()));
                        // start Game Screen Activity
                        parent.getContext().startActivity(new Intent(parent.getContext(), GameScreenActivity.class));
                    }
                }
        });*/
    }
}
}
