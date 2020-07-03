package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    @Test
    public void invokeMainWithZeroArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void invokeMainWithREADME1(){
        // Doesnt matter where the -README is as long as it's one of the first two arguments
        // If we print the readme, it will always result in exit code 2
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "-README", "random");
        assertThat(result.getExitCode(), equalTo(2));
    }

    @Test
    public void invokeMainWithREADME2(){
        // Doesnt matter where the -README is as long as it's one of the first two arguments.
        // If we print the readme, it will always result in exit code 2
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "random", "-README");
        assertThat(result.getExitCode(), equalTo(2));
    }

    @Test
    public void invokeMainWithOnlyTwoArgumentsNoReadme(){
        // If there are only two arguments and none of them are -README, then it should tell the user that we're missing stuff
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "hi", "heyyy");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithOnlyThreeArgumentsNoReadme(){
        // Actually, having <6 arguments in which none of them are equal to -README makes no sense
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "random", "hi", "heyyy");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithTooManyArgumentsNoReadme(){
        // Having >9 arguments in which none of them are equal to -README makes no sense.
        // We have space for the bill name, 2 phone numbers, 2 dates, and 2 times (and maybe -print and -README), which is 9 things maximum
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "random", "hi", "heyyy", "haaaaaaay", "ok", "there", "7", "beep", "boop", "test");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadPhoneNumber1(){
        // args[1] has a bad phone number-- too many digits in the last section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-12334", "503-230-5678", "01/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid phone number"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadPhoneNumber2(){
        // args[2] has a bad phone number-- non digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-hi4-5678", "01/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid phone number"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadDate1(){
        // args[3] has a bad date-- too many digits in the first section. We're not testing the date validating method (this is tested in project1test), just the logic in main,
        // So all I care is that the date in args[3] is bad.
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-124-5678", "001/11/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadDate2(){
        // args[5] has a bad date-- too many digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-544-5678", "01/101/2011", "1:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid date"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadTime1(){
        // args[4] has a bad time-- too many digits in the first section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "001:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithBadTime2(){
        // args[6] has a bad time-- too many digits in the second section
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:101");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokeMainWithValidEntriesNoPrint(){
        // If everything goes well and we DONT specify -print, exit with exit code 0
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokeMainWithValidEntriesYesPrint(){
        // If everything goes well and we DO specify -print, print out the phonecall toString() and exit with exit code 0
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "-print", "name", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokeMainWithValidEntriesYesPrintButOutOfOrder(){
        // If everything goes well and we DO specify -print, print out the phonecall toString() and exit with exit code 0
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class, "name", "-print", "503-123-1234", "503-544-5678", "01/11/2011", "01:00", "01/12/2020", "01:11");
        assertThat(result.getTextWrittenToStandardError(), containsString("Command line arguments out of order"));
        assertThat(result.getExitCode(), equalTo(1));
    }
}