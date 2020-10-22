package com.sero583.potentialenergy;

/**
 * @author Serhat G. (sero583)
 */
public class PotentialEnergy {
    public static void main(String[] args) {
        if(args.length<1) {
            System.out.println("Please input either float or double as first argument.");
        } else if(args.length<2) {
            System.out.println("Please input value for h.");
        } else if(args.length<3) {
            System.out.println("Please input value for m.");
        } else {
            // parse
            boolean useFloat;
            switch(args[0]) {
                case "double":
                case "d":
                    System.out.println("Using primitive data type double.");
                    useFloat = false;
                break;
                case "float":
                case "f":
                    System.out.println("Using primitive data type float.");
                    useFloat = true;
                break;
                default:
                    System.out.println("Please enter either double or float as the primitive datatype. \"" + args[0] + "\" is not valid.");
                return;
            }

            if(useFloat==true) {
                float g = 9.81F;
                float h = -1;
                try {
                    h = Float.parseFloat(args[1]);
                } catch(NumberFormatException e) {
                    System.out.println(args[1] + " entered for m is not a valid float value.");
                }
                // separate blocks for precise error messages
                float m = -1;
                try {
                    m = Float.parseFloat(args[2]);
                } catch(NumberFormatException e) {
                    System.out.println(args[1] + " entered for g is not a valid float value.");
                }

                if(h==-1) {
                    System.out.println("Couldn't parse h.");
                } else if(m==-1) {
                    System.out.println("Couldn't parse m.");
                } else {
                    System.out.println("Result for E=" + m + "*" + g + "*" + h + " is (formula: E=m*g*h): " + potEFloat(m, g, h));
                }
            } else {
                double g = 9.81D;
                double h = -1;
                try {
                    h = Double.parseDouble(args[1]);
                } catch(NumberFormatException e) {
                    System.out.println(args[1] + " entered for m is not a valid double value.");
                }

                double m = -1;
                try {
                    m = Double.parseDouble(args[2]);
                } catch(NumberFormatException e) {
                    System.out.println(args[1] + " entered for g is not a valid double value.");
                }

                if(h==-1) {
                    System.out.println("Couldn't parse h.");
                } else if(m==-1) {
                    System.out.println("Couldn't parse m.");
                } else {
                    System.out.println("Result for E=" + m + "*" + g + "*" + h + " is (formula: E=m*g*h): " + potE(m, g, h));
                }
            }
        }
    }

    private static double potE(double m, double g, double h) {
        return m * g * h;
    }

    private static float potEFloat(float m, float g, float h) {
        return m * g * h;
    }
}