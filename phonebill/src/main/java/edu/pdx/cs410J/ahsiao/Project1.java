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
    if(splitNumber[0].length() != 3 || splitNumber[1].length() != 3){
      return false;
    }
    if(splitNumber[2].length() != 4){
      return false;
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
    // Check lengths, for the first two sections, we can have either a single digit or two digits
    if(splitDate[0].length() > 2){return false;}
    if(splitDate[1].length() > 2){return false;}
    if(splitDate[2].length() != 4){return false;}

    int month = parseInt(splitDate[0]);
    int day = parseInt(splitDate[1]);
    int year = parseInt(splitDate[2]);

    if(month < 1 || month > 12){return false;}
    if(day < 1 || day > 31){return false;}
    // Arbitrarily assign 2000 to be the earliest acceptable year. The upper limit is constrained to 4 digits
    if(year < 2000){return false;}

    // All the months that CANNOT have 31 days.
    if((month == 2 ||
       month == 4 ||
       month == 6 ||
       month == 9 ||
       month == 11) && day == 31){return false;}

    // If it passed through all the filters, it might be true
    return true;
  }

  /**
   * Takes in a string and verifies that the form fits the 24-hour clock format.
   * Returns boolean value. A valid time can exist between 4 and 5 characters (1:01 vs 01:01).
   * Much like the phone number parser, this function splits the time by a colon. To ensure that
   * there even was a colon to begin with, the length of the newly split string should be 2.
   * Each sections should contain only numbers and have a maximum of 2 digits. Within each section
   * the numbers should also makes sense-- eg: the hours in a day are bounded by [0,24] and minutes [0,60)
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

  /**
   * Hardcoded readme for this program
   */
  public static int printREADME(){
    System.out.println("\n\n Written by Austen Hsiao for CS510, assignment 1.\n" +
            " This program creates a new phone bill for the given user. " +
            "\n The phone call information is added to the user's bill.\n" +
            " If the user specifies -print, the phonecall will be printed.\n");
    System.out.print(" usage: java edu.pdx.cs410J.ahsiao.Project1 [options] <args>\n  args are (in this order):\n" +
            "    \tcustomer\t\tPerson whose phone bill we're modeling\n" +
            "    \tcallerNumber\t\tPhone number of caller\n" +
            "    \tcalleeNumber\t\tPhone number of person who was called\n" +
            "    \tstart\t\t\tDate and time call began (24-hour time)\n" +
            "    \tend\t\t\tDate and time call ended (24-hour time)\n" +
            "  options are (options may appear in any order):\n" +
            "    \t-print\t\t\tPrints a description of the new phone call\n" +
            "    \t-README\t\t\tPrints a README for this project and exists\n" +
            "  Date and time should be in the format: mm/dd/yyyy hh:mm");
    return 1;
  }

  /**
   *  parseOptions takes in the number of arguments in the command line array, as well as
   *  the command line array itself. By writing the options parsing as a separate function than
   *  main, I could develop tests for it.
   * @param argCount
   * @param args
   * @return
   */
  public static int parseOptions(int argCount, String[] args){
    // No arguments will return -1 for error
    if(argCount == 0){
      return -1;
    }
    // If 1 argument, if it's "-README", then we return 1.
    // If we only have 1 argument that isn't "-README", we return -1 because it makes no sense--
    // we're missing arguments.
    if(argCount == 1 && "-README".equals(args[0])){
      return 1;
    }
    else if(argCount == 1 && !("-README".equals(args[0]))){
      return -1;
    }
    // If more than 1 argument, we need to check index 0 or 1 if it's "-README". In such a case,
    // we return 1 to tell main to print out the readme
    if("-README".equals(args[0]) || "-README".equals(args[1])){
      return 1;
    }
    // If we get down here, that means that we have at least two command line arguments,
    // and neither of the first two are "-README". So we return 0.
    return 0;
  }

  /*
  After parsing the command line arguments, I have no check to ensure the end time and date is truly
  after the start time and date. Eg. the start date could be in the year 2050 while the end year is 2020.
  */
  public static void main(String[] args) {
    int printYes = 0;
    int argCount = args.length;

    switch(parseOptions(argCount, args)){
      case -1:
        System.err.println("Missing command line arguments");
        System.exit(1);
      case 1:
        Project1.printREADME();
        System.exit(0);
      case 0:
    }

    /* After calling parseOptions, if the exit cases aren't triggered, this means that we have at least 2 arguments that are
    not -README. So if either of the first two arguments are -print, we set printYes to 1. This is a flag that tells us to
    print out the phone call at the end of this method. It also serves as a shift-- I accessed the command line arguments
    by index, so if the -print flag is set, we need to increment all the accessing indices by 1.*/
    if("-print".equals(args[0]) || "-print".equals(args[1])){
      printYes = 1;
    }

    // minimum number of arguments is 7.
    // (name, number1, number2, time1, date1, time2, date2) require for PhoneCall
    if(argCount < 7){
      System.err.println("Missing command line arguments");
      System.exit(1);
    } else if(argCount > 8){ // maximum: -print (name, number1, number2, time1, date1, time2, date2) is 8 things
      System.err.println("Too many command line arguments");
      System.exit(1);
    }

    // Check phone numbers
    if(validPhoneNumber(args[1+printYes]) == false || validPhoneNumber(args[2+printYes]) == false){
      System.err.println("Invalid phone number. Phone number must be in the form xxx-xxx-xxxx where x is a digit between 0-9");
      System.exit(1);
    }

    // Check dates
    if(validDate(args[3+printYes]) == false || validDate(args[5+printYes]) == false){
      System.out.println("Invalid date. Date must be in the form mm/dd/yyyy");
      System.exit(1);
    }

    // Check times
    if(validTime(args[4+printYes]) == false || validTime(args[6+printYes]) == false){
      System.out.println("Invalid time. Time must be in the 24-hour format");
      System.exit(1);
    }

    // Create a new PhoneBill for the specified person and add in the new PhoneCall
    PhoneBill bill = new PhoneBill(args[0+printYes], new PhoneCall(args[0+printYes],
            args[1+printYes],
            args[2+printYes],
            args[3+printYes],
            args[4+printYes],
            args[5+printYes],
            args[6+printYes]));

    // There's only going to be one PhoneCall in project1, but this loop would iterate over all PhoneCalls the user has in their log
    if(printYes == 1){
      for (Object phoneRecords : bill.getPhoneCalls()) {
        System.out.println(phoneRecords);
      }
    }
    System.exit(0);
  }
}