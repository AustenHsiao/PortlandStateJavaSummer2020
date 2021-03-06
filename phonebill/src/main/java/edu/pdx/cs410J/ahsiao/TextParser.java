package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.ArrayList;


/** The TextParser class handles parsing a file, reading the header and subsequent lines,
 * and forming a PhoneBill from the information.
 */
public class TextParser implements edu.pdx.cs410J.PhoneBillParser<PhoneBill>{

    private ArrayList<String> fileRead; // this is our buffer to which we populate from file
    private String name; // the name extracted from the command line
    private PhoneBill phonebill;

    /**
     * The constructor requires a name in the form of a string. This name is supplied on the command
     * line.
     * @param nameFromCmdLine name as reported from the command line (not in the text file)
     */
    TextParser(String nameFromCmdLine){
        name = nameFromCmdLine;
        fileRead = new ArrayList<String>();
        phonebill = null;
    }

    /**
     * Takes in a fileName which is formatted in the same way that TextDumper writes text files. This
     * method will read the file into a class field ArrayList (fileRead). By parsing the first line captured in fileRead,
     * the name supplied on the command line is matched against the name within the file. Then we parse the populated array list.
     * This method ensures that the private methods of this class are run in the correct order. This function also
     * error handles the various exceptions that may arise.
     * @param fileName name of text file to read from
     * @return
     */
    public PhoneBill read(String fileName){
        try {
            String extractedName;
            populateArrayListFromFile(fileName);
            extractedName = getNameFromFile();

            if(!extractedName.equals(name)){
                // if the name given on the command line is different from the one in the file, throws parserException.
                // This case will be handled in main.
                throw new ParserException("Names don't match");
            }
            phonebill = parse();

        }catch(FileNotFoundException e){
            // If we didnt find the file, it still makes a new phonebill.
            phonebill = new PhoneBill(name);
        }catch(IOException e){
            // and IO exception is not good, so we return a null phonebill
            // this is handled gracefully in main
            phonebill = null;
        }catch(ParserException e){
            // If the file format is not right, we return a null phonebill-- either if the name in the file does not match the
            // command line argument, or the file is not formatted correctly.
            phonebill = null;
        }finally{
            return phonebill;
        }
    }

    /**
     * Given a filename, populateArrayListFromFile() will read the file, populating the class field array list, "fileRead"
     * with the lines from the file. Errors in reading cause IO exceptions. If the file is not found, we throw a file not found
     * exception. This is a private method because it must be run in a specific order to work properly.
     * @param fileName name of text file
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void populateArrayListFromFile(String fileName) throws FileNotFoundException, IOException{
        String line;
        // Open the file and dump everything into the ArrayList, "fileRead"
        BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
        while( (line = inputStream.readLine()) != null){
            // read each line in the file and add it to the arraylist
            fileRead.add(line);
        }
        inputStream.close();
    }

    /**
     * getNameFromFile() takes no arguments and returns the customer's name from the text file based on the
     * class field array list-- this means that populateArrayListFromFile should be run first-- this is a private method.
     * If the file is empty, a parser exception is thrown. If the first line doesn't
     * have the correct formatting, then we also throw a parser exception.
     * @return customer name from the text file
     */
    private String getNameFromFile() throws ParserException{
        if(fileRead.size() == 0){
            throw new ParserException("File is empty.");
        }
        String extractedName = "";
        // To get the name out of the file:
        // split the first line in order to pattern match the format of the dumped text
        String[] firstLine = fileRead.get(0).split(" ");
        if("BILL".equals(firstLine[0]) && "FOR:".equals(firstLine[1])){
            // since all of the dump files have the first line as "BILL FOR: ...", everything
            // after this is part of the name. Or at least, it should be.
            for(int i = 2; i < firstLine.length; ++i){
                extractedName += firstLine[i];
            }
        }else{
            throw new ParserException("File format invalid.");
        }
        return extractedName;
    }

    /**
     * If we run this function, that means that the name matches the name from the file.
     * This function parses the text file line by line, adding phone calls as necessary.
     * Returns a PhoneBill.
     * @return PhoneBill
     * @throws ParserException
     */
    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill tempPhoneBill = new PhoneBill(name);
        for(int lineIndex = 1; lineIndex < fileRead.size(); ++lineIndex){
            // for each line after the 0th, phone calls should be formatted as:
            //  "Phone call from 503-111-1111 to 503-222-2222 from 01/11/2020 13:00 to 1/11/2020 13:09"
            // So we only care about index
            // 3, 5 - phone numbers
            // 7, 10 - dates
            // 8, 11 - times

            // DISREGARD ABOVE FOR PROJECT 3:
            // for p3, format = "Phone call from 111-111-1111 to 222-222-2222 from 1/5/20, 3:00 PM to 1/5/20, 4:15 PM"
            // 3, 5 - phone numbers
            // 7, 11 - dates
            // 8, 12 - times
            // 9, 13 - AM/PM denotation

            // Since there are parsing functions for numbers, times, and dates statically declared in
            // the project1 class, I'm going to reuse those.
            String[] currentLine = fileRead.get(lineIndex).split(" ");
            if(currentLine.length != 14){
                throw new ParserException("currentLine read invalid");
            }
            String caller = currentLine[3];
            String callee = currentLine[5];
            String startDate = Project3.TwoDigitDate(currentLine[7].split(",")[0]);
            String endDate = Project3.TwoDigitDate(currentLine[11].split(",")[0]);
            String startTime = currentLine[8];
            String endTime = currentLine[12];
            String startTimeAMPM = currentLine[9];
            String endTimeAMPM = currentLine[13];

            PhoneCall tempPhoneCall = new PhoneCall(caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
            tempPhoneBill.addPhoneCall(tempPhoneCall);
        }
        return tempPhoneBill;
    }
}
