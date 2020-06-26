package edu.pdx.cs410J.ahsiao;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

  /*
  Phone call information is stored privately within a PhoneCall object in case we need it for
  some sort of phone bill persistence later on
   */
  private String callerName;
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
   * @param customerName
   * @param callerNumber
   * @param calleeNumber
   * @param startTime
   * @param startDate
   * @param endTime
   * @param endDate
   */
  PhoneCall(
          String customerName,
          String callerNumber,
          String calleeNumber,
          String startDate,
          String startTime,
          String endDate,
          String endTime){
    this.callerName = customerName;
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.startDate = startDate;
    this.endTime = endTime;
    this.endDate = endDate;
  }

  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return this.startTime + " " + this.startDate;
  }

  @Override
  public String getEndTimeString() {
    return this.endTime + " " + this.endDate;
  }
}
