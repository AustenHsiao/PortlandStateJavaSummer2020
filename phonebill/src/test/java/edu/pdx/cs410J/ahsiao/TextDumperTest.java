package edu.pdx.cs410J.ahsiao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextDumperTest {

    @Test
    public void successfullyMakesAFile(){
        // Tests that dump() creates a file. I'm not checking if the contents are correct, since that will be the function of TextParser
        PhoneCall example1 = new PhoneCall("503-111-1111", "503-222-2222", "01/11/2020", "13:00", "1/11/2020", "13:09");
        PhoneBill bob = new PhoneBill("Bob", example1);
        assertEquals(TextDumper.write(bob), 1);
    }

    @Test
    public void illegalCharInPhoneBillNameShouldThrowIOException(){
        // If there's an illegal char in the name, an IO exception should be thrown since the file cannot be named. '*' and '?' are both illegal chars on windows; '/' is illegal in linux
        PhoneCall example1 = new PhoneCall("503-111-1111", "503-222-2222", "01/11/2020", "13:00", "1/11/2020", "13:09");
        PhoneBill bob = new PhoneBill("/*?\u0000", example1);
        assertEquals(TextDumper.write(bob), -1);
    }

}
