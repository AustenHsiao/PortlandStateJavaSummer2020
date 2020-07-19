package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    String filename;

    PrettyPrinter(String prettyFileName){
        filename = prettyFileName;
    }

    public static int writeToFile(String prettyFileName, PhoneBill phoneBill){
        try {
            PrettyPrinter temp = new PrettyPrinter(prettyFileName);
            temp.dump(phoneBill);
            return 1;
        }catch(IOException e){
            System.err.println("Something went wrong when trying to write to file.");
            return -1;
        }
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        Collection<PhoneCall> temp = phoneBill.getPhoneCalls();
        ArrayList<PhoneCall> phoneCall_Collection = (ArrayList<PhoneCall>)temp;
        phoneCall_Collection.sort(null);

        // pretty print
        if(filename.equals("-")){
            for(PhoneCall callLine: phoneCall_Collection){
                // Duration is the final time - end time.
                Long duration = callLine.getEndTime().getTime() - callLine.getStartTime().getTime();
                int duration_minutes = (int)(duration / 6e+4);

                System.out.print("Call from " + callLine.getCaller() + " to " + callLine.getCallee() + "\n");
                System.out.print("Call start time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getStartTime()) + "\n");
                System.out.print("Call end time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getEndTime()) + "\n");
                System.out.print("Call duration: " + duration_minutes + "\n\n");
            }
        }else{
            // pretty save to file
            FileWriter fw = new FileWriter(filename);
            for(PhoneCall callLine: phoneCall_Collection){
                // Duration is the final time - end time.
                Long duration = callLine.getEndTime().getTime() - callLine.getStartTime().getTime();
                int duration_minutes = (int)(duration / 6e+4);

                fw.flush();
                fw.write("Call from " + callLine.getCaller() + " to " + callLine.getCallee() + "\n");
                fw.flush();
                fw.write("Call start time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getStartTime()) + "\n");
                fw.flush();
                fw.write("Call end time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getEndTime()) + "\n");
                fw.flush();
                fw.write("Call duration (mins): " + duration_minutes + "\n\n");
                fw.flush();
            }
            fw.close();
        }
    }
}
