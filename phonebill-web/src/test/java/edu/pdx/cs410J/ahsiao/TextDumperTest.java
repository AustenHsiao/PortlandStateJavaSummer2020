package edu.pdx.cs410J.ahsiao;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class TextDumperTest {

    @Test
    public void successfullyMakesAFile(){
        // Tests that dump() creates a file. I'm not checking if the contents are correct, since that will be the function of TextParser
        PhoneCall example1 = new PhoneCall("503-111-1111", "503-222-2222", "01/11/2020", "8:00", "pm", "1/11/2020", "8:09", "pm");
        PhoneBill bob = new PhoneBill("Bob", example1);
        assertEquals(TextDumper.write( "BobFile.txt" , bob), 1);

        File bobFile = new File("BobFile.txt");
        bobFile.delete();
    }

    @Test
    public void DumpNoSearch(){
        // Tests that dump() creates a file. I'm not checking if the contents are correct, since that will be the function of TextParser
        PhoneCall example1 = new PhoneCall("503-111-1111", "503-222-2222", "01/11/2020", "8:00", "pm", "1/11/2020", "8:09", "pm");
        PhoneBill bob = new PhoneBill("Bob", example1);

        PhoneCall example = new PhoneCall("503-111-1231", "503-222-5672", "03/11/2020", "11:00", "pm", "03/11/2020", "11:09", "pm");
        bob.addPhoneCall(example);

        example = new PhoneCall("503-111-1231", "503-222-5672", "03/12/2020", "11:00", "pm", "03/12/2020", "11:09", "pm");
        bob.addPhoneCall(example);

        File newFile = new File("TestFile.txt");

        try {
            PrintWriter pw = new PrintWriter("TestFile.txt");
            assertEquals(TextDumper.write(pw, bob), 3);
            newFile.delete();
        }catch(FileNotFoundException e){
            System.err.println("Not found");
        }
    }

    @Test
    public void DumpYesSearch(){
        // Tests that dump() creates a file. I'm not checking if the contents are correct, since that will be the function of TextParser
        PhoneCall example1 = new PhoneCall("503-111-1111", "503-222-2222", "01/11/2020", "8:00", "pm", "1/11/2020", "8:09", "pm");
        PhoneBill bob = new PhoneBill("Bob", example1);

        PhoneCall example = new PhoneCall("503-111-1231", "503-222-5672", "03/11/2020", "11:00", "pm", "03/11/2020", "11:09", "pm");
        bob.addPhoneCall(example);

        example = new PhoneCall("503-111-1231", "503-222-5672", "03/12/2020", "11:00", "pm", "03/12/2020", "11:09", "pm");
        bob.addPhoneCall(example);

        File newFile = new File("TestFile.txt");
        PhoneCall ref = new PhoneCall("111-111-1111", "222-222-2222", "03/10/2020", "1:00", "pm", "3/13/2020", "5:00", "pm");

        try {
            PrintWriter pw = new PrintWriter("TestFile.txt");
            assertEquals(TextDumper.write(pw, bob, ref.getStartTime(), ref.getEndTime()), 2);
            newFile.delete();
        }catch(FileNotFoundException e){
            System.err.println("Not found");
        }
    }
}
