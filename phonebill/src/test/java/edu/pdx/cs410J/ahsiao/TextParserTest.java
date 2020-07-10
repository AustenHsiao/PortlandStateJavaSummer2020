package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextParserTest {

    @Test
    public void readingFromNonExistentFileShouldThrowFileNotFound(){
        // If we throw a filenotfound exception, we should still end up making a new phonebill for the command line name.
        TextParser test = new TextParser("commandLineNameExample");
        assertEquals(test.read("abcdefg.txt").getCustomer(), "commandLineNameExample");
    }

    @Test
    public void readingFromIncorrectlyFormattedFileShouldGiveParserError(){
        // If the text file is incorrectly formatted, we do the same as above-- make an empty phone bill
        TextParser test = new TextParser("commandLineNameExample");
        assertEquals(test.read("formatTest.txt").getCustomer(), "commandLineNameExample");
    }

    @Test
    public void readingFromCorrectlyGeneratedTextFile(){
        // Bob.txt is correctly formatted with 1 phone call, so the length of the phone bill list should be 1, along with the right name.
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("Bob.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 1);
    }

    @Test
    public void readingFromEmptyTextFile(){
        // reading from an empty file causes parser exception which is handled inside read().
        // The result will be an empty phone bill.
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("Empty.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 0);
    }

    @Test
    public void readingFromTextFileButIndividualFieldIsInvalid(){
        // If one of the fields inside the text file is malformed, it also returns
        // and empty PhoneBill.
        TextParser test = new TextParser("Bob");
        PhoneBill bobPhoneBill = test.read("FunkyBob.txt");
        assertEquals(bobPhoneBill.getCustomer(), "Bob");
        assertEquals(bobPhoneBill.getPhoneCalls().size(), 0);
    }

}
