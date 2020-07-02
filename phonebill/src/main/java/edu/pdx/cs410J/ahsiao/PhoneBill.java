package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Collection;
import java.util.ArrayList;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
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
    public void addPhoneCall(PhoneCall phoneCall) {
        billLog.add(phoneCall);
    }

    @Override
    public Collection getPhoneCalls() {
        return this.billLog;
    }
}
