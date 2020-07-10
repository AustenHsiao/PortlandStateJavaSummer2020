Written by Austen Hsiao for CS510, assignment 2.

This program is used to:
    (1) Read in a file to create a phone bill for the specified user
        and/or
    (2) Dump the contents of a user's phone bill into a text file
        and/or
    (3) Add a phone call to a user's phone bill

usage: java edu.pdx.cs410J.ahsiao.Project2 [options] <args>
    args are (in this order):
        customer               Person whose phone bill we’re modeling
        callerNumber           Phone number of caller
        calleeNumber           Phone number of person who was called
        start                  Date and time call began (24-hour time)
        end                    Date and time call ended (24-hour time)
    options are (options may appear in any order):
        -textFile file         Where to read/write the phone bill
        -print                 Prints a description of the new phone call
        -README                Prints a README for this project and exits
    Dates and times should be in the format: mm/dd/yyyy hh:mm