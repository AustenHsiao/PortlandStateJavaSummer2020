package edu.pdx.cs410j.ahsiao.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, PhoneBill> phoneBillHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<String> a = new ArrayList<String>(phoneBillHashMap.keySet());
        for(String i: a){
            for(Object j: phoneBillHashMap.get(i).getPhoneCalls()){
                Log.d(i, ((PhoneCall)j).toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This waits for the results from an activity
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            // back from readme
            if (resultCode == RESULT_OK) {
                Log.d("Map return", "Coming back from readme");
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }else if (requestCode == 1) {
            // request code 1 is for creating new phone bills. We get sent back a new hashmap, so we overwrite
            // the original hash map with the returning one.
            if (resultCode == RESULT_OK) {
                Log.d("Map return", "Create phone bill");
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }else if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                Log.d("Map return", "Added phone call");
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }else if(requestCode == 3){
            if (resultCode == RESULT_OK) {
                Log.d("Map return", "Return from search");
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }else if(requestCode == 4){
            if (resultCode == RESULT_OK) {
                Log.d("Map return", "Return from display");
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }
    }

    public void addPhoneBill(View v){
        Intent addNewPhoneBillPage = new Intent(this, CreatePhoneBill.class);
        addNewPhoneBillPage.putExtra("map", phoneBillHashMap);
        startActivityForResult(addNewPhoneBillPage, 1);
    }

    public void addPhoneCall(View v){
        // I have to check if the phone bill even exists, so again, we have to move the HashMap around.
        // Also similar to before, a successful add will give back a new hashmap to overwrite the old one.
        Intent addNewPhoneCallPage = new Intent(this, AddPhoneCall.class);
        addNewPhoneCallPage.putExtra("map", phoneBillHashMap);
        startActivityForResult(addNewPhoneCallPage, 2);
    }

    public void readme(View v){
        Intent readmePage = new Intent(this, readmeScreen.class);
        readmePage.putExtra("map", phoneBillHashMap);
        startActivityForResult(readmePage, 0);
    }

    public void search(View v){
        Intent searchPage = new Intent(this, search.class);
        searchPage.putExtra("map", phoneBillHashMap);
        startActivityForResult(searchPage, 3);
    }

    public void display(View v){
        Intent display = new Intent(this, display.class);
        display.putExtra("map", phoneBillHashMap);
        startActivityForResult(display, 4);
    }

}