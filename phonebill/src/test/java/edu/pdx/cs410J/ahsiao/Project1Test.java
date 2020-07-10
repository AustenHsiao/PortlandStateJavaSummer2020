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
  public void numberTooShort(){
    // single number
    assertEquals(Project1.validPhoneNumber("5"), false);
  }

  @Test
  public void tooManySectionsInNumber(){
    // phone number with too many -s but correct length
    assertEquals(Project1.validPhoneNumber("503-1-1-1111"), false);
  }

  @Test
  public void tooFewSectionsInNumber(){
    // phone number with too many -s but correct length
    assertEquals(Project1.validPhoneNumber("5034141-1111"), false);
  }

  @Test
  public void sectionOneHasTooManyNumbers(){
    // sections one and two are wonky
    assertEquals(Project1.validPhoneNumber("5034-11-1111"), false);
  }

  @Test
  public void sectionTwoHasTooManyNumbers(){
    // sections two and three are wonky
    assertEquals(Project1.validPhoneNumber("503-1144-111"), false);
  }

  @Test
  public void letterInPhoneNumber(){
    // phone number with an alpha char in it
    assertEquals(Project1.validPhoneNumber("5a3-111-1111"), false);
  }

  @Test
  public void tooManyNumbersInLastSection(){
    // too many numbers
    assertEquals(Project1.validPhoneNumber("503-111-11112"), false);
  }

  @Test
  public void validPhoneNumberTest(){
    // a valid string
    assertEquals(Project1.validPhoneNumber("503-111-1111"), true);
  }

  @Test
  public void dateTooShort(){
    // a single number
    assertEquals(Project1.validDate("5"), false);
  }

  @Test
  public void dateTooLong(){
    // a bunch of numbers
    assertEquals(Project1.validDate("555555555555555"), false);
  }

  @Test
  public void moreThanThreeSections(){
    // too many / chars will end up with >3 sections
    assertEquals(Project1.validDate("0/01/01/2020"), false);
  }

  @Test
  public void lessThanThreeSections(){
    // too many / chars will end up with >3 sections
    assertEquals(Project1.validDate("01501/2020"), false);
  }

  @Test
  public void onlyContainNumbers(){
    // each section should only contain numbers
    assertEquals(Project1.validDate("01/a1/2020"), false);
  }

  @Test
  public void tooManyDigitsInTheFirstSection(){
    // too many digits in the first section
    assertEquals(Project1.validDate("001/01/2020"), false);
  }

  @Test
  public void tooFewDigitsInTheFirstSection(){
    // too few digits in the first section
    assertEquals(Project1.validDate("/01/2020"), false);
  }

  @Test
  public void tooManyDigitsInTheSecondSection(){
    // too many digits in the second section
    assertEquals(Project1.validDate("01/001/2020"), false);
  }

  @Test
  public void tooFewDigitsInTheSecondSection(){
    // too few digits in the second section
    assertEquals(Project1.validDate("01//2020"), false);
  }

  @Test
  public void yearNeedsToBeFourDigitsHigh(){
    // the year must be 4 digits-- no more, no less
    assertEquals(Project1.validDate("12/31/20200"), false);
  }

  @Test
  public void yearNeedsToBeFourDigitsLow(){
    // the year must be 4 digits-- no more, no less
    assertEquals(Project1.validDate("12/31/100"), false);
  }

  @Test
  public void invalidMonthOver(){
    // months must be within [1,12]
    assertEquals(Project1.validDate("13/25/2020"), false);
  }

  @Test
  public void invalidMonthUnder(){
    // months must be within [1,12]
    assertEquals(Project1.validDate("00/25/2020"), false);
  }

  @Test
  public void invalidDayOver(){
    // days must be between 1 and 31
    assertEquals(Project1.validDate("12/40/2020"), false);
  }

  @Test
  public void invalidDayUnder(){
    // days must be between 1 and 31
    assertEquals(Project1.validDate("12/00/2020"), false);
  }

  @Test
  public void monthMustBeGreaterThan1999(){
    // I think a valid date should be after the year 2000
    assertEquals(Project1.validDate("11/20/1990"), false);
  }

  @Test
  public void onAMonthThatCannotHaveThirtyOneDaysFeb(){
    // Certain months never have 31 days
    assertEquals(Project1.validDate("02/31/2020"), false);
  }

  @Test
  public void onAMonthThatCannotHaveThirtyOneDaysApr(){
    // Certain months never have 31 days
    assertEquals(Project1.validDate("4/31/2020"), false);
  }

  @Test
  public void onAMonthThatCannotHaveThirtyOneDaysJun(){
    // Certain months never have 31 days
    assertEquals(Project1.validDate("06/31/2020"), false);
  }

  @Test
  public void onAMonthThatCannotHaveThirtyOneDaysASept(){
    // Certain months never have 31 days
    assertEquals(Project1.validDate("9/31/2020"), false);
  }

  @Test
  public void onAMonthThatCannotHaveThirtyOneDaysNov(){
    // Certain months never have 31 days
    assertEquals(Project1.validDate("11/31/2020"), false);
  }

  @Test
  public void validDateTest1(){
    // a valid date but the first section is written with a leading zero while the second section does not
    assertEquals(Project1.validDate("01/1/2020"), true);
  }

  @Test
  public void validDateTest2(){
    // valid date with no leading zeroes
    assertEquals(Project1.validDate("5/1/2020"), true);
  }

  @Test
  public void validDateTest3(){
    // valid date-- no upper limit on the year (as long as it has 4 digits)
    assertEquals(Project1.validDate("8/1/2025"), true);
  }

  @Test
  public void timeTooShort(){
    // the string is too short
    assertEquals(Project1.validTime("5"), false);
  }

  @Test
  public void timeTooLong(){
    // the string is too short
    assertEquals(Project1.validTime("5555555555"), false);
  }

  @Test
  public void tooManySections(){
    assertEquals(Project1.validTime("0:0:60"), false);
  }

  @Test
  public void sectionNotAllNumber(){
    assertEquals(Project1.validTime("a5:00"), false);
  }

  @Test
  public void sectionTooLong(){
    assertEquals(Project1.validTime("000:60"), false);
  }

  @Test
  public void notEnoughHoursInTheDay(){
    assertEquals(Project1.validTime("25:01"), false);
  }

  @Test
  public void tooManyMinutes(){
    assertEquals(Project1.validTime("00:65"), false);
  }

  @Test
  public void validTimeTest1(){
    assertEquals(Project1.validTime("24:59"), true);
  }

  @Test
  public void validTimeTest2(){
    assertEquals(Project1.validTime("00:00"), true);
  }

  @Test
  public void validTimeTest3(){
    assertEquals(Project1.validTime("0:00"), true);
  }

  @Test
  public void printOutReadme(){
    assertThat(Project1.printREADME(), is(1));
  }

  @Test
  public void noArguments(){
    // no arguments
    String[] args = {};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), -1);
  }

  @Test
  public void oneArgumentNoReadme(){
    // one argument that isn't "-README"
    String[] args = {"Hi"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), -1);
  }

  @Test
  public void oneArgumentYesReadme(){
    // one argument that IS "-README"
    String[] args = {"-README"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
  }

  @Test
  public void twoArgumentNoReadme(){
    // two arguments that both are not "-README"
    // these strings don't make sense in the context of this project, but the error handling is taken care
    // of somewhere else
    String[] args = {"hi", "everybody"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 0);
  }

  @Test
  public void twoArgumentYesReadme0(){
    // two arguments where index 0 is "-README"
    String[] args = {"-README", "everybody"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
  }

  @Test
  public void twoArgumentYesReadme1(){
    // two arguments where index 1 is "-README"
    String[] args = {"hi", "-README"};
    int argCount = args.length;
    assertEquals(Project1.parseOptions(argCount, args), 1);
  }
}
