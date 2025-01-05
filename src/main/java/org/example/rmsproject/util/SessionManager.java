package org.example.rmsproject.util;

import org.example.rmsproject.models.Users;

public class SessionManager {
    private static Users loggedInUser;

    public static Users getLoggedInUser() {

        return loggedInUser;
    }

    public static void setLoggedInUser(Users user) {

        loggedInUser = user;
    }
}
