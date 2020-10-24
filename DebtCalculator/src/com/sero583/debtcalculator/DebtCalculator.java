package com.sero583.debtcalculator;

/**
 * @author Serhat G. (sero583)
 */
public class DebtCalculator {
    public static final int LEAVE_CODE_OK = 0; // again, not necessary actually
    public static final int LEAVE_CODE_ARGS_MISSING = 1;
    public static final int LEAVE_CODE_INVALID_ARGS = 2;
    public static final int LEAVE_CODE_ERROR = 3;

    public static void main(String[] args) {
        if(args.length<3) {
            info("Please supply all necessary arguments before starting.");
            printSyntax();
            System.exit(LEAVE_CODE_ARGS_MISSING);
        } else {
            // initialize
            String rawTotalDebt = args[0];
            String rawInterestRate = args[1];
            String rawMonths = args[2];

            double totalDebt;
            // parse
            try {
                totalDebt = Double.parseDouble(rawTotalDebt);
            } catch(NumberFormatException e) {
                warning("You did not supply a double/integer as total debt value.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }
            totalDebt = roundCurrency(totalDebt);

            double interestRate;

            try {
                interestRate = Double.parseDouble(rawInterestRate);
            } catch(NumberFormatException e) {
                warning("You did not supply a double/integer for the interest rate value.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }

            if(interestRate<0) {
                warning("Interest rate must be greater than 0.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }

            if(interestRate>1) {
                warning("Interest rate must be smaller than 1.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }

            int months;
            try {
                months = Integer.parseInt(rawMonths);
            } catch(NumberFormatException e) {
                warning("Months must be a valid integer value.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }

            if(months<=0) {
                warning("Months must be greater than 0.");
                System.exit(LEAVE_CODE_INVALID_ARGS);
                return;
            }

            info("Using values:");
            info("Debt: " + totalDebt);
            info("Interest rate: " + (interestRate*100) + "%");
            info("Duration: " + months + " month" + (months > 1 ? "s" : ""));
            // finally ended validating values, now here comes the real deal
            double[] conditions = calculateTotalInterest(totalDebt, interestRate, months);

            if(conditions==null) {
                log("We are sorry to let you know, but we cannot offer you a credit under these conditions.");
                System.exit(LEAVE_CODE_OK);
                return;
            }

            double totalInterest = conditions[0];
            int monthsCalculated = (int) conditions[1];

            if(monthsCalculated!=months) {
                warning("New credit duration has been calculated.");
                warning("Credit months have been decreased to " + monthsCalculated + " months, means a decrease of " + ((monthsCalculated-months)*-1) + " happened.");
            }

            double finalCosts = roundCurrency(totalDebt + totalInterest);
            log("Return per month:\t" + (finalCosts / monthsCalculated));
            log("Return total:\t\t" + finalCosts);

            System.exit(LEAVE_CODE_OK); // actually not necessary, but lets do it anyway
        }
    }

    public static double[] calculateTotalInterest(double totalDebt, double interestRate, int months) {
        return calculateTotalInterest(totalDebt, interestRate, months, true);
    }

    public static double[] calculateTotalInterest(double totalDebt, double interestRate, int months, boolean securityFeature) {
        double totalInterest = (totalDebt * interestRate) * months;
        double halfDebt = totalDebt / 2; // even though CPU can handle this easily, don't waste resources on calculating during the while loop the same result over and over
        // it is all about CPU efficiency, imagine writing a server which gets used by dozens of people, CPU efficiency is there very important...

        while(securityFeature==true && totalInterest > halfDebt) {
            months--; // cool bank, very flexible credits :)
            if(months<=0) {
                return null;
            }
            totalInterest = (totalDebt * interestRate) * months;
        }

        return new double[] { totalInterest, months };
    }

    private static double roundCurrency(double value) {
        return round(value, 2);
    }

    public static double round(double value, int precision) {
        if(precision<0) {
            throw new RuntimeException("Precision cannot be smaller than 0.");
        }
        double powVal = Math.pow(10, precision);
        return Math.round(value * powVal) / powVal;
    }

    private static void info(String log) {
        log(log, "[INFO] ");
    }

    private static void warning(String log) {
        log(log, "[WARNING] ");
    }

    private static void critical(String log) {
        log(log, "[CRITICAL] ");
    }

    private static void printSyntax() {
        log("Syntax: <double: totalDebt> <double: interestRate (e.g.: 0.1 for 10%, 0.25 for 25%, ...)> <int: months>");
    }

    private static void log(String log) {
        log(log, null);
    }

    // basic wrapper function, if I decide ever to make a more beautiful logger, I wont need to re-structure entire project even though I most likely won
    private static void log(String log, String prefix) {
        System.out.println((prefix != null ? prefix : "") + log);
    }
}