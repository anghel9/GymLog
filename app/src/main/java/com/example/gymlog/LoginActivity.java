package com.example.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.User;
import com.example.gymlog.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private GymLogRepository repository ;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyUser()){
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });

    }

    private boolean verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(this, "Username is empty, fill it", Toast.LENGTH_SHORT).show();
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            }else{
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        toastMaker(String.format("No %s found",username));
        return false;
    }

    private void toastMaker(String msg) {
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

}