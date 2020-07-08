package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

public class TextParserTest {

    @Test
    public void readingFromIncorrectFormat(){
        // The format for the text files is as follows:
        //  The first line should be "BILL FOR: <customername>"
        //  All subsequent lines use the toString() method on phonecalls, which has the form:
        //  Phone call from <phone number> to <phone number> from <startTime> <startDate> to <endTime> <endDate>
        //  This means that index 3, 5, 7, 8, 10, and 11 are the important ones.
        TextParser testParser = new TextParser();


    }
}
