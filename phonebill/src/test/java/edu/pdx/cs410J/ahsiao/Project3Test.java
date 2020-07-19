package edu.pdx.cs410J.ahsiao;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Project3Test {

    @Test
    public void TWODIGITDATE_TwoDigitEverything(){
        assertEquals(Project3.TwoDigitDate("11/11/2020"), "11/11/2020");
    }

    @Test
    public void TWODIGITDATE_OneDigitMonth(){
        assertEquals(Project3.TwoDigitDate("1/11/2020"), "01/11/2020");
    }

    @Test
    public void TWODIGITDATE_OneDigitDay(){
        assertEquals(Project3.TwoDigitDate("11/1/2020"), "11/01/2020");
    }

    @Test
    public void TWODIGITDATE_OneDigitEverything(){
        assertEquals(Project3.TwoDigitDate("1/1/2020"), "01/01/2020");
    }

    @Test
    public void validTimeTest(){
        assertEquals(Project3.validTime("00:40"), true);
        assertEquals(Project3.validTime("12:59"), true);
        assertEquals(Project3.validTime("1:00"), true);
        assertEquals(Project3.validTime("01:00"), true);
        assertEquals(Project3.validTime("00:4"), false);
        assertEquals(Project3.validTime("000:40"), false);
        assertEquals(Project3.validTime("13:00"), false);
        assertEquals(Project3.validTime("0:60"), false);
        assertEquals(Project3.validTime("0:40"), true);
        assertEquals(Project3.validTime("a:20"), false);
        assertEquals(Project3.validTime("0:2a"), false);
    }
}
