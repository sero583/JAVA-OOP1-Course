package com.sero583.howoldami;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Serhat G. (sero583)
 */
public class HowOldAmI {
    public static final int EXIT_CODE_OK = 0;
    public static final int EXIT_CODE_ARGS_MISSING = 1;
    public static final int EXIT_CODE_INVALID_ARGS = 2;

    private static final int[] DAYS_IN_MONTH = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        if(args.length<1) {
            System.out.println("Did not specify any date. Please specify a date first.");
            System.exit(EXIT_CODE_ARGS_MISSING);
            return;
        }

        Date startDate = parseUserInput(args[0]);

        if(startDate==null) {
            System.out.println("Input \"" + args[0] + "\" for the start date is not a valid date.");
            System.exit(EXIT_CODE_INVALID_ARGS);
            return;
        }

        Date endDate = args.length >= 2 ? parseUserInput(args[1]) : new Date(); // in most cases its today, when no second arg is specified
        if(args.length>=2) {
            System.out.println(args[0] + ":");
            System.out.println(startDate.toString());

            System.out.println(args[1] + ":");
            System.out.println(endDate.toString());
        }

        if(endDate==null) {
            System.out.println("Input \"" + args[1] + "\" for the end date is not a valid date.");
            System.exit(EXIT_CODE_INVALID_ARGS);
            return;
        }

        if(endDate.before(startDate)==true) {
            System.out.println("Start date cannot be after end date.");
            System.exit(EXIT_CODE_INVALID_ARGS);
        } else if(startDate.equals(endDate)==true) {
            System.out.println("Start date and end date cannot be identical.");
            System.exit(EXIT_CODE_INVALID_ARGS);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            // I could used java calendar built in functions, but its required to create a loop; so lets go ahead - Calendar functions are just used for parsing user input dates
            int startDay = calendar.get(Calendar.DAY_OF_MONTH); int startMonth = calendar.get(Calendar.MONTH); int startYear = calendar.get(Calendar.YEAR);

            // no new instance of calendar, just re-set the time
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_MONTH); int endMonth = calendar.get(Calendar.MONTH); int endYear = calendar.get(Calendar.YEAR);

            // why ever java Date starts counting from 0, so increment months
            startMonth++;
            endMonth++;

            int daysCounted = DAYS_IN_MONTH[startMonth-1] - startDay;


            int startOfMonthCounter = startMonth + 1; // skip birth month
            for(int i = startOfMonthCounter; i <= 12; i++) {
                daysCounted += DAYS_IN_MONTH[i-1];
            }

            int yearDiff = endYear - startYear;
            daysCounted += (yearDiff-1) * 365; // exclude destination year
            int leapYears = Math.round(yearDiff / 4); // leap years, cast to int already done
            daysCounted += leapYears;

            for(int i = 1; i <= endMonth-1; i++) {
                daysCounted += DAYS_IN_MONTH[i-1];
            }
            daysCounted += endDay;

            // subtract leapYears from daysCounted when looking at years, so code works right even when more than 365 leap years are reached
            System.out.println("You are " + daysCounted + " old. In other words being said, you are " + ((daysCounted-leapYears)/365) + " years old.");
            System.exit(EXIT_CODE_OK);
        }
    }

    private static final Set<DateFormat> knownDateFormat = new LinkedHashSet<>() {
        {
            add(new SimpleDateFormat("dd.MM.yyyy")); // german pattern
            // add some other generic patterns
            add(new SimpleDateFormat("MM-dd-yyyy"));
            add(new SimpleDateFormat("yyyy-MM-dd")); // ISO 8601

        }
    };

    public static Date parseUserInput(String input) {
        for(DateFormat dF : knownDateFormat) {
            try {
                return dF.parse(input);
            } catch(ParseException e) {
                // ignore
            }
        }
        return null;
    }
}
