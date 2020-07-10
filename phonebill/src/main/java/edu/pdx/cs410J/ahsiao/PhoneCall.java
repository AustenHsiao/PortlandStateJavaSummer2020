package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * Every phone call has a name, caller number, receiver number, start time/date, and end time/date.
 * For a phone call, we should be able to view any of these pieces of info
 */
public class PhoneCall extends AbstractPhoneCall {

  /*
  Phone call information is stored privately within a PhoneCall object in case we need it for
  some sort of phone bill persistence later on
   */
  private String callerNumber;
  private String calleeNumber;
  private String startTime;
  private String startDate;
  private String endTime;
  private String endDate;

  /**
   *
   * Constructor for a single phone call. Every phone call captures the caller/callee names and numbers as well
   * as the start and end times/dates. In the context of Project1, these will be supplied by the command-line
   * arguments.
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
          String endDate,
          String endTime){
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.startDate = startDate;
    this.endTime = endTime;
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
    return this.startDate + " " + this.startTime;
  }

  /**
   * Returns a string containing the end time and end date
   * @return
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }
}
