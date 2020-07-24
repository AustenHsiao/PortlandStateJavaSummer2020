package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Every phone call has a name, caller number, receiver number, start time/date, and end time/date.
 * For a phone call, we should be able to view any of these pieces of info
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{

  private String callerNumber;
  private String calleeNumber;
  private String startTime;
  private String startAM_PM;
  private String startDate;
  private String endTime;
  private String endAM_PM;
  private String endDate;

  /**
   *
   * Constructor for a single phone call. Every phone call captures the caller/callee names and numbers as well
   * as the start and end times/dates. In the context of Project1, these will be supplied by the command-line
   * arguments. Here in project 3 we assume that the dates passed in are in the form MM/dd/yyyy
   *
   * @param callerNumber
   * @param calleeNumber
   * @param startTime
   * @param startDate
   * @param endTime
   * @param endDate
   */
  PhoneCall(
          String callerNumber,
          String calleeNumber,
          String startDate,
          String startTime,
          String startAM_PM,
          String endDate,
          String endTime,
          String endAM_PM){
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.startAM_PM = startAM_PM;
    this.startDate = startDate;
    this.endTime = endTime;
    this.endAM_PM = endAM_PM;
    this.endDate = endDate;
  }

  /**
   * Returns the callerNumber associated with the phonecall
   * @return
   */
  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  /**
   * Returns the callee's number associated with the phonecall
   * @return
   */
  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  /**
   * Returns a string containing the start time and start date
   * @return
   */
  @Override
  public String getStartTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getStartTime());
  }

  /**
   * Returns a string containing the end time and end date
   * @return
   */
  @Override
  public String getEndTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getEndTime());
  }

  /**
   * Returns the start time in a Date object
   * @return
   */
  @Override
  public Date getStartTime() {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    try {
      Date start = df.parse(this.startDate);
      String[] splitTime = this.startTime.split(":");
      int hour = parseInt(splitTime[0]);
      int minute = parseInt(splitTime[1]);
      if(this.startAM_PM.equalsIgnoreCase("PM")){
        return new Date((long)(start.getTime() + ((hour+12) * 3.6e+6) + (minute * 6e+4)));
      }
      return new Date((long)(start.getTime() + (hour * 3.6e+6) + (minute * 6e+4)));
    }catch(ParseException e){
      return null;
    }
  }

  /**
   * Returns the end time in a Date object
   * @return
   */
  @Override
  public Date getEndTime() {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    try {
      Date end = df.parse(this.endDate);
      String[] splitTime = this.endTime.split(":");
      int hour = parseInt(splitTime[0]);
      int minute = parseInt(splitTime[1]);
      if(this.endAM_PM.equalsIgnoreCase("PM")){
        return new Date((long)(end.getTime() + ((hour+12) * 3.6e+6) + (minute * 6e+4)));
      }
      return new Date((long)(end.getTime() + (hour * 3.6e+6) + (minute * 6e+4)));
    }catch(ParseException e){
      return null;
    }
  }

  /**
   * Denotes natural ordering (start time is highest priority, followed by caller number).
   * More recent phone calls will appear at higher indices.
   * @param o, phonecall to compare
   * @return int (denoting higher- positive or lower- negative)
   */
  @Override
  public int compareTo(PhoneCall o) {
    if(this.getStartTime().before(o.getStartTime())){
      return -1;
    }else if(this.getStartTime().after(o.getStartTime())){
      return 1;
    }else{
      return this.getCaller().compareTo(o.getCaller());
    }
  }
}
