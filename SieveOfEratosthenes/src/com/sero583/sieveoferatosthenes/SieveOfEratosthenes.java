package com.sero583.sieveoferatosthenes;

/**
 * @author Serhat G. (sero583)
 */
public class SieveOfEratosthenes {
    public static final int EXIT_CODE_OK = 0;
    public static final int EXIT_CODE_NO_ARGS = 1;
    public static final int EXIT_CODE_INVALID_ARGS = 2;

    public static final int START = 2;
    public static final int LINEBREAK_AFTER = 20;

    public static void main(String[] args) {
        // do not check more for null we don't care about fancy JREs or other outer calls to this method in an abusive way
	    if(args.length<1) {
            System.out.println("Please input the maximum value.");
            System.exit(EXIT_CODE_NO_ARGS);
	        return;
        }

	    int max;

	    try {
            max = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
	        System.out.println("Maximum value must be an integer.");
	        System.exit(EXIT_CODE_INVALID_ARGS);
	        return;
        }

        // count how long we needed for proceeding
        long startTime = System.currentTimeMillis();

        int maxIncremented = max+1;
        int[] result = new int[maxIncremented];


        System.out.println("Using range " + START + "-" + max + ".");

        // 0 -> isn't prime (false)
        // 1 -> is prime (true)
        result[0] = result[1] = 0; // so I don't need to calculate much during indexing

        // create table
        for(int i = START; i < maxIncremented; i++) {
            result[i] = 1; // at initialization, everything is true
        }

        // now, we will look whats wrong by the use of an efficient logic and invalidate
        for(int i = START; i < maxIncremented; i++) {
            int value = result[i];

            /*
            not needed anymore, since starting at START
            if(value==0) {
                // is already true, skip
                continue;
            }*/

            if(isPrimeNumber(i)) {
                int start = i * i;

                for(int j = start; j < maxIncremented; j += i) {
                    result[j] = 0; // not prime
                }
            }
        }


        // output table
        String printResult = "Prime Numbers=[";
        int resultSize = result.length;
        int resultSizeBeforeEnd = resultSize - 4; // remove artificially created length (e.g. index 0 and 1 which already was false, increment etc.)
        for(int i = START; i < maxIncremented;  i++) {
            int state = result[i];
            if(state==0) {
                // this means value isn't prime number, so it gets skipped
                continue;
            }

            printResult += String.valueOf(i);

            if(i%LINEBREAK_AFTER==0) {
                printResult += "\n";
            } else if(i < resultSizeBeforeEnd) {
                printResult += ", ";
            }
        }

        if(printResult.endsWith("\n")) {
            printResult = printResult.substring(0, printResult.length()-1); // if it ends with a linebreak, the linebreak will cut of because it would seem weird if that end bracket would come after a linebreak
        }
        printResult += "]";

        long diff = System.currentTimeMillis() - startTime;
        System.out.println(printResult);
        System.out.println("Took " + diff + "ms for calculating result.");
        if(diff==0) {
            System.out.println("Yes, it can also output 0ms, when you launch it with the right (right in terms of giving much resources for execution) java configuration and your system is powerful enough.");
        }

	    System.exit(EXIT_CODE_OK);
    }

    private static boolean isPrimeNumber(int value) {
        if(value<=1) return false;

        for(int i = 2; i < value+1; i++) {
            if(i==value) continue;

            if(value%i==0) {
                return false;
            }
        }
        return true;
    }
}