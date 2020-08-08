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

import java.util.Date;
import java.util.HashMap;

import static edu.pdx.cs410j.ahsiao.phonebill.AddPhoneCall.validDate;
import static edu.pdx.cs410j.ahsiao.phonebill.AddPhoneCall.validPhoneNumber;
import static edu.pdx.cs410j.ahsiao.phonebill.AddPhoneCall.validTime;

public class search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void searchButton(View v){
        HashMap<String, PhoneBill> phoneBillHashMap = (HashMap<String, PhoneBill>) getIntent().getExtras().get("map");
        String name = ((TextView)findViewById(R.id.billsearchname)).getText().toString();
        String startDate = ((TextView)findViewById(R.id.startDate)).getText().toString();
        String startTime = ((TextView)findViewById(R.id.startTime)).getText().toString();
        String startAM_PM = ((TextView)findViewById(R.id.start_ampm)).getText().toString();
        String endDate = ((TextView)findViewById(R.id.endDate)).getText().toString();
        String endTime = ((TextView)findViewById(R.id.endTime)).getText().toString();
        String endAM_PM = ((TextView)findViewById(R.id.end_ampm)).getText().toString();

        if(!phoneBillHashMap.containsKey(name)){
            Toast.makeText(this, "Phone bill customer not found", Toast.LENGTH_LONG).show();
            return;
        } else if(name.isEmpty()){
            Toast.makeText(this, "No phone bill specified", Toast.LENGTH_LONG).show();
            return;
        }

        if(!validDate(startDate)){
            Toast.makeText(this, "Start date format is not valid. MM/dd/yyyy", Toast.LENGTH_LONG).show();
            return;
        }else if(!validDate(endDate)){
            Toast.makeText(this, "End date format is not valid. MM/dd/yyyy", Toast.LENGTH_LONG).show();
            return;
        }else if(!validTime(startTime)){
            Toast.makeText(this, "Start time format is not valid. hh:mm", Toast.LENGTH_LONG).show();
            return;
        }else if(!validTime(endTime)){
            Toast.makeText(this, "End time format is not valid. hh:mm", Toast.LENGTH_LONG).show();
            return;
        }else if(!startAM_PM.equalsIgnoreCase("AM") && !startAM_PM.equalsIgnoreCase("PM")){
            Toast.makeText(this, "Specify either AM or PM for start time", Toast.LENGTH_LONG).show();
            return;
        }else if(!endAM_PM.equalsIgnoreCase("AM") && !endAM_PM.equalsIgnoreCase("PM")){
            Toast.makeText(this, "Specify either AM or PM for end time", Toast.LENGTH_LONG).show();
            return;
        }

        PhoneCall dummy = new PhoneCall("000-000-0000", "000-000-0000", startDate, startTime, startAM_PM, endDate, endTime, endAM_PM);
        if(dummy.getStartTime().after(dummy.getEndTime())){
            Toast.makeText(this, "Start time cannot occur after the end time", Toast.LENGTH_LONG).show();
            return;
        }

        Intent showMatches = new Intent(this, showMatches.class);
        PhoneBill dummyPhoneBill = new PhoneBill(name);

        for(Object call: phoneBillHashMap.get(name).getPhoneCalls()){
            PhoneCall currentCall = (PhoneCall) call;
            Date start = currentCall.getStartTime();
            if(start.after(dummy.getStartTime()) && start.before(dummy.getEndTime())){
                dummyPhoneBill.addPhoneCall(currentCall);
            }
        }

        showMatches.putExtra("listOfMatches", dummyPhoneBill);
        showMatches.putExtra("map", phoneBillHashMap);
        startActivity(showMatches);

    }

    public void return_home(View v){
        Intent goHome = new Intent(this, MainActivity.class);
        goHome.putExtra("mapReturn", (HashMap<String, PhoneBill>) getIntent().getExtras().get("map"));
        setResult(RESULT_OK, goHome);
        finish();
    }
}