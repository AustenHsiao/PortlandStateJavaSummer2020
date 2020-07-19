Written by Austen Hsiao for CS510, assignment 3.

This program is used to:
    (1) Read in a file to create a phone bill for the specified user
        and/or
    (2) Dump the contents of a user's phone bill into a text file
        and/or
    (3) Dump the formatted contents of a user's phone bill into a text file
        and/or
    (3) Add a phone call to a user's phone bill

usage: java edu.pdx.cs410J.ahsiao.Project2 [options] <args>
    args are (in this order):
        customer               Person whose phone bill we’re modeling
        callerNumber           Phone number of caller
        calleeNumber           Phone number of person who was called
        start                  Date and time (am/pm) call began
        end                    Date and time (am/pm) call ended
    options are (options may appear in any order):
        -pretty file           Pretty print the phone bill to a text file
                               or standard out (file -)
        -textFile file         Where to read/write the phone bill
        -print                 Prints a description of the new phone call
        -README                Prints a README for this project and exits
    Dates and times should be in the format: mm/dd/yyyy hh:mm am/pm