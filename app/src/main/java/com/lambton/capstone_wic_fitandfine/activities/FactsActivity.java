package com.lambton.capstone_wic_fitandfine.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.adapter.login.FactsAdapter;
import com.lambton.capstone_wic_fitandfine.models.Facts;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FactsActivity extends Fragment implements View.OnClickListener {

    private List<Facts> levelsList;
    private RecyclerView recyclerView;
    private FactsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facts, container, false);
        return rootView;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        levelsList = new ArrayList<>();


        setToolBar();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_about_pain_levels);


        mAdapter = new FactsAdapter(levelsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareLevelsData();

        TextView ca = view.findViewById(R.id.textview_log_pain_cancel);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    public FactsActivity()
    {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;

    }

    private void prepareLevelsData() {
        Facts level = new Facts(R.string.level_10,R.string.level_10_name, R.string.level_10_description,R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_9,R.string.level_9_name,R.string.level_9_description, R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_8,R.string.level_8_name,R.string.level_8_description,R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_7,R.string.level_7_name,R.string.level_7_description, R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_6,R.string.level_6_name,R.string.level_6_description, R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_5,R.string.level_5_name,R.string.level_5_description,R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_4,R.string.level_4_name,R.string.level_4_description, R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_3,R.string.level_3_name,R.string.level_3_description,R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_2,R.string.level_2_name,R.string.level_2_description, R.drawable.star);
        levelsList.add(level);

        level = new Facts(R.string.level_1,R.string.level_1_name,R.string.level_1_description,R.drawable.star);
        levelsList.add(level);


    }

    private void setToolBar() {
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
      //  setSupportActionBar(toolbar);
     //   getSupportActionBar().setTitle("FACTS");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {

    }
}

