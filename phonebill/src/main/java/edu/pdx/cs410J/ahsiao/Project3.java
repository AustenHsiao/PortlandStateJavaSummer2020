package edu.pdx.cs410J.ahsiao;

public class Project3 {

    /**
     * The passed in string is assumed to be valid in the form (mm/dd/yyyy) where
     * leading 0s are optional-- it is acceptable to have (m/d/yyyy). This function
     * returns the form (mm/dd/yyyy).
     * @param date
     * @return
     */
    private static String TwoDigitDate(String date){
        if(date.length() == 10){
            return date;
        }
        String MM = "";
        String dd = "";
        String[] splitDate = date.split("/");
        if(splitDate[0].length() == 1){
            MM = "0";
        }
        if(splitDate[1].length() == 1){
            dd = "0";
        }
        return MM + splitDate[0] + '/' + dd + splitDate[1] + '/' + splitDate[2];
    }

}
