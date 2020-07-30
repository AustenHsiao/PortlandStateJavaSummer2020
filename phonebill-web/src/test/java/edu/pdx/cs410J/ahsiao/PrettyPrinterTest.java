package edu.pdx.cs410J.ahsiao;
import org.hamcrest.core.StringContains;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class PrettyPrinterTest {

    @Test
    public void prettyPrintMultipleCallsInBillShouldBeInOrderOfCallerNumberIfStartTimeIsAllSame(){
        PhoneCall A =  new PhoneCall("113-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
        PhoneCall B =  new PhoneCall("112-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
        PhoneCall C =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");

        PhoneBill test = new PhoneBill("Bob");
        test.addPhoneCall(A);
        test.addPhoneCall(B);
        test.addPhoneCall(C);

        PrettyPrinter.writeToFile("TestFileName.txt", test);
        try {
            BufferedReader br = new BufferedReader(new FileReader("TestFileName.txt"));
            assertThat(br.readLine(), containsString("111-111-1111"));
            br.readLine();br.readLine();br.readLine();br.readLine();
            assertThat(br.readLine(), containsString("112-111-1111"));
            br.readLine();br.readLine();br.readLine();br.readLine();
            assertThat(br.readLine(), containsString("113-111-1111"));
            br.close();
            File testFile = new File("TestFileName.txt");
            testFile.delete();
        }catch(FileNotFoundException e){
            System.err.println("Something went horribly wrong");
        }catch(IOException e){
            System.err.println("Something went horribly wrong");
        }
    }

    @Test
    public void prettyPrintMultipleCallsInBillShouldBeInOrderIfTheTimesAreDifferent(){
        PhoneCall A =  new PhoneCall("113-111-1111", "222-222-2222", "01/05/2020", "7:00", "am", "01/05/2020", "4:15", "pm");
        PhoneCall B =  new PhoneCall("112-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
        PhoneCall C =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");

        PhoneBill test = new PhoneBill("Bob");
        test.addPhoneCall(A);
        test.addPhoneCall(B);
        test.addPhoneCall(C);

        PrettyPrinter.writeToFile("TestFileName.txt", test);
        try {
            BufferedReader br = new BufferedReader(new FileReader("TestFileName.txt"));
            assertThat(br.readLine(), containsString("113-111-1111"));
            br.readLine();br.readLine();br.readLine();br.readLine();
            assertThat(br.readLine(), containsString("111-111-1111"));
            br.readLine();br.readLine();br.readLine();br.readLine();
            assertThat(br.readLine(), containsString("112-111-1111"));
            br.close();
            File testFile = new File("TestFileName.txt");
            testFile.delete();
        }catch(FileNotFoundException e){
            System.err.println("Something went horribly wrong");
        }catch(IOException e){
            System.err.println("Something went horribly wrong");
        }
    }

    @Test
    public void prettyPrintMultipleCallsToOut() {
        PhoneCall A = new PhoneCall("113-111-1111", "222-222-2222", "01/05/2020", "7:00", "am", "01/05/2020", "4:15", "pm");
        PhoneCall B = new PhoneCall("112-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
        PhoneCall C = new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");

        PhoneBill test = new PhoneBill("Bob");
        test.addPhoneCall(A);
        test.addPhoneCall(B);
        test.addPhoneCall(C);

        assertEquals(PrettyPrinter.writeToFile("-", test), 1);

    }

    @Test
    public void TestDatePretty() {
        PhoneCall A = new PhoneCall("113-111-1111", "222-222-2222", "01/05/2020", "7:00", "am", "01/05/2020", "4:15", "pm");
        PhoneCall B = new PhoneCall("112-111-1111", "222-222-2222", "01/08/2020", "3:00", "pm", "01/08/2020", "4:15", "pm");
        PhoneCall C = new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");

        PhoneBill test = new PhoneBill("Bob");
        test.addPhoneCall(A);
        test.addPhoneCall(B);
        test.addPhoneCall(C);

        PhoneCall ref = new PhoneCall("222-222-2222", "111-111-1111", "01/04/2020", "12:00", "am", "01/06/2020", "12:00", "am");

        assertEquals(PrettyPrinter.writeOut(test, ref.getStartTime(), ref.getEndTime()), 2);

    }
}
