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
    // single number
    assertEquals(Project1.validPhoneNumber("5"), false);
  }

  @Test
  public void invalidPhoneNumberTest2(){
    // phone number with an alpha char in it
    assertEquals(Project1.validPhoneNumber("5a3-111-1111"), false);
  }

  @Test
  public void invalidPhoneNumberTest3(){
    // too many numbers
    assertEquals(Project1.validPhoneNumber("503-111-11112"), false);
  }

  @Test
  public void validPhoneNumberTest(){
    // a valid string
    assertEquals(Project1.validPhoneNumber("503-111-1111"), true);
  }

  @Test
  public void invalidDateTest1(){
    // a single number
    assertEquals(Project1.validDate("5"), false);
  }

  @Test
  public void invalidDateTest2(){
    // too many digits in the first section, 2 digits in the second, and 4 for the year
    assertEquals(Project1.validDate("001/01/2020"), false);
  }

  @Test
  public void invalidDateTest3(){
    // too many digits in the first section, 1 digit in the second, and 4 for the year
    assertEquals(Project1.validDate("001/1/2020"), false);
  }

  @Test
  public void invalidDateTest4(){
    // 1 digit in the first section, too many digits in the second, 4 digits for the year
    assertEquals(Project1.validDate("1/001/2020"), false);
  }

  @Test
  public void invalidDateTest5(){
    // there is no 13th month that I know of
    assertEquals(Project1.validDate("13/01/2020"), false);
  }

  @Test
  public void invalidDateTest6(){
    // no month has 32 days
    assertEquals(Project1.validDate("12/32/2020"), false);
  }

  @Test
  public void invalidDateTest7(){
    // Arbitrarily declare the year 2000 to be the earliest year we can use
    assertEquals(Project1.validDate("12/25/1999"), false);
  }

  @Test
  public void invalidDateTest8(){
    // invalid year
    assertEquals(Project1.validDate("12/25/a"), false);
  }

  @Test
  public void invalidDateTest9(){
    // invalid year
    assertEquals(Project1.validDate("12/25/a123"), false);
  }

  @Test
  public void validDateTest1(){
    // a valid date but the first section is written with a leading zero while the second section does not
    assertEquals(Project1.validDate("01/1/2020"), true);
  }

  @Test
  public void validDateTest2(){
    // valid date with no leading zeroes
    assertEquals(Project1.validDate("1/1/2020"), true);
  }

  @Test
  public void validDateTest3(){
    // valid date-- no upper limit on the year
    assertEquals(Project1.validDate("1/1/2025"), true);
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
  public void readMeOptions1(){
    // no arguments
    String[] args = {};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), -1);
  }

  @Test
  public void readMeOptions2(){
    // one argument that isn't "-README"
    String[] args = {"Hi"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), -1);
  }

  @Test
  public void readMeOptions3(){
    // one argument that IS "-README"
    String[] args = {"-README"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
  }

  @Test
  public void readMeOptions4(){
    // two arguments that both are not "-README"
    // these strings don't make sense in the context of this project, but the error handling is taken care
    // of somewhere else
    String[] args = {"hi", "everybody"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 0);
  }

  @Test
  public void readMeOptions5(){
    // two arguments where index 0 is "-README"
    String[] args = {"-README", "everybody"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
  }

  @Test
  public void readMeOptions6(){
    // two arguments where index 1 is "-README"
    String[] args = {"hi", "-README"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
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
