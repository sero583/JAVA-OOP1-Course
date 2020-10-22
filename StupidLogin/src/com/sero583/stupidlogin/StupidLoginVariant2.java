package com.sero583.stupidlogin;

/**
 * @author Serhat G. (sero583)
 */
public class StupidLoginVariant2 {
    public static String[] user = {"user1", "user2", "user3", "user4"};
    public static String[] password = {"passwd1", "pw2", "pw3", "pw4"};

    // no this time I don't use exit codes
    public static void main(String[] args) {
        if(args.length<1) {
           System.out.println("Username is missing.");
        } else if(args.length<2) {
            System.out.println("Password is missing.");
        } else {
            System.out.println("Trying to login...");

            String inputUsername = args[0];
            String inputPassword = args[1];

            for(int i = 0; i < user.length; i++) {
                String currentUser = user[i];

                if(currentUser.equals(inputUsername)==true) {
                    // user found, compare password now
                    String pw = password[i];

                    if(inputPassword.equals(pw)) {
                        // login
                        System.out.println("Successfully logged in!");
                        return;
                    } else {
                        // fehlerhafter login
                        System.out.println("Wrong login credentials.");
                        return;
                    }
                }
            }
            System.out.println("User \"" + inputUsername + "\" could not be found.");
        }
    }
}
