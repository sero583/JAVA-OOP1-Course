package com.sero583.minmax;

public class MinMax {
    public static void main(String[] args) {
        if(args.length<1) {
            System.out.println("Please supply the type from which you would like to know the miniumum and the maximum value.");
            return;
        }

        switch(args[0].toLowerCase()) {
            case "boolean":
                minMax('b', true);
            break;
            case "byte":
            case "b":
                minMax('b');
            break;
            case "char":
            case "c":
                minMax('c');
            break;
            case "double":
            case "d":
                minMax('d');
            break;
            case "float":
            case "f":
                minMax('f');
            break;
            case "integer":
            case "int":
            case "i":
                minMax('i');
            break;
            case "long":
            case "l":
                minMax('l');
            break;
            case "short":
            case "s":
                minMax('s');
            break;
            default:
                System.out.println("There is no primitive datatype called \"" + args[0] + "\"."); // use args[0] again to have the case, user entered
            break;
        }
    }

    public static String minMax(char i) {
        return minMax(i, false);
    }

    public static String minMax(char i, boolean isBoolean) {
        return minMax(i, isBoolean, true);
    }

    public static String minMax(char i, boolean isBoolean /* dind't knew a better way to handle it :/*/, boolean print) {
        Class c = null;

        if(i=='b') {
            if(isBoolean) {
                // boolean is 1 bit so.. + it does not have these constants
                String result = "[0, 1]";

                if(print==true) {
                    System.out.println(result);
                }
                return result;
            } else c = Byte.class;
        } else if(i=='c') {
            c = Character.class;
        } else if(i==100) { // fancy, right? This is possible, because char is only a reference to the index number in the ASCII/UTF-8 table :) 100 is index for small "d"
            c = Double.class;
        } else if(i=='f') {
            c = Float.class;
        } else if(i=='i') {
            c = Integer.class;
        } else if(i=='s') {
            c = Short.class;
        }

        try {
            String result = "[" + c.getField("MIN_VALUE").get(null) + ", " + c.getField("MAX_VALUE").get(null) + "]";

            if(print==true) {
                System.out.println(result);
            }
            return result;
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldn't determine min and max value, because class of type " + c.getSimpleName() + " has not the fields MIN_VALUE and or MAX_VALUE.");
        }
        return null;
    }
}
