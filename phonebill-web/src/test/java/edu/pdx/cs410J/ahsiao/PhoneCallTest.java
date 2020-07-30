package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

 /* private PhoneCall dummyPhoneCall(){
    return new PhoneCall(
            "111-111-1111",
            "222-222-2222",
            "1/11/2025",
            "19:00",
            "02/22/2020",
            "19:10");
  }

  @Test
  public void gettingCallerNo(){
    PhoneCall test = dummyPhoneCall();
    assertEquals(test.getCaller(), "111-111-1111");
  }

  @Test
  public void gettingCalleeNo(){
    PhoneCall test = dummyPhoneCall();
    assertEquals(test.getCallee(), "222-222-2222");
  }

  @Test
  public void getStartTimeString() {
    PhoneCall call = dummyPhoneCall();
    assertEquals(call.getStartTimeString(), "1/11/2025 19:00");
  }

  @Test
  public void getEndTimeString() {
    PhoneCall call = dummyPhoneCall();
    assertEquals(call.getEndTimeString(), "02/22/2020 19:10");
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = dummyPhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }*/

  private PhoneCall P3PhoneCall(){
    return new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "4:00", "pm", "01/05/2020", "4:15", "pm");
  }

  @Test
  public void forProject3getStarTTimeValid(){
    assertThat(P3PhoneCall().getStartTimeString(), containsString("4:00"));
  }

  @Test
  public void forProject3getEndTimeValid(){
    assertThat(P3PhoneCall().getEndTimeString(), containsString("4:15"));
  }

  @Test
  public void forProject3SortAllDifferent(){
    PhoneCall A =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "4:00", "pm", "01/05/2020", "4:15", "pm");
    PhoneCall B =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
    PhoneCall C =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "5:00", "pm", "01/05/2020", "4:15", "pm");

    ArrayList<PhoneCall> test= new ArrayList<>();
    test.add(A);
    test.add(B);
    test.add(C);
    Collections.sort(test);

    assertEquals(test.get(0).equals(B), true);
    assertEquals(test.get(1).equals(A), true);
    assertEquals(test.get(2).equals(C), true);
  }

  @Test
  public void forProject3SortSameTime(){
    PhoneCall A =  new PhoneCall("113-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
    PhoneCall B =  new PhoneCall("112-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");
    PhoneCall C =  new PhoneCall("111-111-1111", "222-222-2222", "01/05/2020", "3:00", "pm", "01/05/2020", "4:15", "pm");

    ArrayList<PhoneCall> test= new ArrayList<>();
    test.add(A);
    test.add(B);
    test.add(C);
    Collections.sort(test);

    assertEquals(test.get(0).equals(C), true);
    assertEquals(test.get(1).equals(B), true);
    assertEquals(test.get(2).equals(A), true);
  }
}
