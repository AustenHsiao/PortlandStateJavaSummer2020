package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class Project2IT extends InvokeMainTestCase {

    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    @Test
    public void invokeMainWithZeroArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertEquals(result.getExitCode().toString(), "-1");
    }

    @Test
    public void invokeMainWithTooManyArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command line arguments"));
        assertEquals(result.getExitCode().toString(), "-2");
    }

    @Test
    public void invokeMainWithREADME1(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Written by Austen Hsiao for CS510, assignment 2."));
    }

    @Test
    public void invokeMainWithREADME2(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "1", "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Written by Austen Hsiao for CS510, assignment 2."));
    }

    @Test
    public void invokeMainWithREADME3(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "1", "1", "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Written by Austen Hsiao for CS510, assignment 2."));
    }

    @Test
    public void invokeMainWithREADME4(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "1", "1", "1", "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Written by Austen Hsiao for CS510, assignment 2."));
    }

    @Test
    public void invokeMain5ArgumentsNoReadme(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "1", "1", "1", "1", "1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertEquals(result.getExitCode().toString(), "-1");
    }

    @Test
    public void invokeMainValidArgumentsNoOptionsBehavesAsProject1(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertEquals(result.getExitCode().toString(), "0");
    }

    @Test
    public void invokeMainValidArgumentsWithPrintNoFileBehavesAsProject1(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "-print", "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardOut(),  containsString("Phone call from 503-123-1234 to 503-544-5678 from 01/11/2011 01:00 to 01/12/2020 01:11"));
        assertEquals(result.getExitCode().toString(), "0");
    }

    @Test
    public void invokeMainValidArgumentsWithSpecifiedFileButMismatchedNames(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "-textFile", "Bob.txt", "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(),  containsString("Name in file does not match command line argument and/or malformed text file."));
        assertEquals(result.getExitCode().toString(), "-3");
    }

    @Test
    public void invokeMainValidArgumentsWithSpecifiedFileButMatchingNames(){
        // Should run to completion and we can manually verify that Bob.txt has been updated
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "-textFile", "Bob.txt", "Bob", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertEquals(result.getExitCode().toString(), "0");
    }

    @Test
    public void invokeMainValidArgumentsWithSpecifiedFileButFileDoesntExist(){
        // Should run to completion and we can manually verify that Bob.txt has been updated
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "-textFile", "aName.txt", "Bob", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertEquals(result.getExitCode().toString(), "0");
    }

    @Test
    public void invokeMainWithBadPhoneNumber1(){
        // args[1] has a bad phone number-- too many digits in the last section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-12334", "503-230-5678", "01/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid phone number"));
        assertThat(result.getExitCode(), equalTo(3));
    }

    @Test
    public void invokeMainWithBadPhoneNumber2(){
        // args[2] has a bad phone number-- non digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-hi4-5678", "01/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid phone number"));
        assertThat(result.getExitCode(), equalTo(3));
    }

    @Test
    public void invokeMainWithBadDate1(){
        // args[3] has a bad date-- too many digits in the first section. We're not testing the date validating method (this is tested in project1test), just the logic in main,
        // So all I care is that the date in args[3] is bad.
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-124-5678", "001/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertThat(result.getExitCode(), equalTo(4));
    }

    @Test
    public void invokeMainWithBadDate2(){
        // args[5] has a bad date-- too many digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-544-5678", "01/101/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertThat(result.getExitCode(), equalTo(4));
    }

    @Test
    public void invokeMainWithBadTime1(){
        // args[4] has a bad time-- too many digits in the first section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "001:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertThat(result.getExitCode(), equalTo(5));
    }

    @Test
    public void invokeMainWithBadTime2(){
        // args[6] has a bad time-- too many digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:101");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertThat(result.getExitCode(), equalTo(5));
    }
}
