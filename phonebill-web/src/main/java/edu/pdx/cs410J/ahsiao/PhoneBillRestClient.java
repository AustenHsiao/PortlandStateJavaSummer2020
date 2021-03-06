package edu.pdx.cs410J.ahsiao;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all dictionary entries from the server
     */
    public Map<String, PhoneBill> getAllDictionaryEntries() throws IOException {
        Map<String, PhoneBill> phoneBook = new HashMap<String, PhoneBill>();

        String customerName = "";
        String caller;
        String callee;
        String startDate;
        String endDate;
        String startTime;
        String endTime;
        String startTimeAMPM;
        String endTimeAMPM;
        PhoneCall temp;

        Response response = get(this.url, Map.of());

        if(response.getContent().equals("No phonebills to display")){
            return phoneBook;
        }

        // I rewrote the parser so I could work through the logic and jog my memory...
        String[] completeDictionary = response.getContent().split("\n");


        for(String line: completeDictionary){
            if(line.contains("BILL FOR")){
                customerName = line.split("BILL FOR ")[1].split(":")[0];
                phoneBook.put(customerName, new PhoneBill(customerName));
            }else{
                String[] currentLine = line.split(" ");
                caller = currentLine[3];
                callee = currentLine[5];
                startDate = Project4.TwoDigitDate(currentLine[7].split(",")[0]);
                endDate = Project4.TwoDigitDate(currentLine[11].split(",")[0]);
                startTime = currentLine[8];
                endTime = currentLine[12];
                startTimeAMPM = currentLine[9];
                endTimeAMPM = currentLine[13];

                // we are relying on the assumption that our values are well formed such that the "BILL FOR"
                // line is always read first.
                temp = new PhoneCall(caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
                PhoneBill tempBill = phoneBook.get(customerName);
                tempBill.addPhoneCall(temp);
                phoneBook.replace(customerName, tempBill);
            }
      }
      return phoneBook;
    }

    /**
     * Returns the definition for the given word
     */
    public PhoneBill getDefinition(String word) throws IOException {
        Map<String, PhoneBill> phoneBook = getAllDictionaryEntries();
        if(phoneBook.containsKey(word)){
            return phoneBook.get(word);
        }
        throw new PhoneBillRestException(404);
    }

    public void addDictionaryEntry(String word, String definition) throws IOException {
      Response response = postToMyURL(Map.of("customer", word, "definition", definition));
      throwExceptionIfNotOkayHttpStatus(response);
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
      return post(this.url, dictionaryEntries);
    }

    public void removeAllDictionaryEntries() throws IOException {
      Response response = delete(this.url, Map.of());
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new PhoneBillRestException(code);
      }
      return response;
    }

    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
      PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

}
