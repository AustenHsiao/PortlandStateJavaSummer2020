package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from {@link Project1IT} which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
public class Project1Test {

  @Test
  public void invalidPhoneNumberTest1(){
    assertEquals(Project1.validPhoneNumber("5"), false);
  }

  @Test
  public void invalidPhoneNumberTest2(){
    assertEquals(Project1.validPhoneNumber("5a3-111-1111"), false);
  }

  @Test
  public void invalidPhoneNumberTest3(){
    assertEquals(Project1.validPhoneNumber("503-111-11112"), false);
  }

  @Test
  public void validPhoneNumberTest(){
    assertEquals(Project1.validPhoneNumber("503-111-1111"), true);
  }

  @Test
  public void invalidDateTest1(){
    assertEquals(Project1.validDate("5"), false);
  }

  @Test
  public void invalidDateTest2(){
    assertEquals(Project1.validDate("001/01/2020"), false);
  }

  @Test
  public void invalidDateTest3(){
    assertEquals(Project1.validDate("001/1/2020"), false);
  }

  @Test
  public void invalidDateTest4(){
    assertEquals(Project1.validDate("1/001/2020"), false);
  }

  @Test
  public void invalidDateTest5(){
    assertEquals(Project1.validDate("13/01/2020"), false);
  }

  @Test
  public void invalidDateTest6(){
    assertEquals(Project1.validDate("12/32/2020"), false);
  }

  @Test
  public void validDateTest1(){
    assertEquals(Project1.validDate("01/1/2020"), true);
  }

  @Test
  public void validDateTest2(){
    assertEquals(Project1.validDate("1/1/2020"), true);
  }

  @Test
  public void invalidTimeTest1(){
    assertEquals(Project1.validTime("00:60"), false);
  }

  @Test
  public void invalidTimeTest2(){
    assertEquals(Project1.validTime("25:00"), false);
  }

  @Test
  public void invalidTimeTest3(){
    assertEquals(Project1.validTime("000:60"), false);
  }

  @Test
  public void invalidTimeTest4(){
    assertEquals(Project1.validTime("a0:60"), false);
  }

  @Test
  public void validTimeTest1(){
    assertEquals(Project1.validTime("00:59"), true);
  }

  @Test
  public void validTimeTest2(){
    assertEquals(Project1.validTime("24:59"), true);
  }

  @Test
  public void validTimeTest3(){
    assertEquals(Project1.validTime("00:00"), true);
  }

  @Test
  public void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt");
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }
}
