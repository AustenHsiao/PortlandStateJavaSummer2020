package edu.pdx.cs410j.ahsiao.phonebill;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class CreatePhoneBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phone_bill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void createPhoneBillButton(View v) {
        String name = ((TextView) findViewById(R.id.name)).getText().toString();
        // Name cant be empty
        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        // If the name is valid, we will look through the hash map to make sure it's not already there.
        HashMap<String, PhoneBill> phoneBillHashMap = (HashMap<String, PhoneBill>) getIntent().getExtras().get("map");
        if(phoneBillHashMap.containsKey(name)){
            Toast.makeText(this, "Customer already exists", Toast.LENGTH_LONG).show();
            return;
        }else{
            phoneBillHashMap.put(name, new PhoneBill(name));
            Toast.makeText(this, "Phone bill created", Toast.LENGTH_LONG).show();
        }

        Intent goHome = new Intent(this, MainActivity.class);
        goHome.putExtra("mapReturn", phoneBillHashMap);
        setResult(RESULT_OK, goHome);
        finish();
    }
}