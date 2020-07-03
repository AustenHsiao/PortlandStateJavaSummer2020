package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Collection;
import java.util.ArrayList;

/**
 * A phone bill contains a list of phonecalls for a given user.
 * A new phone bill may or may not contain a populated phonecall list.
 * With an instantiated phone bill, we should be able to view the owner's name, view the list of phone calls
 * and add new phone calls to the list.
 */
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

    /**
     * Returns the billOwner's name
     * @return
     */
    @Override
    public String getCustomer() {
        return this.billOwner;
    }

    /**
     * Adds a phone call to the bill
     * @param phoneCall
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        billLog.add(phoneCall);
    }

    /**
     * Returns a collection containing all phone calls on the bill
     * @return
     */
    @Override
    public Collection getPhoneCalls() {
        return this.billLog;
    }
}
