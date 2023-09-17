package com.example.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

public class GeneralUtilityTest {
    @Test
    void isAGivenDateAfterTheCurrentDate_ReturnsFalse() {
        Assertions.assertFalse(GeneralUtility.isAGivenDateAfterTheCurrentDate(new SimpleDateFormat("04-24-1998 15:28")));
    }

    @Test
    void isDateValid_ReturnsTrue() {
        Assertions.assertTrue(GeneralUtility.isDateValid("04-26-2023 23:58"));
    }

    @Test
    void isDateValid_ReturnsFalse() {
        Assertions.assertFalse(GeneralUtility.isDateValid("04-26-2023 24:58"));
    }

    @Test
    void isURLValid_ReturnsTrue() {
        Assertions.assertTrue(GeneralUtility.isURLValid("https://www.google.com/photos/123"));
    }

    @Test
    void isURLValid_ReturnsFalse() {
        Assertions.assertFalse(GeneralUtility.isURLValid("nemURL.com"));
    }

    @Test
    void isValidLongNumber_ReturnsTrue() {
        Assertions.assertTrue(GeneralUtility.isValidLongNumber("121"));
    }

    @Test
    void isValidLongNumber_ReturnsFalse() {
        Assertions.assertFalse(GeneralUtility.isValidLongNumber("5.5"));
    }
}
