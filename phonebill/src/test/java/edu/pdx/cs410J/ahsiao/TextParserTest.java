package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TextParserTest {

    @Test
    public void readingFromNonExistentFileShouldThrowFileNotFound(){
        TextParser test = new TextParser();
        assertEquals(test.read("abcdefg.txt").getCustomer(), "1");
    }

    @Test
    public void readingFromIncorrectlyFormattedFileShouldGiveParserError(){
        TextParser test = new TextParser();
        assertEquals(test.read("formatTest.txt").getCustomer(), "1");
    }
}
