package com.lambton.capstone_wic_fitandfine.activities.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lambton.capstone_wic_fitandfine.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;


public class ChatBotActivity extends AppCompatActivity {

    private static final String TAG = ChatBotActivity.class.getSimpleName();
    private String uuid = UUID.randomUUID().toString();

    Button submitButton;
    TextView resultTextView;
    EditText queryEditText;
    TextToSpeech textToSpeech;
    private ArrayList<String> entityArrayList;

    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        submitButton = findViewById(R.id.button);
        resultTextView = findViewById(R.id.textView);
        queryEditText = findViewById(R.id.editText);

        initChatbot();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });


    }

    private void initChatbot() {
        final AIConfiguration config = new AIConfiguration("e194dd6256564e10a03f2e07db49619d",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(this, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);// helps to create new session whenever app restarts
        aiRequest = new AIRequest();
    }

    private void sendMessage(View view) {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(ChatBotActivity.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            queryEditText.setText("");
            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(ChatBotActivity.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);

        }
    }

    public void callback(AIResponse aiResponse) {

        if (aiResponse != null) {
            // process aiResponse here
            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            Log.d(TAG, "Bot Reply: " + botReply);
            resultTextView.setText(botReply);
            String toSpeak = resultTextView.getText().toString();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        } else {
            Log.d(TAG, "Bot Reply: Null");
            resultTextView.setText("There was some communication issue. Please Try again!");
        }

    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}

