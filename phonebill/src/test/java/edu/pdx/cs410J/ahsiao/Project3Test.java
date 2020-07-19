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


}
