package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

  private PhoneCall dummyPhoneCall(){
    return new PhoneCall(
            "Austen",
            "111-111-1111",
            "222-222-2222",
            "1/11/2025",
            "19:00",
            "2/22/2020",
            "19:10");
  }

  @Test
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = dummyPhoneCall();
    assertEquals(call.getStartTimeString(), "19:00 1/11/2025");
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = dummyPhoneCall();
    assertThat(call.getCallee(), is("222-222-2222"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = dummyPhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

}
