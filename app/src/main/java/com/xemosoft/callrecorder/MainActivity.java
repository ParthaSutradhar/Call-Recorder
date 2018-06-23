package com.xemosoft.callrecorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.xemosoft.callrecorder.utils.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch toolbar_switch;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    
    public void Hack(){
        
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkForRecorderState() {
        boolean isOn = Util.getRecorderState(this);
        if (isOn){
            toolbar_switch.setChecked(true);
        }else{
            toolbar_switch.setChecked(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem swithItem =  menu.findItem(R.id.switch_toolbar_menu);

        View view = MenuItemCompat.getActionView(swithItem);

        toolbar_switch = view.findViewById(R.id.toolbar_switch);

        checkForRecorderState();

        toolbar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Util.setRecorderState(true,MainActivity.this);
                }else{
                    Util.setRecorderState(false,MainActivity.this);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_switch:
                if (toolbar_switch.isChecked()){
                    Util.setRecorderState(false,this);
                    toolbar_switch.setChecked(false);
                }else{
                    Util.setRecorderState(true,this);
                    toolbar_switch.setChecked(true);
                }
                break;
        }
    }
}
