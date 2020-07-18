package edu.pdx.cs410J.ahsiao;
import org.junit.Test;

import java.io.File;

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

}
