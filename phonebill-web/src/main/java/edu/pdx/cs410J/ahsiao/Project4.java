package edu.pdx.cs410J.ahsiao;
import edu.pdx.cs410J.web.HttpRequestHelper;
import java.io.*;
import java.util.Collection;

import static java.lang.Integer.parseInt;



/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static void printReadme(){
        try (InputStream readme = Project4.class.getResourceAsStream("README_p4.txt");){
            String line;
            BufferedReader read = new BufferedReader(new InputStreamReader(readme));
            while( (line = read.readLine()) != null){
                System.out.println(line);
            }
        }catch(FileNotFoundException e){
            System.err.println("Cannot find readme file.");
        }catch(IOException e){
            System.err.println("Cannot read readme file.");
        }
    }

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
        if(splitTime[1].length() != 2){return false;}
        int hour = parseInt(splitTime[0]);
        int minute = parseInt(splitTime[1]);

        if(hour > 24 || hour < 0){return false;}
        if(minute > 59 || minute < 0){return false;}

        return true;
    }

    /**
     * The passed in string is assumed to be valid in the form (mm/dd/yyyy) where
     * leading 0s are optional-- it is acceptable to have (m/d/yyyy). This function
     * returns the form (mm/dd/yyyy). If the year is expressed with 2 digits (MM/dd/yy),
     * append a 20 in front of the year.
     * @param date
     * @return
     */
    public static String TwoDigitDate(String date){
        if(date.length() == 10){return date;}
        String MM = "";
        String dd = "";
        String yyyy = "";
        String[] splitDate = date.split("/");
        if(splitDate[0].length() == 1){MM = "0";}
        if(splitDate[1].length() == 1){dd = "0";}
        if(splitDate[2].length() == 2){yyyy = "20";}
        return MM + splitDate[0] + '/' + dd + splitDate[1] + '/' + yyyy + splitDate[2];
    }

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        PhoneBillRestClient client = null;
        int host = 0;
        String hostname = "";
        int port = -1;
        int search = 0;
        int print = 0;
        int argStart = 0;

        if(args.length == 0){
            System.err.println("Missing command line arguments");
            System.exit(1);
        }else{
            for(int i = 0; i < args.length; ++i){
                if(args[i].equals("-host")){
                    host = 1;
                    hostname = args[i+1];
                    argStart += 2;
                }else if(args[i].equals("-port")){
                    try {
                        port = parseInt(args[i + 1]);
                        argStart += 2;
                        if(port < 0 || port > 65535){
                            throw new NumberFormatException("Out of range");
                        }
                    }catch(NumberFormatException e){
                        System.err.println("Port specified must be an int between [0, 65535]");
                        System.exit(2);
                    }
                }else if(args[i].equals("-search")){
                    search = 1;
                    argStart++;
                }else if(args[i].equals("-print")){
                    print = 1;
                    argStart++;
                }else if(args[i].equals("-README")){
                    printReadme();
                    System.exit(3);
                }else if(args[i].charAt(0) == '-'){
                    System.err.println("Option not recognized");
                    System.exit(4);
                }
            }
        }

        if (host == 1 && port != -1) {
            client = new PhoneBillRestClient(hostname, port);
        }else if(host == 1 && port == -1){
            System.err.println("Port number not specified");
            System.exit(14);
        }else if(host != 1 && port != -1){
            System.err.println("Hostname not specified");
            System.exit(15);
        }

        if(host == 1 && port != -1 && search == 0){
            int argumentCounter = 0;

            if(argStart+9 < args.length){
                System.err.println("Too many arguments");
                System.exit(19);
            }
            try {
                String customer = args[argStart]; argumentCounter++;
                String callerNumber = args[argStart + 1]; argumentCounter++;
                String calleeNumber = args[argStart + 2]; argumentCounter++;
                String startDate = args[argStart + 3]; argumentCounter++;
                String startTime = args[argStart + 4]; argumentCounter++;
                String startAM_PM = args[argStart + 5]; argumentCounter++;
                String endDate = args[argStart + 6]; argumentCounter++;
                String endTime = args[argStart + 7]; argumentCounter++;
                String endAM_PM = args[argStart + 8]; argumentCounter++;

                if (!validPhoneNumber(callerNumber) || !validPhoneNumber(calleeNumber)) {
                    System.err.println("Invalid caller or callee number");
                    System.exit(16);
                } else if (!validDate(startDate) || !validDate(endDate)) {
                    System.err.println("Invalid start or end date");
                    System.exit(20);
                } else if (!validTime(startTime) || !validTime(endTime)) {
                    System.err.println("Invalid start or end time");
                    System.exit(21);
                } else if (!startAM_PM.equalsIgnoreCase("AM") && !startAM_PM.equalsIgnoreCase("PM")) {
                    System.err.println("start time am/pm not specified");
                    System.exit(22);
                } else if (!endAM_PM.equalsIgnoreCase("AM") && !endAM_PM.equalsIgnoreCase("PM")) {
                    System.err.println("end time am/pm not specified");
                    System.exit(23);
                }

                PhoneCall tempCall = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, startAM_PM, endDate, endTime, endAM_PM);

                if(tempCall.getStartTime().after(tempCall.getEndTime())){
                    System.err.println("End time occurs before start time");
                    System.exit(999);
                }

                try {
                    String definition = callerNumber + " " + calleeNumber + " " + startDate + " " + startTime + " " + startAM_PM + " " + endDate + " " + endTime + " " + endAM_PM;
                    client.addDictionaryEntry(customer, definition);
                }catch(IOException e){
                    System.err.println("IO Except caught");
                    System.exit(5000);
                }

                if(print == 1){
                    PrettyPrinter.writeOut(tempCall);
                }
            }catch(ArrayIndexOutOfBoundsException e){
                if(argumentCounter == 1){
                    try {
                        PhoneBill temp = client.getDefinition(args[argStart]);
                        Collection<PhoneCall> tempPhoneCalls = temp.getPhoneCalls();
                        for(PhoneCall call: tempPhoneCalls){
                            PrettyPrinter.writeOut(call);
                        }
                    }catch(IOException ex){
                        System.err.println("Cannot connect to server");
                        System.exit(8000);
                    }catch(PhoneBillRestClient.PhoneBillRestException exc){
                        // not found
                        System.out.println("No calls for this customer on file");
                        System.exit(9000);
                    }
                }
                System.err.println("Missing command line arguments");
                System.exit(18);
            }
        }

        if(search == 1 && host == 1 && port != -1){
            // if search is specified, we don't care about -print

            if(argStart+7 < args.length){
                System.err.println("Too many arguments for search function");
                System.exit(10);
            }
            try {
                String customer = args[argStart];
                String startDate = args[argStart + 1];
                String startTime = args[argStart + 2];
                String startAM_PM = args[argStart + 3];
                String endDate = args[argStart + 4];
                String endTime = args[argStart + 5];
                String endAM_PM = args[argStart + 6];

                if (!validDate(startDate) || !validDate(endDate)) {
                    System.err.println("Invalid start or end date");
                    System.exit(5);
                } else if (!validTime(startTime) || !validTime(endTime)) {
                    System.err.println("Invalid start or end time");
                    System.exit(6);
                } else if (!startAM_PM.equalsIgnoreCase("AM") && !startAM_PM.equalsIgnoreCase("PM")) {
                    System.err.println("start time am/pm not specified");
                    System.exit(7);
                } else if (!endAM_PM.equalsIgnoreCase("AM") && !endAM_PM.equalsIgnoreCase("PM")) {
                    System.err.println("end time am/pm not specified");
                    System.exit(8);
                }
                PhoneCall temp = new PhoneCall("111-111-1111", "222-222-2222", startDate, startTime, startAM_PM, endDate, endTime, endAM_PM);

                if(temp.getEndTime().before(temp.getStartTime())){
                    System.err.println("End time is before start time");
                    System.exit(11);
                }
                try {
                    if(client.getAllDictionaryEntries().containsKey(customer)){
                        PhoneBill customerBill = client.getAllDictionaryEntries().get(customer);
                        if(PrettyPrinter.writeOut(customerBill, temp.getStartTime(), temp.getEndTime()) == 0){
                            System.out.println("No calls to display between the given time interval.");
                        }
                    }
                    System.exit(13); // if -search, do nothing else
                }catch (IOException e){
                    System.err.println("IO Except");
                    System.exit(12);
                }
            }catch(ArrayIndexOutOfBoundsException e){
                System.err.println("Missing arguments for search function");
                System.exit(9);
            }
        }
        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }
}