package com.lambton.capstone_wic_fitandfine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.fragment.FragAMuscle;
import com.lambton.capstone_wic_fitandfine.models.Days;
import com.lambton.capstone_wic_fitandfine.models.Workout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MuscleActivity extends AppCompatActivity implements View.OnClickListener{

    Days day;
    public Workout workout;
    public int dayNum;
    @BindView(R.id.textview_log_pain_cancel)
    TextView textviewLogPainCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ActionBar ab = getSupportActionBar();
        // ab.setTitle("Fit and Fine");
        workout = getIntent().getExtras().getParcelable("currentWorkout");
        dayNum = getIntent().getExtras().getInt("currentDay");
        FragAMuscle simpleA = FragAMuscle.newInstance();
        setContentView(R.layout.activity_muscle);
        ButterKnife.bind(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, simpleA)
                .commit();
        textviewLogPainCancel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Fragment f = MuscleActivity.this.getSupportFragmentManager().findFragmentById(R.id.content);
        if (f instanceof FragAMuscle) {
            Intent intent = new Intent();
            intent.putExtra("currentWorkout", workout);
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.textview_log_pain_cancel:
                Fragment f = MuscleActivity.this.getSupportFragmentManager().findFragmentById(R.id.content);
                if (f instanceof FragAMuscle) {
                    Intent intent = new Intent();
                    intent.putExtra("currentWorkout", workout);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

        }
    }
}