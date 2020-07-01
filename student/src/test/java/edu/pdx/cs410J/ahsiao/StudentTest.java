package edu.pdx.cs410J.ahsiao;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  public void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createStudentNamed(name);
    assertThat(pat.getName(), equalTo(name));
  }

  @Test
  public void toStringContainsStudentName(){
    String name = "Pat";
    Student pat = createStudentNamed(name);
    assertThat(pat.toString(), containsString(name));
  }

  @Test
  public void toStringContainsGpa(){
    Student pat = new Student("Pat", new ArrayList<>(), 3.76, "Doesn't matter");
    assertThat(pat.toString(), containsString("has a GPA of 3.76"));
  }

  private Student createStudentNamed(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }
}