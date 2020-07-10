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
     *               1->
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



    }
}
