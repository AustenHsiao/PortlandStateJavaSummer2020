package edu.pdx.cs410J.ahsiao;

import java.io.*;

public class Project2 {

    /**
     * Takes in the command line arguments to look for any option flags.
     * Since the maximum length of the options is 4 (-textFile file, -print, -README),
     * it parses the arguments if the length is 0 - (>=4). The minimum number
     * of arguments we can have is 7, maximum = 11
     *
     * @param args command line arguments
     * @return int, -2-> Too many arguments
     *              -1-> Not enough arguments
     *               0-> -README is found
     *               1-> Could be valid
     */
    private static int switchOption(String[] args){
        int length = args.length;

        if(length == 0){
            return -1;
        }else if(length <= 4){
            for(int i = 0; i < length; ++i){
                if("-README".equals(args[i])){return 0;}
            }
        }else if(length > 11){
            return -2;
        }
        return 1;
    }

    public static void main(String[] args) {
        switch(switchOption(args)){
            case -2:
                System.err.println("Too many command line arguments");
                System.exit(-2);
            case -1:
                System.err.println("Missing command line arguments");
                System.exit(-1);
            case 0:
                try (InputStream readme = Project1.class.getResourceAsStream("README.txt");){
                    String line;
                    BufferedReader read = new BufferedReader(new InputStreamReader(readme));
                    while( (line = read.readLine()) != null){
                            System.out.println(line);
                    }
                }catch(FileNotFoundException e){
                    System.err.println("Cannot find readme file.");
                }catch(IOException e){
                    System.err.println("Cannot read readme file.");
                }finally {
                    System.exit(0);
                }
            case 1:
        }

        // Getting to this point means that we have more than 4 arguments and less than 12.
        // The user is required to include phone call data, so we need to have at least 7 arguments.
        // We can have between 0 and 3 arguments for options (-README is already taken care of):
        //  -textFile file <- two arguments
        //  -print <- one argument
        if(args.length < 7){
            System.err.println("Missing command line arguments");
            System.exit(-1);
        }

        // Scan the first 3 arguments looking for -textFile and/or -print flags.
        int printSpecified = 0;
        int fileSpecified = 0;
        int fileNameIndex = -1;

        for(int i = 0; i < 3; ++i){
            if("-textFile".equals(args[i])){
                fileNameIndex = (i+1);
                fileSpecified = 2;
            }else if("-print".equals(args[i])){printSpecified = 1;}
        }

        /*
            We can use the printSpecified and fileSpecified flags as shifts to read correct command line arguments:
                I'm writing it out here for my own reference.
                name = args[0 + fileSpecified + printSpecified]
                numberFrom = args[1 + fileSpecified + printSpecified]
                numberTo = args[2 + fileSpecified + printSpecified]
                startDate = args[3 + fileSpecified + printSpecified]
                startTime = args[4 + fileSpecified + printSpecified]
                endDate = args[5 + fileSpecified + printSpecified]
                endTime = args[6 + fileSpecified + printSpecified]
         */

        // Check phone numbers-- copied over from Project1
        if(!Project1.validPhoneNumber(args[1 + fileSpecified + printSpecified]) || !Project1.validPhoneNumber(args[2 + fileSpecified + printSpecified])){
            System.err.println("Invalid phone number. Phone number must be in the form xxx-xxx-xxxx where x is a digit between 0-9");
            System.exit(3);
        }

        // Check dates
        if(!Project1.validDate(args[3 + fileSpecified + printSpecified]) || !Project1.validDate(args[5 + fileSpecified + printSpecified])){
            System.err.println("Invalid date. Date must be in the form mm/dd/yyyy");
            System.exit(4);
        }

        // Check times
        if(!Project1.validTime(args[4 + fileSpecified + printSpecified]) || !Project1.validTime(args[6 + fileSpecified + printSpecified])){
            System.err.println("Invalid time. Time must be in the 24-hour format (xx:xx)");
            System.exit(5);
        }

        PhoneBill userPhoneBill;
        PhoneCall newCall = new PhoneCall(
                args[1 + fileSpecified + printSpecified],
                args[2 + fileSpecified + printSpecified],
                args[3 + fileSpecified + printSpecified],
                args[4 + fileSpecified + printSpecified],
                args[5 + fileSpecified + printSpecified],
                args[6 + fileSpecified + printSpecified]);

        if(fileSpecified != 0){
            // If we're going to read/write the phone bill:
            //  Try to read in the file specified on the command line
            //  Populate the bill with the supplied phone call
            //  write to file (<userName>.txt).
            TextParser readIn = new TextParser(args[fileSpecified + printSpecified]);
            if( (userPhoneBill = readIn.read(args[fileNameIndex])) == null ){
                System.err.println("Name given on command line is different than the one found in the text file.");
                System.exit(-3);
            }

            userPhoneBill.addPhoneCall(newCall);
            TextDumper.write(userPhoneBill);
        }else{
            userPhoneBill = new PhoneBill(args[fileSpecified + printSpecified], newCall);
        }

        if(printSpecified != 0){
            System.out.println(newCall.toString());
        }
        System.exit(0);
    }
}
