package controller;

import model.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class userManager {
    public static LinkedHashMap<String, String> directorList = new LinkedHashMap<>();
    public static LinkedHashMap<String, String> librarianList = new LinkedHashMap<>();

    public static void setManagerList() {
        User firstDirector = new User("votiendung","123456");
        User secondDirector = new User("dotienthanh","123456");
        directorList.put(firstDirector.getUserName(), firstDirector.getPassword());
        directorList.put(secondDirector.getUserName(), secondDirector.getPassword());
        User firstLibrarian = new User("doquocviet","abcd");
        User secondLibrarian = new User("lothihuyen","abcd");
        librarianList.put(firstLibrarian.getUserName(), firstLibrarian.getPassword());
        librarianList.put(secondLibrarian.getUserName(), secondLibrarian.getPassword());

    }

    public static boolean checkIsDirector(String userName) {
        Set<Map.Entry<String, String>> directorEntries = directorList.entrySet();
        for (Map.Entry<String, String> directorEntry: directorEntries) {
            String usernameValue = directorEntry.getKey();
            if (usernameValue.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIsLibrarian(String userName) {
        Set<Map.Entry<String, String>> librarianEntries = librarianList.entrySet();
        for (Map.Entry<String, String> librarianEntry: librarianEntries) {
            String usernameValue = librarianEntry.getKey();
            if (usernameValue.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIsPassword(String userName, String password) {
        Set<Map.Entry<String, String>> directorEntries = directorList.entrySet();
        for (Map.Entry<String, String> directorEntry: directorEntries) {
            String usernameValue = directorEntry.getKey();
            String passwordValue = directorEntry.getValue();
            if (usernameValue.equals(userName) && passwordValue.equals(password)) {
                return true;
            }
        }
        Set<Map.Entry<String, String>> librarianEntries = librarianList.entrySet();
        for (Map.Entry<String, String> librarianEntry: librarianEntries) {
            String usernameValue = librarianEntry.getKey();
            String passwordValue = librarianEntry.getValue();
            if (usernameValue.equals(userName) && passwordValue.equals(password)) {
                return true;
            }
        }
        return false;
    }


}
