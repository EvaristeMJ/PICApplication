package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import com.example.picapplication.board.TestThread;
import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.databinding.ActivityGameScreenBinding;

import com.example.picapplication.R;
import com.example.picapplication.board.BluetoothBoardConnection;
import com.example.picapplication.board.BoardMessage;
import com.example.picapplication.board.BoardMessageReceiver;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.GameInfo;
import com.example.picapplication.database.PicDatabase;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

public class GameScreenActivity extends AppCompatActivity implements BoardMessageReceiver {
    private ArrayList<String> rules = new ArrayList<>();
    private ActivityGameScreenBinding binding;
    private PicDatabase database = new DatabaseHelper();
    private static int numInfo = 0;
    public boolean textToSpeechInitialized = true;
    private TextToSpeech textToSpeech;
    private String[] informationName;
    private String[] information;
    private TextView mainInfo;
    private TextView secondInfo;
    private TextView thirdInfo;
    private TextView fourthInfo;
    private String cardInfo;
    private MaterialButton speakButton;
    private String ruleInfo = "Rule Information";
    private PicBoardConnection boardConnection = new BluetoothBoardConnection();
    private boolean shareCardInfoTTS;
    private boolean shareRuleInfoTTS;
    private boolean shareRuleInfo;
    private boolean ruleTurnMode;
    private static int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num++;
        //ruleTurnMode = boardConnection.getRuleMode();
        ruleTurnMode = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        boardConnection.addReceiver(this);
        setContentView(R.layout.activity_game_screen);
        shareCardInfoTTS = database.getUserLogged().wantsAssistance();
        shareRuleInfoTTS = database.getUserLogged().wantsAssistance() || ruleTurnMode;
        shareRuleInfo = true;
        mainInfo = findViewById(R.id.firstInfo);
        secondInfo = findViewById(R.id.secondInfo);
        thirdInfo = findViewById(R.id.thirdInfo);
        fourthInfo = findViewById(R.id.fourthInfo);
        speakButton = findViewById(R.id.speakButton);
        binding = ActivityGameScreenBinding.inflate(getLayoutInflater());
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechInitialized = true;
                    int result = textToSpeech.setLanguage(Locale.US);
                }
                else{
                    System.out.println("Failed to initialize TTS");
                }
            }
        });
        speakButton.setText("Card");
        Game game = database.getGameSelected();
        GameInfo gameInfo = game.getGameInfo();
        informationName = gameInfo.getNameInformation();
        information = gameInfo.getInformation();
        fourthInfo.setText("Rule :");
        //launch test thread
        if(num == 2){
            TestThread testThread = new TestThread();
        }
        //binding.backgroundView.setImageBitmap(game.getImage());
        updateView();
        setListeners();
    }

    private void setListeners(){
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCardInfo();
            }
        });
    }
    private void speak(String text){
        if(textToSpeechInitialized){
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

    }
    public void shareCardInfo(){
        if(shareCardInfoTTS){
            speak(cardInfo);
        }
    }

    /**
     * Wait for the card info to be shared before sharing the rule info
     * This is done to avoid the user being overwhelmed with information
     */
    public void shareRuleInfo(){
        if(!textToSpeech.isSpeaking()){
            speak(ruleInfo);
        }
    }
    private void updateView(){
        mainInfo.setText(informationName[0] + " : " + information[0]);
        secondInfo.setText(informationName[1] + " : " + information[1]);
        thirdInfo.setText("");
    }
    private void handleMessage(BoardMessage message){
        if(numInfo == 10){
            numInfo = 0;
            ruleInfo = "";
        }
        switch(message.getType()){
            case BoardMessage.RULE_INFORMATION:
                if(shareRuleInfo){
                    ruleInfo = message.getMessage();
                    addRule(ruleInfo);
                    updateRuleDisplay();
                    if(shareRuleInfoTTS){
                        shareRuleInfo();
                    }
                }
                break;
            case BoardMessage.CARD_INFORMATION:
                cardInfo = message.getMessage();
                shareCardInfo();
                break;
            case BoardMessage.MAIN_INFORMATION:
                information[0] = message.getMessage();
                break;
            case BoardMessage.SECOND_INFORMATION:
                information[1] = message.getMessage();
                break;
            case BoardMessage.THIRD_INFORMATION:
                information[2] = message.getMessage();
                break;
            case BoardMessage.FOURTH_INFORMATION:
                information[3] = message.getMessage();
                break;
            case BoardMessage.RESET_BOARD:
                mainInfo.setText("First Information");
                secondInfo.setText("Second Information");
                thirdInfo.setText("Third Information");
                fourthInfo.setText("Fourth Information");
                break;
        }
        numInfo++;
        updateView();
    }
    private void updateRuleDisplay(){
        String ruleText ="";
        for(int i = 0; i < rules.size(); i++){
            ruleText += "Rule : ";
            ruleText += rules.get(i);
            if(i != rules.size() - 1){
                ruleText += "\n";
            }
        }
        fourthInfo.setText(ruleText);
    }

    /**
     * This method is called when a rule is added to the board
     * Add a rule to the list of rules
     * Limit the number of rules to 3
     * First in first out
     * @param rule
     */
    private void addRule(String rule){
        if(rules.size() == 3){
            rules.remove(0);
        }
        rules.add(rule);
    }


    /**
     * This method is called when a message is received from the board
     * It updates the information on the screen
     * It also shares the card information with the user (Text to speech)
     * @param message
     */
    @Override
    public void onReceive(BoardMessage message) {
        handleMessage(message);
        updateView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardConnection.removeReceiver(this);
    }
}