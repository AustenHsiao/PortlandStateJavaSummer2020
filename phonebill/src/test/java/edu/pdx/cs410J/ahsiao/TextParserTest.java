package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TextParserTest {

    @Test
    public void readingFromNonExistentFileShouldThrowFileNotFound(){
        // If we throw a filenotfound exception, we should still end up making a new phonebill for the command line name.
        TextParser test = new TextParser("commandLineNameExample");
        assertEquals(test.read("abcdefg.txt").getCustomer(), "commandLineNameExample");
    }

    @Test
    public void readingFromIncorrectlyFormattedFileShouldGiveParserError(){
        // If the text file is incorrectly formatted, we should not make a phonebill.
        try {
            FileWriter malformedField = new FileWriter("formatTest.txt");
            malformedField.write("This is a random string that makes this text file incorrectly formatted.");
            malformedField.flush();
            malformedField.close();
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("commandLineNameExample");
        assertNull(test.read("formatTest.txt"));
        File a = new File("formatTest.txt");
        a.delete();
    }

    @Test
    public void readingFromCorrectlyGeneratedTextFile(){
        try {
            FileWriter file = new FileWriter("BobFile1.txt");
            file.write("BILL FOR: Bob\n");
            file.flush();
            file.write("Phone call from 503-111-1111 to 503-222-2222 from 01/11/2020 11:00 AM to 1/11/2020 1:09 AM\n");
            file.flush();
            file.close();
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("BobFile1.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 1);
        File closefile = new File("BobFile1.txt");
        closefile.delete();
    }

    @Test
    public void readingFromEmptyTextFile(){
        // Trying to read in a phone bill from an empty file should not create a new phone bill
        try {
            FileWriter empty = new FileWriter("Empty.txt");
            TextParser test = new TextParser("Bob");
            assertNull(test.read("Empty.txt"));
            empty.close();
            File a = new File("Empty.txt");
            a.delete();
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }

    }

    @Test
    public void readingFromTextFileButIndividualFieldIsInvalid(){
        // If one of the fields inside the text file is malformed, no phone bill should be created
        try {
            FileWriter malformedField = new FileWriter("malformedField.txt");
            malformedField.write("BILL FOR: Bob\n");
            malformedField.flush();
            malformedField.write("Phone call from 503-1a1-1111 to 503-222-2222 from 01/11/2020 13:00 to 1/11/2020 13:09\n");
            malformedField.flush();
            malformedField.close();
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("Bob");
        assertNull(test.read("malformedField.txt"));
        File a = new File("malformedField.txt");
        a.delete();
    }

}
