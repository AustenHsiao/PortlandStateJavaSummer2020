package edu.pdx.cs410J.ahsiao;
import static java.lang.Integer.parseInt;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  /**
   * Tests if a string is a valid phone number in the form xxx-xxx-xxxx
   * where x is [0-9]. Length of string should be 12 (10 digits and 2 hyphens).
   * This function splits up the string by hyphens and tests if each section is comprised of
   * only digits. Returns true if the parameter is a valid phone number.
   * @param phonenum
   */
  public static boolean validPhoneNumber(String phonenum){
    if(phonenum.length() != 12){return false;}
    String[] splitNumber = phonenum.split("-");
    if(splitNumber.length != 3){return false;}
    for(String numberSections: splitNumber){
      if(!numberSections.matches("[0-9]+")){
        return false;
      }
    }
    return true;
  }

  /**
   * Takes in a string and returns a boolean value representing whether or not it can be a valid date with
   * the form mm/dd/yyyy. Months and dates with leading 0s are accepted. Leap years not accounted for.
   * @param date
   * @return
   */
  public static boolean validDate(String date){
    // At a minimum, the date can be represented as x/x/xxxx, which is 8 chars
    // On the flip side, the maximum is 10 chars
    if(date.length() < 8 || date.length() > 10){return false;}
    String[] splitDate = date.split("/");

    // If we don't end up with 3 portions, we weren't given a valid date for sure
    if(splitDate.length != 3){return false;}

    // After splitting, check each section, at a maximum, the string should be 4 characters in length
    // and contains only numbers
    for(String dateSections: splitDate) {
      if (!dateSections.matches("[0-9]+")) {
        return false;
      }
    }
    if(splitDate[0].length() > 2){return false;}
    if(splitDate[1].length() > 2){return false;}
    if(splitDate[2].length() != 4){return false;}

    int month = parseInt(splitDate[0]);
    int day = parseInt(splitDate[1]);
    int year = parseInt(splitDate[2]);

    if(month < 1 || month > 12){return false;}
    if(day < 1 || day > 31){return false;}
    // Arbitrarily assign 2000 to be the earliest acceptable year, in the case of backdating or old logs
    if(year < 2000){return false;}

    // All the months that CANNOT have 31 days.
    if((month == 2 ||
       month == 4 ||
       month == 6 ||
       month == 9 ||
       month == 11) && day == 31){return false;}

    return true;
  }

  /**
   * Takes in a string and verifies that the form fits the 24-hour clock format.
   * Returns boolean value.
   * @param time
   * @return
   */
  public static boolean validTime(String time){
    if(time.length() < 4 || time.length() > 5){return false;}
    String[] splitTime = time.split(":");
    if(splitTime.length != 2){return false;}
    for(String timeSections: splitTime){
      if(!timeSections.matches("[0-9]+") || timeSections.length() > 2){
        return false;
      }
    }
    int hour = parseInt(splitTime[0]);
    int minute = parseInt(splitTime[1]);

    if(hour > 24 || hour < 0){return false;}
    if(minute > 59 || minute < 0){return false;}

    return true;
  }

  public static void main(String[] args) {
    if(args.length < 7){
      System.err.println("Missing command line arguments");
      System.exit(1);
    } else if(args.length > 7){
      System.err.println("Too many command line arguments");
      System.exit(1);
    }

    // Check phone numbers
    if(validPhoneNumber(args[1]) == false || validPhoneNumber(args[2]) == false){
      System.err.println("Invalid phone number. Phone number must be in the form xxx-xxx-xxxx where x is a digit between 0-9");
      System.exit(1);
    }

    // Check dates
    if(validDate(args[3]) == false || validDate(args[5]) == false){
      System.out.println("Invalid date. Date must be in the form mm/dd/yyyy");
      System.exit(1);
    }

    // Check times
    if(validTime(args[4]) == false || validTime(args[6]) == false){
      System.out.println("Invalid time. Time must be in the 24-hour format");
      System.exit(1);
    }

    // Create a new PhoneBill for the specified person and add in the new PhoneCall
    PhoneBill bill = new PhoneBill(args[0], new PhoneCall(args[0], args[1], args[2], args[3], args[4], args[5], args[6]));

    // There's only going to be one PhoneCall in project1, but this loop would iterate over all PhoneCalls the user has in their log
    for(Object phoneRecords: bill.getPhoneCalls()){
      System.out.println(phoneRecords);
    }
    System.exit(0);
  }

}