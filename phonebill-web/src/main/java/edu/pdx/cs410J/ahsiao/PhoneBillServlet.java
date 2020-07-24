package edu.pdx.cs410J.ahsiao;

import com.google.common.annotations.VisibleForTesting;
import org.w3c.dom.Text;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{

    /**
     * Tests if a string is a valid phone number in the form xxx-xxx-xxxx
     * where x is [0-9]. Length of string should be 12 (10 digits and 2 hyphens).
     * This function splits up the string by hyphens and tests if each section is comprised of
     * only digits. Returns true if the parameter is a valid phone number.
     * @param phonenum
     */
    private boolean validPhoneNumber(String phonenum){
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
    private boolean validDate(String date){
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
    private boolean validTime(String time){
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
    private String TwoDigitDate(String date){
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

    static final String WORD_PARAMETER = "customer";
    static final String DEFINITION_PARAMETER = "definition";
    static final String START_BOUND = "start";
    static final String END_BOUND = "end";

    private final Map<String, PhoneBill> dictionary = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        PrintWriter pw = response.getWriter();
        response.setContentType( "text/plain" );

        String word = getParameter( WORD_PARAMETER, request );
        String start = getParameter(START_BOUND, request);
        String end = getParameter(END_BOUND, request);
        String newStartDate;
        String newEndDate;

        if (word != null && start == null && end == null) {
            writeDefinition(word, response);
        }else if (word != null && start != null && end != null){
            String[] startString = start.split(" ");
            String[] endString = end.split(" ");

            if(validDate(startString[0])){
                newStartDate = TwoDigitDate(startString[0]);
            }else{
                pw.println("Malformed start date");
                return;
            }
            if(validDate(endString[0])){
                newEndDate = TwoDigitDate(endString[0]);
            }else{
                pw.println("Malformed end date");
                return;
            }

            if(!validTime(startString[1]) || !validTime(endString[1])){
                pw.println("Malformed time(s)");
                return;
            }

            if(!startString[2].equalsIgnoreCase("AM") && !startString[2].equalsIgnoreCase("PM")){
                pw.println("Start time am/pm not denoted correctly");
                return;
            }else if(!endString[2].equalsIgnoreCase("AM") && !endString[2].equalsIgnoreCase("PM")){
                pw.println("End time am/pm not denoted correctly");
                return;
            }

            // Reference call is only used for its time.
            PhoneCall referenceCall = new PhoneCall("111-111-1111", "222-222-2222", newStartDate, startString[1], startString[2], newEndDate, endString[1], endString[2]);
            if(referenceCall.getEndTime().before(referenceCall.getStartTime())){
                missingRequiredParameter( response, "end time occurs before start time." );
                response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED);
                return;
            }

            writeDefinition(word, response, referenceCall.getStartTime(), referenceCall.getEndTime());
        }else if( (word != null && start != null && end == null) || (word != null && start == null && end != null)){
            pw.println("Missing start or end time");
        }else {
            writeAllDictionaryEntries(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String word = getParameter(WORD_PARAMETER, request );
        if (word == null) {
            missingRequiredParameter(response, WORD_PARAMETER);
            return;
        }

        PhoneBill phonebill;
        if(!this.dictionary.containsKey(word)) {
            phonebill = new PhoneBill(word);
        }else{
            phonebill = this.dictionary.get(word);
        }

        String definition = getParameter(DEFINITION_PARAMETER, request );

        if ( definition == null) {
            // Missing definition
            missingRequiredParameter( response, "Customer Name" );
            response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }else if(definition.split(" ").length != 8){
            String[] arguments = definition.split(" ");

            try {
                String curr_argument;

                for(int i = 0; i < 8; ++i){
                    curr_argument = arguments[i];
                }

                missingRequiredParameter(response, "too many parameters");
                return;
            }catch(ArrayIndexOutOfBoundsException e){
                missingRequiredParameter(response, "missing parameters, format: callerNumber calleeNumber StartDate EndDate");
                return;
            }
        }else{
            // Definition matches
            String[] arguments = definition.split(" ");
            String callerNumber = arguments[0];
            String calleeNumber = arguments[1];
            String startDate = arguments[2];
            String startTime = arguments[3];
            String startAM_PM = arguments[4];
            String endDate = arguments[5];
            String endTime = arguments[6];
            String endAM_PM = arguments[7];

            if(!validPhoneNumber(callerNumber) || !validPhoneNumber(calleeNumber)){
                missingRequiredParameter( response, "malformed caller or callee phone number" );
                return;
            }else if(!validDate(startDate) || !validDate(endDate)){
                missingRequiredParameter( response, "malformed start or end date" );
                return;
            }else if(!validTime(startTime) || !validTime(endTime)){
                missingRequiredParameter( response, "malformed start or end time" );
                return;
            }else if(!startAM_PM.equalsIgnoreCase("AM") && !startAM_PM.equalsIgnoreCase("PM")){
                missingRequiredParameter( response, "start time am or pm denotation" );
                return;
            }else if(!endAM_PM.equalsIgnoreCase("AM") && !endAM_PM.equalsIgnoreCase("PM")){
                missingRequiredParameter( response, "end time am or pm denotation" );
                return;
            }

            PhoneCall validCall = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, startAM_PM, endDate, endTime, endAM_PM);
            if(validCall.getEndTime().before(validCall.getStartTime())){
                missingRequiredParameter( response, "end time occurs before start time." );
                response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED);
                return;
            }
            phonebill.addPhoneCall(validCall);
        }

        this.dictionary.put(word, phonebill);

        PrintWriter pw = response.getWriter();

        Collection<PhoneCall> calls = phonebill.getPhoneCalls();
        pw.println(phonebill.getCustomer() + ":");
        pw.flush();
        for(PhoneCall call: calls){
            pw.println("\t" + call.toString());
            pw.flush();
        }

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the phonecalls of the given customer's (word) phonebill to the HTTP response.
     *
     */
    private void writeDefinition(String name, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();

        if( !this.dictionary.containsKey(name)){
            pw.println("No entry found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            TextDumper.write(pw, this.dictionary.get(name));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private void writeDefinition(String name, HttpServletResponse response, Date start, Date end) throws IOException {
        PrintWriter pw = response.getWriter();

        if( !this.dictionary.containsKey(name)){
            pw.println("No entry found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            TextDumper.write(pw, this.dictionary.get(name), start, end);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();

        if(this.dictionary.size() == 0){
            pw.println("No phonebills to display");
            pw.flush();
        }else {
            for (PhoneBill name : dictionary.values()) {
                TextDumper.write(pw, name);
            }
        }
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    PhoneBill getDefinition(String word) {
        return this.dictionary.get(word);
    }

}
