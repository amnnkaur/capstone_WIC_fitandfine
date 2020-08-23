package com.lambton.capstone_wic_fitandfine.activities;


import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.fragment.DashboardFragment;

import java.util.ArrayList;
import java.util.Date;


public class DashboardActivity extends AppCompatActivity implements ICallBackWeekFragment {



    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);

        initUIViews();
    }

    private void initUIViews(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.viewpager_dashboard);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public void icallBackWeekFragment(ArrayList<Date> list, Date selectedDate) {
      /*  CalendarWeekViewActivity cal = new CalendarWeekViewActivity();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Intent.EXTRA_TEXT,list);
        bundle.putSerializable(IAppConstants.KEY_TITLE,selectedDate);
        cal.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(mViewPager.getId(), cal, "hkdhsk")
                .commit();*/
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new DashboardFragment();
                case 1:
                  //  return new MyTherapiesFragment();
                case 2:
                    return new DashboardFragment();
                case 3:
                    //return CalendarFragment.newInstance(DashboardActivity.this);
                default:
                    return DashboardFragment.newInstance(position + 1);
            }
        }


        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }
}
