package de.codecave.demo.util;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Python3Compat {

    private static final Pattern REGEX_SPLIT = Pattern.compile("\\s");
    private static final Pattern REGEX_STRING_ISNUMERIC = Pattern.compile("[\\p{Nd}\\p{No}]+");

    private Python3Compat() {
    }

    // The isnumeric() method returns True if all the characters are numeric (0-9), otherwise False.
    // Exponents, like ² and ¾ are also considered to be numeric values.
    public static boolean isnumeric(String text) {
        return REGEX_STRING_ISNUMERIC.matcher(text).matches();
    }

    // python3 string.split: The split() method splits a string into a list. You can specify the separator, default separator is any whitespace
    public static Stream<String> split(String text) {
        return REGEX_SPLIT.splitAsStream(text);
    }

}
