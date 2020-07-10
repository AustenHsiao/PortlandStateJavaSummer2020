package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
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
}
