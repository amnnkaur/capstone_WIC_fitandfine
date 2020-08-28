package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.fragment.FragANutri;
import com.lambton.capstone_wic_fitandfine.fragment.FragBNutri;

public class NutriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri);

        if (getData() == 0 && savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, FragANutri.newInstance())
                    .commit();
        } else if (getData() != 0) {
            Bundle bundle = new Bundle();
            bundle.putDouble("WEIGHT", getData2());
            bundle.putInt("TDEE", getData());
            bundle.putInt("GOAL", getData3());
            FragBNutri simpleFragmentB = FragBNutri.newInstance();
            simpleFragmentB.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, simpleFragmentB)
                    .commit();
        }
    }

    private int getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("TDEE", 0);
    }

    private double getData2() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return Double.valueOf(sharedPreferences.getString("WEIGHT", "0"));
    }

    private int getData3() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", MODE_PRIVATE);
        return sharedPreferences.getInt("GOAL", 0);
    }




}