package com.sero583.stupidlogin;

import java.util.LinkedHashMap;

/**
 * @author Serhat G. (sero583)
 */
public class StupidLogin {
    public static final int STATUS_OK = 0;
    public static final int STATUS_WRONG_SYNTAX_USAGE = 1;
    public static final int STATUS_INVALID_LOGIN_CREDENTIALS = 2;
    public static final int STATUS_ERROR = 3;

    private static final LinkedHashMap<String, String> plainLoginData = new LinkedHashMap<>() {
        {
            put("mmuster", "hink123");
            put("admin", "123");
            put("testuser", "test");
            put("fwalter", "password");
        }
    };

    public static void main(String[] args) {
        if(args==null) {
            System.out.println("Unexpected java behaviour. Is your JRE fine?");
            System.exit(STATUS_ERROR);
            return;
        }

        if(args.length<1) {
            System.out.println("Please enter a username.");
            System.exit(STATUS_WRONG_SYNTAX_USAGE);
        } else if(args.length<2) {
            // user, but no password
            System.out.println("Please enter a password.");
        } else { // three or more, so we don't ignore if e.g. user just spaces a few times - but what we ignore is the input which isn't necessary. Be user friendly :)
            String userName = args[0];
            String password = args[1];

            if(plainLoginData.containsKey(userName)==false) {
                System.out.println("User \"" + userName + "\" was not found.");
                System.exit(STATUS_INVALID_LOGIN_CREDENTIALS);
                return;
            }

            System.out.println("User \"" + userName + "\" was found.");
            System.out.println("Logging in...");
            if(plainLoginData.get(userName).equals(password)==false) {
                System.out.println("Wrong password given for user \"" + userName + "\".");
                System.exit(STATUS_INVALID_LOGIN_CREDENTIALS);
                return;
            }

            System.out.println("Successfully logged in!");
            System.exit(STATUS_OK); // not necessary - I know, but let's do it anyway
        }
    }
}
