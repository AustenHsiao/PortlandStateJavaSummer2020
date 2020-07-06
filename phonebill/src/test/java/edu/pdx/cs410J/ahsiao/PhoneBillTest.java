package edu.pdx.cs410J.ahsiao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhoneBillTest {

    @Test
    public void getCustomerTest(){
        PhoneBill dummy = new PhoneBill("Bob");
        assertEquals(dummy.getCustomer(), "Bob");
    }

    @Test public void addingPhoneCall(){
        PhoneCall sampleCall = new PhoneCall(
                "503-111-1111",
                "503-222-2222",
                "06/26/2020",
                "12:00",
                "6/27/2020",
                "19:01");
        PhoneBill dummy = new PhoneBill("Bob", sampleCall);

        // We only have one phone call for Project1
        for(Object onePhoneCall: dummy.getPhoneCalls()){
            assertEquals(onePhoneCall, sampleCall);
        }
    }

    @Test
    public void addNewPhoneCallToBill(){
        PhoneCall sampleCall = new PhoneCall(
                "503-111-1111",
                "503-222-2222",
                "06/26/2020",
                "12:00",
                "6/27/2020",
                "19:01");
        PhoneBill dummy = new PhoneBill("Bob", sampleCall);

        // add the call again to test addPhoneCall
        dummy.addPhoneCall(sampleCall);
        // everything in this collection is an object at its base. I dont
        // care what they are, just that he has 2 of them since he started with one and
        // we added another.
        int count = 0;
        for(Object call: dummy.getPhoneCalls()){
            count++;
        }
        assertEquals(count, 2);
    }

}
