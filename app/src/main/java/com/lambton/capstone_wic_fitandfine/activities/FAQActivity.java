package com.lambton.capstone_wic_fitandfine.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.adapter.FaqAdapter;
import com.lambton.capstone_wic_fitandfine.models.QA;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FAQActivity extends Fragment implements View.OnClickListener{

    RecyclerView list;
    String[] questions;
    String[] answers;
    List<QA> qaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_f_a_q, container, false);
        return rootView;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        list = getView().findViewById(R.id.list);
        final Resources res = getResources();
        questions = res.getStringArray(R.array.Q);
        answers = res.getStringArray(R.array.A);
        qaList = new ArrayList<>();
        int i = 0;
        while (i < 7) {
            QA qa = new QA(questions[i], answers[i]);
            qaList.add(qa);
            i++;
        }
        FaqAdapter mQA = new FaqAdapter(qaList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mQA);
    }

    public void mail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "Fitandfine@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Issue - ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Fite and Fine!");
        startActivity(Intent.createChooser(emailIntent, "Chooser EMail app"));
    }

    @Override
    public void onClick(View view) {

    }
}