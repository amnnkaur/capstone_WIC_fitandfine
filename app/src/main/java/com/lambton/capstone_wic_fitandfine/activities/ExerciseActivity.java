package com.lambton.capstone_wic_fitandfine.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.fragment.ExerciseFragment;
import com.lambton.capstone_wic_fitandfine.models.Workout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class ExerciseActivity  extends AppCompatActivity implements View.OnClickListener  {

    private static final int REQUEST_CODE = 111;
    private static Workout ex;
    FloatingActionButton fab;
    FragmentPagerItemAdapter adapter;
    FirebaseAuth mAuth;
    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
       ActionBar ab = getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        Bundle b = new Bundle();
        TextView s = findViewById(R.id.textView);
        b = getIntent().getExtras();
        ex = b.getParcelable("workout");
       // System.out.println("naaa"+ex.getName());
     //    s.setText(ex.getName());
        fab = findViewById(R.id.fab);
        final ViewPager viewPager = findViewById(R.id.pager);
        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        String[] exList = tab10();
        for (int i = 0; i < exList.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("w", ex.getDays().get(i));
            pages.add(FragmentPagerItem.of(exList[i], ExerciseFragment.class, bundle));
        }

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        findViewById(R.id.textview_log_pain_cancel).setOnClickListener(this);
        findViewById(R.id.textview_log_pain_done).setOnClickListener(this);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        for (int i = 0; i < pages.size(); i++)
            viewPagerTab.getTabAt(i).setBackground(null);
        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_SETTLING:
                        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        displayFloatingActionButtonIfNeeded(viewPager.getCurrentItem());
                        break;

                    default:
                        fab.hide();
                }
            }
        });
    }

    private void displayFloatingActionButtonIfNeeded(final int position) {
        if (adapter.getItem(position) instanceof ExerciseFragment) {

            final ExerciseFragment floatingActionButtonFragment = (ExerciseFragment) adapter.getItem(position);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExerciseActivity.this, MuscleActivity.class);
                    intent.putExtra("currentWorkout", ex);
                    intent.putExtra("currentDay", position);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Workout result = data.getExtras().getParcelable("currentWorkout");
                Bundle bundle = new Bundle();
                bundle.putParcelable("workout", result);
                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ExerciseActivity.this.finish();
            }
        }
    }

    public static String[] tab10() {
        String[] dayNames = new String[ex.getSize()];
        for (int i = 0; i < ex.getSize(); i++)
            dayNames[i] = ex.getDays().get(i).getName();

        return dayNames;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_log_pain_cancel:
                finish();
                break;
            case R.id.textview_log_pain_done:
                save();

                break;

        }
    }

    private void save() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("workouts");
        mRef.keepSynced(true);
        mRef.child(String.valueOf(ex.getId())).setValue(ex);

        showAlert("Your changes were successfully saved!");

       //Toast.makeText(this, R.string.toast_save, Toast.LENGTH_SHORT).show();

       // startActivity(new Intent(ExerciseActivity.this, WorkOutActivity.class));
    }
    private void showAlert(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert!");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alerts);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ExerciseActivity.this, WorkOutActivity.class));
              //  dialog.dismiss();
               // finish();
            }

        });
        alertDialogBuilder.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert = alertDialogBuilder.create();
        alert.show();

    }


}