package edu.pdx.cs410J.ahsiao;

import java.io.FileWriter;
import java.io.IOException;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper<PhoneBill>{

    /**
     * the write() method exists to make using TextDumper easier. Instead of having to instantiate a TextDumper object,
     * then calling dump(), a user can use this static method.
     * @param phoneBill The PhoneBill to dump to text file
     * @return int-- mostly used for testing and debugging
     */
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

    /**
     * dump is the implementation of the abstract method from PhoneBillDumper.
     * This method takes in a PhoneBill to dump. Based on the customer name, a text
     * file is created and each PhoneCall in the object is written to the file using the
     * toString() method.
     * @param phoneBill PhoneBill to dump to text
     * @throws IOException
     */
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
