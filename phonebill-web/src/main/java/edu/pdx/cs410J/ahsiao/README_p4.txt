Written by Austen Hsiao for CS510, assignment 4.

This program is used to connect to a server to:
    (1) Print out a user's phonebill information
    (2) Print out a user's phonebill phonecalls starting within a given time frame
    (3) Add a phone call to a user's phone bill

usage: java edu.pdx.cs410J.ahsiao.Project4 [options] <args>
    args are (in this order):
        customer               Person whose phone bill we are modeling
        callerNumber           Phone number of caller
        calleeNumber           Phone number of person who was called
        start                  Date and time (am/pm) call began
        end                    Date and time (am/pm) call ended
    options are (options may appear in any order):
        -host hostname         Host computer on which the server runs
        -port port             Port on which the server is listening
        -search                Phone calls should be searched for
	-print		       Prints a description of the new phone call
        -README                Prints a README for this project and exits
    Dates and times should be in the format: mm/dd/yyyy hh:mm am/pm