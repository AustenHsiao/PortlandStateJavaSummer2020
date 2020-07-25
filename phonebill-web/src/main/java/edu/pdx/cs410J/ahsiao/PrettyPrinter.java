package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    String filename;

    /**
     * PrettyPrinter constructor requires a filename. Note that the filename could be "-"
     * @param prettyFileName
     */
    PrettyPrinter(String prettyFileName){
        filename = prettyFileName;
    }

    /**
     * WriteToFile is the static method by which the user can dump a pretty print.
     * By specifying "-", the method will invoke dump which will pretty print out the phonebill.
     * By specifying a filename, the method will invoke dump and pretty print to a file.
     * @param prettyFileName
     * @param phoneBill
     * @return
     */
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

    public static int writeOut(PhoneBill phoneBill, Date start, Date end){
        try {
            PrettyPrinter temp = new PrettyPrinter("");
            return temp.dump(phoneBill, start, end);

        }catch(IOException e){
            System.err.println("Something went wrong when trying to write to file.");
            return -1;
        }
    }

    public static void writeOut(PhoneCall call){
        try {
            PrettyPrinter temp = new PrettyPrinter("");
            temp.dump(call);
            return;

        }catch(IOException e){
            System.err.println("Something went wrong when trying to write to file.");
            return;
        }
    }

    /**
     * Dump will use the Collection returned by phonebill's getPhoneCalls() method and sort all by the natural ordering.
     * Then depending on the file name, will either print out or write to file.
     * @param phoneBill
     * @throws IOException
     */
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
                System.out.print("Call duration: " + duration_minutes + " minutes\n\n");
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
                fw.write("Call duration: " + duration_minutes + " minutes\n\n");
                fw.flush();
            }
            fw.close();
        }
    }

    public int dump(PhoneBill phoneBill, Date start, Date end) throws IOException {
        Collection<PhoneCall> temp = phoneBill.getPhoneCalls();
        ArrayList<PhoneCall> phoneCall_Collection = (ArrayList<PhoneCall>)temp;
        phoneCall_Collection.sort(null);
        int count = 0;

        // pretty print
        for(PhoneCall callLine: phoneCall_Collection){
            if(callLine.getStartTime().after(start) && callLine.getStartTime().before(end)) {
                // Duration is the final time - end time.
                Long duration = callLine.getEndTime().getTime() - callLine.getStartTime().getTime();
                int duration_minutes = (int) (duration / 6e+4);

                System.out.print("Call from " + callLine.getCaller() + " to " + callLine.getCallee() + "\n");
                System.out.print("Call start time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getStartTime()) + "\n");
                System.out.print("Call end time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(callLine.getEndTime()) + "\n");
                System.out.print("Call duration: " + duration_minutes + " minutes\n\n");
                count++;
            }
        }
        return count;
    }

    public void dump(PhoneCall call) throws IOException {
        Long duration = call.getEndTime().getTime() - call.getStartTime().getTime();
        int duration_minutes = (int) (duration / 6e+4);

        System.out.print("Call from " + call.getCaller() + " to " + call.getCallee() + "\n");
        System.out.print("Call start time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(call.getStartTime()) + "\n");
        System.out.print("Call end time: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(call.getEndTime()) + "\n");
        System.out.print("Call duration: " + duration_minutes + " minutes\n\n");
    }
}
