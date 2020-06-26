package edu.pdx.cs410J.ahsiao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhoneBillTest {

    @Test
    public void getCustomerTest(){
        PhoneBill dummy = new PhoneBill("Bob");
        assertEquals(dummy.getCustomer(), "Bob");
    }

}
