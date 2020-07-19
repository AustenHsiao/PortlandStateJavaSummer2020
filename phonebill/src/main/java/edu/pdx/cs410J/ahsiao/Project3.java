package edu.pdx.cs410J.ahsiao;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Project3 {

    /**
     * Prints out the readme associated with project 3
     */
    public static void printReadme(){
        try (InputStream readme = Project3.class.getResourceAsStream("README_p3.txt");){
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
     * Returns a bool as to whether or not a string is a valid time in a 12-hour format.
     * 1:59 and 01:59 are both valid times.
     * @param time
     * @return boolean
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

        if(hour > 12 || hour < 0){return false;}
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

    public static void main(String[] args){
        int prettyFileIndex = 0;
        int pretty = 0;
        int textFileIndex = 0;
        int file = 0;
        int print = 0;

        // Parse args
        if(args.length == 0){
            System.err.println("Missing command line arguments");
            System.exit(1);
        }else if(args.length < 7){
            // maximum options are: -pretty file -textFile file -print -README
            for(String word: args){
                if(word.equals("-README")){
                    printReadme();
                    System.exit(-1);
                }
            }
            System.err.println("Missing command line arguments");
            System.exit(2);
        }else if(args.length > 6 && args.length < 15){
            for(int i = 0; i < 7; ++i){
                if(args[i].equals("-pretty")){
                    prettyFileIndex = i + 1;
                    pretty = 2;
                }else if(args[i].equals("-textFile")){
                    textFileIndex = i + 1;
                    file = 2;
                }else if(args[i].equals("-print")){
                    print = 1;
                }else if(args[i].equals("-README")){
                    printReadme();
                    System.exit(3);
                }else if(args[i].charAt(0) == '-' && args[i].length() != 1){
                    System.err.println("Unrecognized option(s)");
                    System.exit(4);
                }
            }
        }else{
            System.err.println("Too many command line arguments");
            System.exit(5);
        }

        int start = pretty + file + print;
        if(args.length - start > 9){
            System.err.println("Too many arguments");
            System.exit(6);
        }else if(args.length - start < 9){
            System.err.println("Missing arguments");
            System.exit(7);
        }

        String customer = args[start];
        String callerNumber = args[1 + start];
        String calleeNumber = args[2 + start];
        String startDate = TwoDigitDate(args[3 + start]);
        String startTime = args[4 + start];
        String startAMPM = args[5 + start];
        String endDate = TwoDigitDate(args[6 + start]);
        String endTime = args[7 + start];
        String endAMPM = args[8 + start];
        String prettyFileName = args[prettyFileIndex];
        String textFileName = args[textFileIndex];

        if(!startAMPM.equalsIgnoreCase("AM") && !startAMPM.equalsIgnoreCase("PM")){
            System.err.println("Incorrect start time. Time should be designated with <am/pm>");
            System.exit(8);
        }else if(!endAMPM.equalsIgnoreCase("AM") && !endAMPM.equalsIgnoreCase("PM")){
            System.err.println("Incorrect end time. Time should be designated with <am/pm>");
            System.exit(9);
        }else if(!Project1.validPhoneNumber(callerNumber) || !Project1.validPhoneNumber(calleeNumber)){
            System.err.println("Invalid number(s)");
            System.exit(10);
        }else if(!Project1.validDate(startDate) || !Project1.validDate(endDate)){
            System.err.println("Invalid date(s)");
            System.exit(11);
        }else if(!Project3.validTime(startTime) || !Project3.validTime(endTime)){
            System.err.println("Invalid time(s)");
            System.exit(12);
        }

        PhoneCall userCall = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        if(userCall.getEndTime().before(userCall.getStartTime())){
            System.err.println("End time occurs before start time");
            System.exit(13);
        }

        PhoneBill userBill;

        if(file != 0){
            TextParser tp = new TextParser(customer);
            if( (userBill = tp.read(textFileName)) == null ){
                System.err.println("Name in file does not match command line argument and/or malformed text file.");
                System.exit(14);
            }
            userBill.addPhoneCall(userCall);
            TextDumper.write(textFileName, userBill);
        }else{
            userBill = new PhoneBill(customer, userCall);
        }

        if(pretty != 0){
            PrettyPrinter.writeToFile(prettyFileName, userBill);
        }

        if(print != 0){
            System.out.println(userCall.toString());
        }
        System.exit(15);
    }

}
