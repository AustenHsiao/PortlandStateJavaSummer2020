package edu.pdx.cs410j.ahsiao.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.HashMap;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void submit(View v){
        HashMap<String, PhoneBill> phoneBillHashMap = (HashMap<String, PhoneBill>) getIntent().getExtras().get("map");
        String name = ((TextView)findViewById(R.id.billName)).getText().toString();
        if(!phoneBillHashMap.containsKey(name)){
            Toast.makeText(this, "No phone bill data found for the specified customer", Toast.LENGTH_LONG).show();
            return;
        }
        TextView output = ((TextView)findViewById(R.id.printOutArea));
        output.setText("");
        output.append("For customer " + name + "\n\n");
        int callCounter = 0;

        for(Object call : phoneBillHashMap.get(name).getPhoneCalls()){
            PhoneCall phoneCall = (PhoneCall) call;
            Long duration = phoneCall.getEndTime().getTime() - phoneCall.getStartTime().getTime();
            int duration_minutes = (int)(duration / 6e+4);

            output.append("Call from " + phoneCall.getCaller() + " to " + phoneCall.getCallee() + "\n");
            output.append("Call start time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(phoneCall.getStartTime()) + "\n");
            output.append("Call end time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(phoneCall.getEndTime()) + "\n");
            output.append("Call duration: " + duration_minutes + " minutes\n\n");
            callCounter++;
        }

        if(callCounter == 0){
            output.append("No calls to display");
        }

    }

    public void returnHomePage(View v){
        Intent goHome = new Intent(this, MainActivity.class);
        goHome.putExtra("mapReturn", (HashMap<String, PhoneBill>)getIntent().getExtras().get("map"));
        setResult(RESULT_OK, goHome);
        finish();
    }

}