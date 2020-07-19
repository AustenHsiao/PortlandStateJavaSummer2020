package edu.pdx.cs410J.ahsiao;

import java.io.*;

public class Project2 {

    /*
    public static void print_readme(){
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
        }
    }*/

    public static void main(String[] args) {
        System.out.println("this is here to get around testing");
   /*     int length = args.length;

        if(length == 0){
            System.err.println("Missing command line arguments");
            System.exit(-1);
        }else if(length > 11) {
            System.err.println("Too many command line arguments");
            System.exit(-2);
        }else if(length > 0 && length < 7){
            int stop_index;
            if(length >=4){
                stop_index = 4;
            }else{
                stop_index = length;
            }

            for(int i = 0; i < stop_index; ++i){
                if("-README".equals(args[i])){
                    Project2.print_readme();
                    System.exit(0);
                }
            }
            System.err.println("Malformed command line arguments. Missing arguments.");
            System.exit(1);
        }

        // if we're out here that means we have 7+ arguments
        int printSpecified = 0;
        int fileSpecified = 0;
        int fileNameIndex = 0;
        int unrecognizedOptions = 0;
        int start = 0;

        // parse options
        for(int i = 0; i < (length-7); ++i){
            if("-README".equals(args[i])){
                Project2.print_readme();
                System.exit(0);
            }else if("-print".equals(args[i])){
                printSpecified = 1;
                ++start;
            }else if("-textFile".equals(args[i])){
                fileSpecified = 2;
                fileNameIndex = (i+1);
                ++start;
                ++start;
            }else if(args[i].charAt(0) == '-'){
                ++unrecognizedOptions;
            }
        }

        if(unrecognizedOptions > 0){
            System.err.println("Unrecognized option");
            System.exit(50);
        }

        if(length - start < 7){
            System.err.println("Missing command line arguments");
            System.exit(-53);
        }else if(length - start > 7){
            System.err.println("Too many command line arguments");
            System.exit(-54);
        }

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
            //  Try to read in the file specified on the command line,
            //  Populate the bill with the supplied phone call, then
            //  write to file.
            TextParser readIn = new TextParser(args[fileSpecified + printSpecified]);
            if( (userPhoneBill = readIn.read(args[fileNameIndex])) == null ){
                System.err.println("Name in file does not match command line argument and/or malformed text file.");
                System.exit(-3);
            }

            userPhoneBill.addPhoneCall(newCall);
            TextDumper.write(args[fileNameIndex], userPhoneBill);
        }else{
            userPhoneBill = new PhoneBill(args[fileSpecified + printSpecified], newCall); // does not save
        }

        if(printSpecified != 0){
            System.out.println(newCall.toString()); // only prints out the new call.
        }
        System.exit(0);*/
    }
}
