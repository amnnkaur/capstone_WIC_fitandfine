package com.lambton.capstone_wic_fitandfine.activities.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.lambton.capstone_wic_fitandfine.R;

public class ChatBotWelcomActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_welcom);

        button = (Button) findViewById(R.id.button_chat_bot_welcome);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, ChatBotActivity.class);
        startActivity(intent);
    }
}