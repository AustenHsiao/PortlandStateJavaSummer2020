package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;

public class TextParser implements edu.pdx.cs410J.PhoneBillParser<PhoneBill>{

    private ArrayList<String> fileRead = new ArrayList<String>();

    public PhoneBill read(String fileName){
        try {
            String line;
            BufferedReader inputStream = new BufferedReader(new FileReader(fileName));

            while( (line = inputStream.readLine()) != null){
                fileRead.add(line);
            }
            return parse();

        }catch(FileNotFoundException e){
            return new PhoneBill("1");
        }catch(IOException e){
            System.err.println("Error reading file.");
            return null;
        }catch(ParserException e){
            System.err.println("File incorrectly formatted. No phone call data imported");
            return new PhoneBill("1");
        }
    }

    @Override
    public PhoneBill parse() throws ParserException {
        for(String e: fileRead){
            System.out.println(e);
        }
        return null;
    }


}
