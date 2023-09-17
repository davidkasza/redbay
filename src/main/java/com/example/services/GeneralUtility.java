package com.example.services;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtility {
    public static boolean isEmptyOrNull(String data) {
        return (data == null || data.isBlank());
    }

    public static boolean isAGivenDateAfterTheCurrentDate(SimpleDateFormat givenSimpleDateFormat) {
        SimpleDateFormat currentTime = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        String givenDate = givenSimpleDateFormat.format(new Date());
        String currentDate = currentTime.format(new Date());

        int result = givenDate.compareTo(currentDate);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDateValid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeException e) {
            return false;
        }

        return true;
    }

    public static boolean isURLValid(String url) {
        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (isMatch(url, regex)) {
            return true;
        }
        return false;
    }

    private static boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean isValidLongNumber(String longNumber) {
        try {
            Long id = Long.parseLong(longNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
