package com.lambton.capstone_wic_fitandfine.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.activities.AboutActivity;
import com.lambton.capstone_wic_fitandfine.activities.BlogActivity;
import com.lambton.capstone_wic_fitandfine.activities.FAQActivity;
import com.lambton.capstone_wic_fitandfine.activities.NutriActivity;
import com.lambton.capstone_wic_fitandfine.activities.ProfileActivity;
import com.lambton.capstone_wic_fitandfine.activities.ProgressActivity;
import com.lambton.capstone_wic_fitandfine.activities.SurveyStartActivity;
import com.lambton.capstone_wic_fitandfine.activities.SurveyWelcomeActivity;
import com.lambton.capstone_wic_fitandfine.activities.WorkOutActivity;
import com.lambton.capstone_wic_fitandfine.activities.WorkingOutActivity;
import com.lambton.capstone_wic_fitandfine.activities.chatbot.ChatBotWelcomActivity;
import com.lambton.capstone_wic_fitandfine.models.Days;
import com.lambton.capstone_wic_fitandfine.models.Workout;
import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class DashboardFragment extends Fragment implements View.OnClickListener {
    private static Context context;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ImageView mNotes;
    private ImageView mWorkoutAreas;
    private ImageView mPainHistory;
    private ImageView mNutrion;
    private ImageView mstart;
    private ImageView mImgProfile;
    private ImageView mAbout;
    private Button btnSurveyList;
    private Button btnFaq;

    ImageView next;
    int day;
    private Workout workoutNow;
    private CircleSegmentBar segmentBar;
    private Runnable mTimer;
    protected int progress;
    private Handler mHandler;
    ImageView btn;
    Workout dzdz;
    private List<Days> daysList;
    public FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    public DashboardFragment() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        day = 0;

        mNotes = (ImageView) getView().findViewById(R.id.imageview_dashboard_notes);
        mNotes.setOnClickListener(this);
        mNutrion = (ImageView) getView().findViewById(R.id.imageview_dashboard_Nut);
        mNutrion.setOnClickListener(this);
        mPainHistory = (ImageView) getView().findViewById(R.id.imageview_dashboard_history);
        mPainHistory.setOnClickListener(this);
        mWorkoutAreas = (ImageView) getView().findViewById(R.id.imageview_dashboard_workout);
        mWorkoutAreas.setOnClickListener(this);
        mImgProfile =(ImageView) getView().findViewById(R.id.imageview_dashboard_profile);
        mImgProfile.setOnClickListener(this);
        mstart = (ImageView) getView().findViewById(R.id.imageview_dashboard_start);
        mstart.setOnClickListener(this);
        btnSurveyList=(Button)getView().findViewById(R.id.btnSurveyList);
        btnSurveyList.setOnClickListener(this);
        mAbout = (ImageView) getView().findViewById(R.id.imageview_dashboard_about);
        mAbout.setOnClickListener(this);
        mHandler = new Handler();
        segmentBar = (CircleSegmentBar)getView().findViewById(R.id.segment_bar);

        initSegmentProgressBar();
        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final long[] mVibratePattern = new long[]{0, 100000, 0, 100000};
        mstart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (vibrator != null) {
                        vibrator.vibrate(mVibratePattern, -1);
                    }
                    setTimer();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    progress = 0;
                    segmentBar.setProgress((float) progress);
                    segmentBar.setText("" + progress, 30, Color.GRAY);
                    mHandler.removeCallbacks(mTimer);
                    if (vibrator != null) {
                        vibrator.cancel();
                    }
                    return false;
                }
                return true;
            }
        });

    }


    private void setTimer() {
        mTimer = new Runnable() {
            @Override
            public void run() {
                progress += 5;
                if (progress <= 100)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            segmentBar.setProgress((float) progress);
                            segmentBar.setText("" + progress, 30, Color.GRAY);
                        }
                    });
                if (progress == 100) {
                    letsStart();
                }
                mHandler.postDelayed(this, 50);
            }
        };
        mHandler.postDelayed(mTimer, 100);
    }
    private void letsStart() {

        ConnectivityManager connMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMan != null;
        NetworkInfo netInfo = connMan.getActiveNetworkInfo();
            Intent intent2 = new Intent(getContext(), WorkingOutActivity.class);
            intent2.putExtra("day", workoutNow.getDays().get(day));
            startActivity(intent2);


    }
    private void initSegmentProgressBar() {
        segmentBar.setCircleViewPadding(2);
        segmentBar.setWidthProgressBackground(25);
        segmentBar.setWidthProgressBarLine(25);
        segmentBar.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);
        segmentBar.setLinearGradientProgress(true);
    }
    public static DashboardFragment newInstance(int sectionNumber) {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageview_dashboard_notes:
                startActivity(new Intent(getContext(), ChatBotWelcomActivity.class));
                break;
            case R.id.imageview_dashboard_workout:
                startActivity(new Intent(getContext(), WorkOutActivity.class));
                break;
            case R.id.imageview_dashboard_Nut:
                startActivity(new Intent(getContext(), NutriActivity.class));
                break;
            case R.id.imageview_dashboard_history:
                 startActivity(new Intent(getActivity(), ProgressActivity.class));
                break;

            case R.id.btnSurveyList:
                startActivity(new Intent(getActivity(), SurveyWelcomeActivity.class));
                break;
            case R.id.btnBlog:
                startActivity(new Intent(getActivity(), BlogActivity.class));
                break;
            case R.id.imageview_dashboard_profile:
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.imageview_dashboard_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.imageview_dashboard_start:
                final long[] mVibratePattern = new long[]{0, 100000, 0, 100000};
                progress = 0;
                segmentBar.setProgress((float) progress);
                segmentBar.setText("" + progress, 30, Color.GRAY);
                mHandler.removeCallbacks(mTimer);
                break;
        }

    }
    private static void lunch(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        getAppContext().startActivity(browserIntent);
    }

    public static Context getAppContext() {
        return DashboardFragment.context;
    }

    public static class CheckTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String strUrl = params[0];

            ConnectivityManager connMan = (ConnectivityManager) getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connMan != null;
            NetworkInfo netInfo = connMan.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL urlServer = new URL(params[0]);
                    HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                    urlConn.setConnectTimeout(2000); //<- 3Seconds Timeout
                    urlConn.connect();
                    if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        return params[0];
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return e.getMessage();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null)
                Toast.makeText(getAppContext(), R.string.toast_check_connection, Toast.LENGTH_SHORT).show();
            else
                lunch(result);
        }
    }
    private int getData3() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("GOAL", 0);
    }

    private int getDefault() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("DEF", 101);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = sharedPreferences.edit();
        DatabaseReference mRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("workouts");
        mRef.keepSynced(true);
        mRef.orderByChild("def").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot != null)
                        dzdz = childDataSnapshot.getValue(Workout.class);
                }
                if (dzdz != null) {

                    daysList = dzdz.getDays();
                    workoutNow = dzdz;
                    mEditor.putInt("DEF", workoutNow.getId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (getDefault() == 101) {
            mEditor.putString("WORK", getResources().getString(R.string.pick_workout));
        } else if (dzdz != null) {
            mEditor.putString("WORK", workoutNow.getName());
        }
        mEditor.apply();
    }


}
