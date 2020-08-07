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

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class AddPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_phone_call_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Copy and pasted from assignment 1
    private static boolean validPhoneNumber(String phonenum){
        if(phonenum.length() != 12){return false;}
        String[] splitNumber = phonenum.split("-");
        if(splitNumber.length != 3){return false;}
        for(String numberSections: splitNumber){
            if(!numberSections.matches("[0-9]+")){
                return false;
            }
        }
        if(splitNumber[0].length() != 3 || splitNumber[1].length() != 3){
            return false;
        }
        if(splitNumber[2].length() != 4){
            return false;
        }
        return true;
    }

    // Copy and pasted from assignment 1
    private static boolean validDate(String date){
        // At a minimum, the date can be represented as x/x/xxxx, which is 8 chars
        // On the flip side, the maximum is 10 chars
        if(date.length() < 8 || date.length() > 10){return false;}
        String[] splitDate = date.split("/");

        // If we don't end up with 3 portions, we weren't given a valid date for sure
        if(splitDate.length != 3){return false;}

        // After splitting, check each section, at a maximum, the string should be 4 characters in length
        // and contains only numbers
        for(String dateSections: splitDate) {
            if (!dateSections.matches("[0-9]+")) {
                return false;
            }
        }
        // Check lengths, for the first two sections, we can have either a single digit or two digits
        if(splitDate[0].length() > 2){return false;}
        if(splitDate[1].length() > 2){return false;}
        if(splitDate[2].length() != 4){return false;}

        int month = parseInt(splitDate[0]);
        int day = parseInt(splitDate[1]);
        int year = parseInt(splitDate[2]);

        if(month < 1 || month > 12){return false;}
        if(day < 1 || day > 31){return false;}
        // Arbitrarily assign 2000 to be the earliest acceptable year. The upper limit is constrained to 4 digits
        if(year < 2000){return false;}

        // All the months that CANNOT have 31 days.
        if((month == 2 ||
                month == 4 ||
                month == 6 ||
                month == 9 ||
                month == 11) && day == 31){return false;}

        // If it passed through all the filters, it might be true
        return true;
    }

    // Copy and pasted from assignment 1
    private static boolean validTime(String time){
        if(time.length() < 4 || time.length() > 5){return false;}
        String[] splitTime = time.split(":");
        if(splitTime.length != 2){return false;}
        for(String timeSections: splitTime){
            if(!timeSections.matches("[0-9]+") || timeSections.length() > 2){
                return false;
            }
        }
        if(splitTime[1].length() != 2){return false;}
        int hour = parseInt(splitTime[0]);
        int minute = parseInt(splitTime[1]);

        if(hour > 24 || hour < 0){return false;}
        if(minute > 59 || minute < 0){return false;}

        return true;
    }

    public void addPhoneCall(View v){
        HashMap<String, PhoneBill> phoneBillHashMap = (HashMap<String, PhoneBill>) getIntent().getExtras().get("map");
        String name = ((TextView)findViewById(R.id.pbName)).getText().toString();
        String callerNumber = ((TextView)findViewById(R.id.caller)).getText().toString();
        String calleeNumber = ((TextView)findViewById(R.id.callee)).getText().toString();
        String startDate = ((TextView)findViewById(R.id.startDate)).getText().toString();
        String startTime = ((TextView)findViewById(R.id.startTime)).getText().toString();
        String startAM_PM = ((TextView)findViewById(R.id.startAM_PM)).getText().toString();
        String endDate = ((TextView)findViewById(R.id.endDate)).getText().toString();
        String endTime = ((TextView)findViewById(R.id.endTime)).getText().toString();
        String endAM_PM = ((TextView)findViewById(R.id.endAM_PM)).getText().toString();

        if(!phoneBillHashMap.containsKey(name)){
            Toast.makeText(this, "Phone bill customer does not exist", Toast.LENGTH_LONG).show();
            return;
        } else if(name.isEmpty()){
            Toast.makeText(this, "No phone bill specified", Toast.LENGTH_LONG).show();
            return;
        }

        if(!validPhoneNumber(callerNumber)){
            Toast.makeText(this, "Caller phone number format is not valid. xxx-xxx-xxxx", Toast.LENGTH_LONG).show();
            return;
        } else if(!validPhoneNumber(calleeNumber)){
            Toast.makeText(this, "Callee phone number format is not valid. xxx-xxx-xxxx", Toast.LENGTH_LONG).show();
            return;
        } else if(!validDate(startDate)){
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

        PhoneCall temp = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, startAM_PM, endDate, endTime, endAM_PM);

        if(temp.getStartTime().after(temp.getEndTime())){
            Toast.makeText(this, "Start time cannot occur after end time", Toast.LENGTH_LONG).show();
            return;
        }

        PhoneBill pcToAdd = phoneBillHashMap.get(name);
        pcToAdd.addPhoneCall(temp);
        phoneBillHashMap.replace(name, pcToAdd);

        Intent create = new Intent(this, MainActivity.class);
        create.putExtra("mapReturn", phoneBillHashMap);
        setResult(RESULT_OK, create);
        finish();
    }

}