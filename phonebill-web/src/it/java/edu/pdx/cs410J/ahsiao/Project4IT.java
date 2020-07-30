package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.ahsiao.PhoneBillRestClient.PhoneBillRestException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
    }

    @Test
    public void BadConnectionFromHostnameorPort(){
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME+"e", "-port", PORT, "Austen");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));

        result = invokeMain( Project4.class, "-host", HOSTNAME+"e", "-port", PORT, "-search", "Austen", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));

        result = invokeMain( Project4.class, "-host", HOSTNAME+"e", "-port", PORT, "-print", "Austen", "111-111-1111", "222-222-2222", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "1", "Austen");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "1", "-search", "Austen", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "1", "-print", "Austen", "111-111-1111", "222-222-2222", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to server"));
    }

    @Test
    public void InvalidArgumentLengths(){
        String[] empty = {};
        MainMethodResult result = invokeMain( Project4.class, empty);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertEquals(result.getExitCode(), 1);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT);
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid arguments"));
        assertEquals(result.getExitCode(), 18);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing arguments"));
        assertEquals(result.getExitCode(), 9);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "random");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid arguments"));
        assertEquals(result.getExitCode(), 18);
    }

    @Test
    public void InvalidArgument(){
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "-1");
        assertThat(result.getTextWrittenToStandardError(), containsString("Port specified must be an int between"));
        assertEquals(result.getExitCode(), 2);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "600000");
        assertThat(result.getTextWrittenToStandardError(), containsString("Port specified must be an int between"));
        assertEquals(result.getExitCode(), 2);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "2", "333-333-3333", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 16);
        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "3a", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 16);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "200/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 20);
        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "3:00", "pm", "002/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 20);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "200:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 21);
        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "3:00", "pm", "2/22/2020", "130:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 21);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "3:00", "ppm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("start time"));
        assertEquals(result.getExitCode(), 22);
        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "ppm");
        assertThat(result.getTextWrittenToStandardError(), containsString("end time"));
        assertEquals(result.getExitCode(), 23);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "222-222-2222", "333-333-3333", "2/23/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("End time occurs before start time"));
        assertEquals(result.getExitCode(), 999);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "Austen", "20/22/2020", "3:00", "ppm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid"));
        assertEquals(result.getExitCode(), 5);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "Austen", "02/23/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("End time is before start time"));
        assertEquals(result.getExitCode(), 11);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-InvalidOption");
        assertThat(result.getTextWrittenToStandardError(), containsString("Option not recognized"));
        assertEquals(result.getExitCode(), 4);

        result = invokeMain( Project4.class, "-host", HOSTNAME);
        assertThat(result.getTextWrittenToStandardError(), containsString("Port number not specified"));
        assertEquals(result.getExitCode(), 14);

        result = invokeMain( Project4.class, "-port", PORT);
        assertThat(result.getTextWrittenToStandardError(), containsString("Hostname not specified"));
        assertEquals(result.getExitCode(), 15);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "Austen", "02/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm", "extra");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments for search function"));
        assertEquals(result.getExitCode(), 10);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "NotInDict", "02/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("No data associated with this customer"));
        assertEquals(result.getExitCode(), 3333);
    }

    @Test
    public void ValidTests(){
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen");
        assertThat(result.getTextWrittenToStandardError(), containsString("No data associated"));
        assertEquals(result.getExitCode(), 9000);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", "Austen", "222-222-2222", "333-333-3333", "2/22/2020", "3:00", "pm", "2/22/2020", "3:15", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Call from"));
        assertEquals(result.getExitCode(), 0);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "503-111-1111", "971-333-3333", "2/28/2020", "7:00", "pm", "2/28/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 0);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Call from"));
        assertEquals(result.getExitCode(), 808);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Austen", "503-111-1111", "971-333-3333", "2/28/2020", "7:00", "pm", "2/28/2020", "7:23", "pm");
        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "Austen", "2/25/2020", "7:00", "pm", "03/4/2020", "7:23", "pm");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Call from"));
        assertEquals(result.getExitCode(), 13);

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "Austen", "6/04/2020", "7:00", "pm", "6/4/2020", "7:23", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("No calls to display between the given"));
        assertEquals(result.getExitCode(), 4444);

    }

    @Test
    public void readme(){
        MainMethodResult result = invokeMain( Project4.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Written by Austen Hsiao for CS510, assignment 4."));
        assertEquals(result.getExitCode(), 3);
    }

    @Test
    public void TryingToSearchWithoutAHostOrPort(){
        MainMethodResult result = invokeMain( Project4.class, "-search", "Austen", "6/04/2020", "7:00", "pm", "6/4/2020", "7:23", "pm");
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot search without being connected"));
        assertEquals(result.getExitCode(), 55);
    }

    @Test
    public void printValidWithNoHostOrPortOrSearch(){
        MainMethodResult result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "6/04/2020", "7:00", "pm", "6/4/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 0);
    }

    @Test
    public void printInvalidWithNoHostOrPortOrSearch(){
        MainMethodResult result = invokeMain( Project4.class, "-print", "Austen", "a11-111-1111", "222-222-2222", "6/04/2020", "7:00", "pm", "6/4/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 16);

        result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "600/04/2020", "7:00", "pm", "6/4/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 20);

        result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "6/04/2020", "7:a00", "pm", "6/4/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 21);

        result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "6/04/2020", "7:00", "ppm", "6/4/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 22);

        result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "6/04/2020", "7:00", "pm", "6/4/2020", "7:23", "ppm");
        assertEquals(result.getExitCode(), 23);

        result = invokeMain( Project4.class, "-print", "Austen", "111-111-1111", "222-222-2222", "6/04/2020", "7:40", "pm", "6/2/2020", "7:23", "pm");
        assertEquals(result.getExitCode(), 999);
    }
}