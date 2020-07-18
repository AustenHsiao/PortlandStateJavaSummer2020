package edu.pdx.cs410J.ahsiao;
import java.io.FileWriter;
import java.io.IOException;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper<PhoneBill>{

    String dumpFileName;

    /**
     * Constructor takes in the filename that is supplied at the command line.
     * This is the filename we will dump to.
     * @param fileName
     */
    TextDumper(String fileName){
            dumpFileName = fileName;
    }

    /**
     * The write() method exists to make using TextDumper easier. Instead of having to instantiate a TextDumper object,
     * then calling dump(), a user can use this static method.
     * @param fileName name of file we will write to
     * @param phoneBill phone bill we want to dump into the file
     * @return
     */
    public static int write(String fileName, PhoneBill phoneBill){
        // this will let us use dump() more easily
        try{
            TextDumper temp = new TextDumper(fileName);
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
        FileWriter file = new FileWriter(dumpFileName);
        file.write("BILL FOR: " + phoneBill.getCustomer() + "\n");
        file.flush();
        for(Object call: phoneBill.getPhoneCalls()){
            file.write(((PhoneCall)call).toString() + "\n");
            file.flush();
        }
        file.close();
    }
}
