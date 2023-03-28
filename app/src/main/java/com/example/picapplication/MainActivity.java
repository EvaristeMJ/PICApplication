package com.example.picapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.picapplication.board.BoardConnection;
import com.example.picapplication.board.BoardMessage;
import com.example.picapplication.board.BoardMessageReceiver;
import com.example.picapplication.board.GameMessage;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.ui.GameScreenActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.picapplication.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements BoardMessageReceiver {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private PicDatabase database = new DatabaseHelper();
    private PicBoardConnection boardConnection = new BoardConnection();
    private TextView navUserPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardConnection.addReceiver(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        navUsername.setText(database.getUserLogged().getUsername());
        ImageView navUserImage = (ImageView) headerView.findViewById(R.id.imageView);
        navUserImage.setImageBitmap(database.getUserLogged().getProfilePicture());
        navUserPlayer = (TextView) headerView.findViewById(R.id.PlayerInfo);
        navUserPlayer.setText("Not connected to a PIC board");

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_account, R.id.nav_games_created, R.id.nav_log_out)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * This method is called when a message is received from the board
     * It sets the game selected in the database (so that the game screen activity can retrieve it)
     * It launches the game screen activity
     * @param message
     */
    @Override
    public void onReceive(BoardMessage message) {
        if(message.getType() == BoardMessage.GAME_START){
            GameMessage gameMessage = (GameMessage) message;
            database.setGameSelected(gameMessage.getGame());
            startActivity(new Intent(this, GameScreenActivity.class));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardConnection.removeReceiver(this);
        binding = null;
    }
    public void setNavUserPlayer(String text){
        navUserPlayer.setText(text);
    }
}