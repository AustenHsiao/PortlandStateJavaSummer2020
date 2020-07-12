package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

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
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("commandLineNameExample");
        assertNull(test.read("formatTest.txt"));
    }

    @Test
    public void readingFromCorrectlyGeneratedTextFile(){
        // BobFile is made in a different test (project2 main test)
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("BobFile.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 1);
    }

    @Test
    public void readingFromEmptyTextFile(){
        // Trying to read in a phone bill from an empty file should not create a new phone bill
        try {
            FileWriter empty = new FileWriter("Empty.txt");
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("Bob");
        assertNull(test.read("Empty.txt"));
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
        }catch(IOException e){
            System.out.println("This test has failed due to IOException");
        }
        TextParser test = new TextParser("Bob");
        assertNull(test.read("malformedField.txt"));
    }

}
