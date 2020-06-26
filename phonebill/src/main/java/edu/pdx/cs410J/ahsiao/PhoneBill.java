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

    // adds a phone call to the user's bill log
    public void addPhoneCall(PhoneCall call) {
        billLog.add(call);
    }

    // I don't know what this should do since the abstractPhoneCall doesn't have any good info.
    // It's needed to form the concrete class.
    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
    }

    @Override
    public Collection getPhoneCalls() {
        return this.billLog;
    }
}
