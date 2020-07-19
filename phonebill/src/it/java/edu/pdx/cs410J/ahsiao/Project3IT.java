package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class Project3IT extends InvokeMainTestCase{

    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    @Test
    public void noArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line"));
        assertEquals(result.getExitCode().toString(), "1");
    }

    @Test
    public void OneArgumentReadme(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-README");
        assertEquals(result.getExitCode().toString(), "-1");
    }

    @Test
    public void OneArgumentNoReadme(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "hi");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line"));
        assertEquals(result.getExitCode().toString(), "2");
    }

    @Test
    public void TooManyArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-pretty", "prettyFileName", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am", "1", "2", "3", "4");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command line"));
        assertEquals(result.getExitCode().toString(), "5");
    }

    @Test
    public void MoreThan6ArgumentsWithPretty(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-pretty", "prettyFileName.txt", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertEquals(result.getExitCode().toString(), "15");
        File a = new File("prettyFileName.txt");
        a.delete();
    }

    @Test
    public void MoreThan6ArgumentsWithTextFile(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-textFile", "textFileName.txt", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertEquals(result.getExitCode().toString(), "15");
        File a = new File("textFileName.txt");
        a.delete();
    }

    @Test
    public void MoreThan6ArgumentsWithPrint(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-print", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardOut(), containsString("111-111-1111"));
        assertEquals(result.getExitCode().toString(), "15");
    }

    @Test
    public void MoreThan6ArgumentsWithReadme(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-README", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardOut(), containsString("This program is used to"));
        assertEquals(result.getExitCode().toString(), "3");
    }

    @Test
    public void MoreThan6ArgumentsWithUnrecognizedOptions(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-hi", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unrecognized"));
        assertEquals(result.getExitCode().toString(), "4");
    }

    @Test
    public void MoreThan6ArgumentsWithUnrecognizedAndPretty(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "-hi", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unrecognized"));
        assertEquals(result.getExitCode().toString(), "4");
    }

    @Test
    public void TooManyArgsAfterTheOptions(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am", "extra");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments"));
        assertEquals(result.getExitCode().toString(), "6");
    }

    @Test
    public void TooFewArgsAfterTheOptions(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing arguments"));
        assertEquals(result.getExitCode().toString(), "7");
    }

    @Test
    public void startAM_PM_InvalidFormat(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "gm", "1/1/2020", "1:13", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect start time"));
        assertEquals(result.getExitCode().toString(), "8");
    }

    @Test
    public void endAM_PM_InvalidFormat(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "gm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect end time"));
        assertEquals(result.getExitCode().toString(), "9");
    }

    @Test
    public void badCallerNumber(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "11a-111-1111", "222-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid number"));
        assertEquals(result.getExitCode().toString(), "10");
    }

    @Test
    public void badCalleeNumber(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "22a-222-2222", "1/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid number"));
        assertEquals(result.getExitCode().toString(), "10");
    }

    @Test
    public void badStartDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "001/1/2020", "1:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertEquals(result.getExitCode().toString(), "11");
    }

    @Test
    public void badEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "01/1/2020", "1:00", "am", "001/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertEquals(result.getExitCode().toString(), "11");
    }

    @Test
    public void badStartTime(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "01/1/2020", "001:00", "am", "1/1/2020", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertEquals(result.getExitCode().toString(), "12");
    }

    @Test
    public void badEndTime(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "01/1/2020", "01:00", "am", "1/1/2020", "13:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertEquals(result.getExitCode().toString(), "12");
    }

    @Test
    public void EndTimeBeforeStartTime(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "name", "111-111-1111", "222-222-2222", "01/1/2020", "01:00", "am", "12/1/2019", "1:13", "am");
        assertThat(result.getTextWrittenToStandardError(), containsString("End time occurs before"));
        assertEquals(result.getExitCode().toString(), "13");
    }

    @Test
    public void NamesDontMatch(){
        try {
            FileWriter fw = new FileWriter("testfile.txt");
            fw.write("BILL FOR: Bob\n");
            fw.write("Phone call from 111-111-1111 to 222-222-2222 from 1/5/20, 3:00 PM to 1/5/20, 4:15 PM");
            fw.close();

            InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-textFile", "testfile.txt", "name", "111-111-1111", "222-222-2222", "01/1/2020", "01:00", "am", "1/1/2020", "1:13", "am");
            assertThat(result.getTextWrittenToStandardError(), containsString("Name in file does not match"));
            assertEquals(result.getExitCode().toString(), "14");
            File a = new File("testfile.txt");
            a.delete();
        }catch(IOException e){
            System.err.print(e + " ...failed");
        }
    }

    @Test
    public void NamesDoMatch(){
        try {
            FileWriter fw = new FileWriter("testfile.txt");
            fw.write("BILL FOR: Bob\n");
            fw.write("Phone call from 111-111-1111 to 222-222-2222 from 1/5/20, 3:00 PM to 1/5/20, 4:15 PM");
            fw.close();

            InvokeMainTestCase.MainMethodResult result = invokeMain(Project3.class, "-textFile", "testfile.txt", "Bob", "111-111-1111", "222-222-2222", "01/1/2020", "01:00", "am", "1/1/2020", "1:13", "am");
            assertEquals(result.getExitCode().toString(), "15");
            File a = new File("testfile.txt");
            a.delete();
        }catch(IOException e){
            System.err.print(e + " ...failed");
        }
    }

}
