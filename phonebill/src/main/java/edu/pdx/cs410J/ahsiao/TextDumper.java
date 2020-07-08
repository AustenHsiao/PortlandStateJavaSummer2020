package edu.pdx.cs410J.ahsiao;

import java.io.FileWriter;
import java.io.IOException;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper<PhoneBill>{

    public static int write(PhoneBill phoneBill){
        // this will let us use dump() more easily
        try{
            TextDumper temp = new TextDumper();
            temp.dump(phoneBill);
            return 1;
        }catch(IOException e){
            System.err.println("File could not be written.");
            return -1;
        }
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        // If a file of the same name exists, it will be overwritten, we only want the contents
        // of the phoneBill passed in. This means that there's no support for customers with the same name.
        // We could fix this by assigning each customer with a unique ID as their phonebills are instantiated, but
        // this is not part of the assignment.
        FileWriter file = new FileWriter(phoneBill.getCustomer() + ".txt");
        file.write("BILL FOR: " + phoneBill.getCustomer() + "\n");
        file.flush();
        for(Object call: phoneBill.getPhoneCalls()){
            file.write(((PhoneCall)call).toString() + "\n");
            file.flush();
        }
        file.close();
    }
}
