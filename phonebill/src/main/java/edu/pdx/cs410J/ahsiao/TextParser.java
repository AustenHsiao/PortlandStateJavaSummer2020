package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.ArrayList;

public class TextParser implements edu.pdx.cs410J.PhoneBillParser<PhoneBill>{

    private ArrayList<String> fileRead; // this is our buffer to which we populate from file
    private String name; // the name extracted from the command line
    private PhoneBill phonebill;

    /**
     * The constructor requires a name in the form of a string. This name is supplied on the command
     * line.
     * @param nameFromCmdLine
     */
    TextParser(String nameFromCmdLine){
        name = nameFromCmdLine;
        fileRead = new ArrayList<String>();
        phonebill = null;
    }

    /**
     * Takes in a fileName which is formatted in the same way that TextDumper writes text files. This
     * method will read the file into a class field ArrayList (fileRead). By parsing the first line captured in fileRead,
     * the name supplied on the command line is matched against the name within the file.
     * @param fileName
     * @return
     */
    public PhoneBill read(String fileName){
        try {
            String extractedName;
            populateArrayListFromFile(fileName);
            extractedName = getNameFromFile();

            if(!extractedName.equals(name)){
                // if the name given on the command line is different from the one in the file, inform user and exit.
                System.err.println("Name given on command line is different that the one found in the text file. Exiting.");
                System.exit(-1);
            }
            phonebill = parse();

        }catch(FileNotFoundException e){
            phonebill = new PhoneBill(name);
        }catch(IOException e){
            // and IO exception is not good, so we exit from here
            System.err.println("Error reading file.");
            System.exit(-1);
        }catch(ParserException e){
            System.err.println("File incorrectly formatted. No phone call data imported");
            phonebill = new PhoneBill(name);
        }finally{
            return phonebill;
        }
    }

    /**
     * Given a filename, populateArrayListFromFile() will read the file, populating the class field array list, "fileRead"
     * with the lines from the file. Errors in reading cause IO exceptions. If the file is not found, we throw a file not found
     * exception.
     * @param fileName
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
    }

    /**
     * getNameFromFile() takes no arguments and returns the customer's name from the text file.
     * If the file is empty, a parser exception is thrown. If the first line doesn't
     * have the correct formatting, then we also throw a parser exception.
     * @return
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
     * @return
     * @throws ParserException
     */
    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill tempPhoneBill = new PhoneBill(name);
        for(int lineIndex = 1; lineIndex < fileRead.size(); ++lineIndex){
            // for each line after the 0th, phone calls should be formatted as:
            //  "Phone call from 503-111-1111 to 503-222-2222 from 13:00 01/11/2020 to 13:09 1/11/2020"
            // So we only care about index
            // 3, 5 - phone numbers
            // 7, 10 - times
            // 8, 11 - dates
            // Since there are parsing functions for numbers, times, and dates statically declared in
            // the project1 class, I'm going to reuse those.
            String[] currentLine = fileRead.get(lineIndex).split(" ");
            if(Project1.validPhoneNumber(currentLine[3]) == false || Project1.validPhoneNumber(currentLine[5]) == false ||
                      Project1.validTime(currentLine[7]) == false || Project1.validTime(currentLine[10]) == false ||
                      Project1.validDate(currentLine[8]) == false || Project1.validDate(currentLine[11]) == false){
                throw new ParserException("Invalid formatting.");
            }
            PhoneCall tempPhoneCall = new PhoneCall(currentLine[3], currentLine[5], currentLine[7], currentLine[8], currentLine[10], currentLine[11]);
            tempPhoneBill.addPhoneCall(tempPhoneCall);
        }
        return tempPhoneBill;
    }
}
