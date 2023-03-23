package com.example.picapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Toast;

import com.example.picapplication.R;
import com.example.picapplication.databinding.ActivitySignUpBinding;
import com.example.picapplication.sql.PicDatabase;
import com.example.picapplication.utilities.PreferenceManager;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private PicDatabase picDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }
    private void setListeners(){
        binding.textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidSignUpDetails()){
                    signUp();
                }
            }
        });
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void signUp(){
        picDatabase.addUser(binding.inputName.getText().toString(),
                binding.inputPassword.getText().toString());
    }

    /**
     * Check if the sign up details are valid
     * Check if the username is valid
     * Check if the password is valid
     * Check if the confirm password is valid
     * @return
     */
    private Boolean isValidSignUpDetails(){
        if(binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Enter username");
            return false;
        }
        else if(binding.inputName.getText().toString().trim().length() < 3){
            showToast("Username too short, minimum 3 characters");
            return false;
        }
        else if(binding.inputName.getText().toString().trim().length() > 20){
            showToast("Username too long, maximum 20 characters");
            return false;
        }
        else if(picDatabase.checkUsername(binding.inputName.getText().toString().trim())){
            showToast("Username not available");
            return false;
        }
        else if(binding.inputPassword.getText().toString().isEmpty()){
            showToast("Enter password");
            return false;
        }
        else if(binding.inputPassword.getText().toString().length() < 6){
            showToast("Password too short");
            return false;
        }
        else if(binding.inputConfirmPassword.getText().toString().isEmpty()){
            showToast("Confirm your password");
            return false;
        }
        else if(!binding.inputPassword.getText().toString().equals(
                binding.inputConfirmPassword.getText().toString()))
        {
            showToast("Password & confirm password must be same");
            return false;
        }
        /*
        else if(binding.inputEmail.getText().toString().isEmpty()){
            showToast("Enter email");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
            showToast("Enter valid email");
        }
        */
        else if(binding.inputPassword.getText().toString().isEmpty()){
            showToast("Enter password");
            return false;
        }
        else if(binding.inputConfirmPassword.getText().toString().isEmpty()){
            showToast("Confirm your password");
            return false;
        }
        else if(!binding.inputPassword.getText().toString().equals(
                binding.inputConfirmPassword.getText().toString()))
        {
            showToast("Password & confirm password must be same");
        }
        return true;
    }
}