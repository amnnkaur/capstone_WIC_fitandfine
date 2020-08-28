package com.lambton.capstone_wic_fitandfine.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.activities.MuscleActivity;
import com.lambton.capstone_wic_fitandfine.models.Days;
import com.lambton.capstone_wic_fitandfine.models.Equipment;
import com.lambton.capstone_wic_fitandfine.models.Exercise;
import com.lambton.capstone_wic_fitandfine.models.Muscle;
import com.lambton.capstone_wic_fitandfine.util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class FragAMuscle extends Fragment {
    public static final String TAG = FragAMuscle.class.getSimpleName();
    private List<Muscle> muscleList;
    private List<Exercise> exList;
    private List<Equipment> eqList;
    Days day;
    public MuscleActivity muscleActivity;

    public FragAMuscle() {
        // Required empty public constructor
    }

    public static FragAMuscle newInstance() {
        return new FragAMuscle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muscle_a, container, false);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("muscles");
        Log.d(TAG, "onDataChangeRE:" +myRef);
        myRef.keepSynced(true);
        muscleList = new ArrayList<>();
       processJSON();
        final ImageButton abs_view = view.findViewById(R.id.abs);
        final ImageButton chest_view = view.findViewById(R.id.chest);
        final ImageButton back_view = view.findViewById(R.id.back);
        final ImageButton tricep_view = view.findViewById(R.id.tricep);
        final ImageButton bicep_view = view.findViewById(R.id.bicep);
        final ImageButton legs_view = view.findViewById(R.id.legs);
        final ImageButton cardio_view = view.findViewById(R.id.cardio);
        final ImageButton crossfit_view = view.findViewById(R.id.crossfit);
        final ImageButton shoulder_view = view.findViewById(R.id.shoulder);
        abs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.abs_t);
                bundle.putString("TRANS", abs_view.getTransitionName());
                bundle.putParcelable("MUSCLE", muscleList.get(6));
                bundle.putInt("PHOTO", R.drawable.mabs);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(abs_view, ViewCompat.getTransitionName(abs_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });

        chest_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.chest_t);
                bundle.putString("TRANS", chest_view.getTransitionName());
                bundle.putParcelable("MUSCLE", muscleList.get(0));
                bundle.putInt("PHOTO", R.drawable.mchest);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(chest_view, ViewCompat.getTransitionName(chest_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.back_t);
               bundle.putParcelable("MUSCLE", muscleList.get(1));
                bundle.putString("TRANS", back_view.getTransitionName());
                bundle.putInt("PHOTO", R.drawable.mback);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(back_view, ViewCompat.getTransitionName(back_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        tricep_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.tricep_t);
                bundle.putString("TRANS", tricep_view.getTransitionName());
                bundle.putParcelable("MUSCLE", muscleList.get(5));
                bundle.putInt("PHOTO", R.drawable.mtriceps);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(tricep_view, ViewCompat.getTransitionName(tricep_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        bicep_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.bicep_t);
                bundle.putString("TRANS", bicep_view.getTransitionName());

               bundle.putParcelable("MUSCLE", muscleList.get(4));
                bundle.putInt("PHOTO", R.drawable.mbicep);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(bicep_view, ViewCompat.getTransitionName(bicep_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        legs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.legs_t);
                bundle.putString("TRANS", legs_view.getTransitionName());
                bundle.putParcelable("MUSCLE", muscleList.get(2));
                bundle.putInt("PHOTO", R.drawable.mquad);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(legs_view, ViewCompat.getTransitionName(legs_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        cardio_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.cardio_t);
                bundle.putString("TRANS", cardio_view.getTransitionName());
               bundle.putParcelable("MUSCLE", muscleList.get(7));
                bundle.putInt("PHOTO", R.drawable.mtest);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(cardio_view, ViewCompat.getTransitionName(cardio_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        crossfit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.crossfit_t);
                bundle.putString("TRANS", crossfit_view.getTransitionName());
               bundle.putParcelable("MUSCLE", muscleList.get(8));
                bundle.putInt("PHOTO", R.drawable.mc2);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(crossfit_view, ViewCompat.getTransitionName(crossfit_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        shoulder_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("PIC", R.drawable.shoulder_t);
                bundle.putString("TRANS", shoulder_view.getTransitionName());
               bundle.putParcelable("MUSCLE", muscleList.get(3));
                bundle.putInt("PHOTO", R.drawable.mdelt);
                FragBMuscle simpleFragmentB = FragBMuscle.newInstance();
                simpleFragmentB.setArguments(bundle);
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(shoulder_view, ViewCompat.getTransitionName(shoulder_view))
                        .addToBackStack(TAG)
                        .replace(R.id.content, simpleFragmentB)
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize(view);
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getActivity().getAssets().open("muscels.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private void processJSON()
    {
        String js=loadJSONFromAsset();
        if(js !=null)
        {
            try {
                JSONArray mJSONArray=new JSONArray(js);

                for(int i=0;i<mJSONArray.length();i++) {
                    JSONObject mJSONObj=mJSONArray.getJSONObject(i);
                    if(mJSONObj.has("id")) {
                        int id = mJSONObj.getInt("id");
                        String sname = mJSONObj.getString("name");
                        JSONArray innerArray = mJSONArray.getJSONObject(i).getJSONArray("exercises");
                        if(innerArray.length()!=0){
                            exList = new ArrayList<>();
                            for(int k=0;k<innerArray.length();k++){
                                JSONObject innerObj = innerArray.getJSONObject(k);

                                Exercise exercise = new Exercise();
                                exercise.setId(innerObj.getInt("id"));
                                exercise.setName(innerObj.getString("name"));
                                exercise.setMechanics(innerObj.getString("mechanics"));
                                JSONArray innerEquipArray = innerArray.getJSONObject(k).getJSONArray("equip");
                                if (innerEquipArray.length()!=0) {
                                    eqList = new ArrayList<>();
                                    for (int i1 = 0; i1 < innerEquipArray.length(); i1++) {
                                        JSONObject innerEqObj = innerEquipArray.getJSONObject(i1);
                                        Equipment equipment = new Equipment();
                                        equipment.setName(innerEqObj.getString("name"));
                                        eqList.add(equipment);
                                    }
                                ;
                                }
                                exercise.setEquip(eqList);
                                exList.add(exercise);

                            }
                        }
                        muscleList.add(new Muscle(id,sname,exList));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}