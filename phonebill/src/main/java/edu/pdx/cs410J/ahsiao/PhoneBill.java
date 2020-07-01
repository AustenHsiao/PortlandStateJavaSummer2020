package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.Collection;
import java.util.ArrayList;

public class PhoneBill extends AbstractPhoneBill {
    private String billOwner;
    private Collection<PhoneCall> billLog = new ArrayList<PhoneCall>();

    /**
     * Constructor used when user bill is being created but does not have any PhoneCall to import
     * @param name
     */
    PhoneBill(String name){
        this.billOwner = name;
    }

    /**
     * Constructor used when user bill is being created simultaneously with a PhoneCall to import
     * @param name
     * @param call
     */
    PhoneBill(String name, PhoneCall call){
        this(name);
        billLog.add(call);
    }

    // Return customer name (bill owner)
    @Override
    public String getCustomer() {
        return this.billOwner;
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        PhoneCall castedCall = (PhoneCall) call;
        billLog.add(castedCall);
    }
/*
    // adds a phone call to the user's bill log
    public void addPhoneCall(PhoneCall call) {
        billLog.add(call);
    }*/



    @Override
    public Collection getPhoneCalls() {
        return this.billLog;
    }
}
