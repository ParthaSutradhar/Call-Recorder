package com.xemosoft.callrecorder.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.xemosoft.callrecorder.R;
import com.xemosoft.callrecorder.fragments.RecordListFragment;
import com.xemosoft.callrecorder.utils.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();


    private SwitchCompat toolbar_switch;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,0,0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // viewPager
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        TabFragmentsAdapter tabFragmentsAdapter = new TabFragmentsAdapter(getSupportFragmentManager());
        // dynamically add fragments to activity view
        tabFragmentsAdapter.addFragment(new RecordListFragment() , "Records");
        tabFragmentsAdapter.addFragment(new RecordListFragment() , "Records");
        viewPager.setAdapter(tabFragmentsAdapter);
        tabLayout.setupWithViewPager(viewPager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkForRecorderState() {
        boolean isOn = Util.getRecorderState(this);
        if (isOn) {
            toolbar_switch.setChecked(true);
        } else {
            toolbar_switch.setChecked(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem swithItem = menu.findItem(R.id.switch_toolbar_menu);


        View view = swithItem.getActionView();

        toolbar_switch = view.findViewById(R.id.toolbar_switch);

        checkForRecorderState();

        toolbar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Util.setRecorderState(true, MainActivity.this);
                } else {
                    Util.setRecorderState(false, MainActivity.this);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home){
            drawerLayout.openDrawer(Gravity.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_switch:
                if (toolbar_switch.isChecked()) {
                    Util.setRecorderState(false, this);
                    toolbar_switch.setChecked(false);
                } else {
                    Util.setRecorderState(true, this);
                    toolbar_switch.setChecked(true);
                }
                break;
        }
    }

    private class TabFragmentsAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragmentList;
        private ArrayList<String> titleList;


        public TabFragmentsAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
            titleList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        public void addFragment(Fragment fragment,String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }
    }
    

}
