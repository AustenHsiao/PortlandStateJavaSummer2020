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

        if (requestCode == 1) {
            // request code 1 is for creating new phone bills. We get sent back a new hashmap, so we overwrite
            // the original hash map with the returning one.
            if (resultCode == RESULT_OK) {
                this.phoneBillHashMap = (HashMap<String, PhoneBill>)data.getExtras().get("mapReturn");
            }
        }
    }

    public void addPhoneBill(View v){
        Intent addNewPhoneBillPage = new Intent(this, CreatePhoneBill.class);
        addNewPhoneBillPage.putExtra("map", phoneBillHashMap);
        startActivityForResult(addNewPhoneBillPage, 1);
    }

    public void readme(View v){
        Intent readmePage = new Intent(this, readmeScreen.class);
        startActivity(readmePage);
    }

}