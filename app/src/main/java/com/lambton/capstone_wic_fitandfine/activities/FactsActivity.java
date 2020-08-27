package com.lambton.capstone_wic_fitandfine.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.adapter.login.FactsAdapter;
import com.lambton.capstone_wic_fitandfine.models.Facts;

import java.util.ArrayList;
import java.util.List;

public class FactsActivity extends AppCompatActivity {

    private List<Facts> levelsList;
    private RecyclerView recyclerView;
    private FactsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facts);

        levelsList = new ArrayList<>();

        prepareLevelsData();
        setToolBar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_about_pain_levels);


        mAdapter = new FactsAdapter(levelsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;

    }

    private void prepareLevelsData() {
        Facts level = new Facts(R.string.level_10,R.string.level_10_name, R.string.level_10_description,R.drawable.pain_level_8_10);
        levelsList.add(level);

        level = new Facts(R.string.level_9,R.string.level_9_name,R.string.level_9_description, R.drawable.pain_level_8_10);
        levelsList.add(level);

        level = new Facts(R.string.level_8,R.string.level_8_name,R.string.level_8_description,R.drawable.pain_level_8_10);
        levelsList.add(level);

        level = new Facts(R.string.level_7,R.string.level_7_name,R.string.level_7_description, R.drawable.pain_level_4_7);
        levelsList.add(level);

        level = new Facts(R.string.level_6,R.string.level_6_name,R.string.level_6_description, R.drawable.pain_level_4_7);
        levelsList.add(level);

        level = new Facts(R.string.level_5,R.string.level_5_name,R.string.level_5_description,R.drawable.pain_level_4_7);
        levelsList.add(level);

        level = new Facts(R.string.level_4,R.string.level_4_name,R.string.level_4_description, R.drawable.pain_level_4_7);
        levelsList.add(level);

        level = new Facts(R.string.level_3,R.string.level_3_name,R.string.level_3_description,R.drawable.pain_level_1_3);
        levelsList.add(level);

        level = new Facts(R.string.level_2,R.string.level_2_name,R.string.level_2_description, R.drawable.pain_level_1_3);
        levelsList.add(level);

        level = new Facts(R.string.level_1,R.string.level_1_name,R.string.level_1_description,R.drawable.pain_level_1_3);
        levelsList.add(level);

        level = new Facts(R.string.level_0,R.string.level_0_name,R.string.level_0_description,R.drawable.pain_level_0);
        levelsList.add(level);


    }

    private void setToolBar() {
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FACTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

