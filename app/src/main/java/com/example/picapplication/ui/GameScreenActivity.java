package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.example.picapplication.R;
import com.example.picapplication.board.BoardMessage;
import com.example.picapplication.board.BoardMessageReceiver;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.GameInfo;
import com.example.picapplication.databinding.ActivityGameScreenBinding;
import com.example.picapplication.database.PicDatabase;

import java.util.Locale;

public class GameScreenActivity extends AppCompatActivity implements BoardMessageReceiver {
    private ActivityGameScreenBinding binding;
    private PicDatabase database;
    private TextToSpeech textToSpeech;
    private String[] informationName;
    private String[] information;
    private String cardInfo;
    private PicBoardConnection boardConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardConnection.addReceiver(this);
        setContentView(R.layout.activity_game_screen);
        binding = ActivityGameScreenBinding.inflate(getLayoutInflater());
        Game game = database.getGameSelected();
        GameInfo gameInfo = game.getGameInfo();
        informationName = gameInfo.getNameInformation();
        information = gameInfo.getInformation();
        binding.backgroundView.setImageBitmap(game.getImage());
        updateView();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }
    private void speak(String text){
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
    public void shareCardInfo(){
        speak(cardInfo);
    }
    private void updateView(){
        binding.firstInfo.setText(informationName[0] + " : " + information[0]);
        binding.secondInfo.setText(informationName[1] + " : " + information[1]);
        binding.thirdInfo.setText(informationName[2] + " : " + information[2]);
        binding.fourthInfo.setText(informationName[3] + " : " + information[3]);
        binding.fifthInfo.setText(informationName[4] + " : " + information[4]);
    }

    /**
     * This method is called when a message is received from the board
     * It updates the information on the screen
     * It also shares the card information with the user (Text to speech)
     * @param message
     */
    @Override
    public void onReceive(BoardMessage message) {
        switch(message.getType()){
            case BoardMessage.CARD_INFORMATION:
                cardInfo = message.getMessage();
                shareCardInfo();
                break;
            case BoardMessage.MAIN_INFORMATION:
                binding.firstInfo.setText(message.getMessage());
                break;
            case BoardMessage.SECOND_INFORMATION:
                binding.secondInfo.setText(message.getMessage());
                break;
            case BoardMessage.THIRD_INFORMATION:
                binding.thirdInfo.setText(message.getMessage());
                break;
            case BoardMessage.FOURTH_INFORMATION:
                binding.fourthInfo.setText(message.getMessage());
                break;
            case BoardMessage.FIFTH_INFORMATION:
                binding.fifthInfo.setText(message.getMessage());
                break;
            case BoardMessage.RESET_BOARD:
                binding.firstInfo.setText("First Information");
                binding.secondInfo.setText("Second Information");
                binding.thirdInfo.setText("Third Information");
                binding.fourthInfo.setText("Fourth Information");
                binding.fifthInfo.setText("Fifth Information");
                break;
        }
        updateView();
    }
}