package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

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
        TextParser test = new TextParser("commandLineNameExample");
        assertNull(test.read("formatTest.txt"));
    }

    @Test
    public void readingFromCorrectlyGeneratedTextFile(){
        // Bob.txt is correctly formatted with 1 phone call, so the length of the phone bill list should be 1, along with the right name.
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("BobRead.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 1);
    }

    @Test
    public void readingFromEmptyTextFile(){
        // Trying to read in a phone bill from an empty file should not create a new phone bill
        TextParser test = new TextParser("Bob");
        assertNull(test.read("Empty.txt"));
    }

    @Test
    public void readingFromTextFileButIndividualFieldIsInvalid(){
        // If one of the fields inside the text file is malformed, no phone bill should be created
        TextParser test = new TextParser("Bob");
        assertNull(test.read("FunkyBob.txt"));
    }

}
