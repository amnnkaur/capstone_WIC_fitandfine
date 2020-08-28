package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lambton.capstone_wic_fitandfine.R;

public class SurveyQuestionThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_question_three);

        Button button = (Button) findViewById(R.id.btn_next);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(SurveyQuestionThreeActivity.this,
                        SurveyQuestionFourActivity.class).putExtra("Question1",0);
                startActivity(myIntent);
            }
        });
    }
}