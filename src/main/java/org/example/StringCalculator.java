package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    // Default delimiters used if none are specified
    private static final String DEFAULT_DELIMITERS = ",|\n";

    // Maximum valid number
    private static final int MAX_NUMBER = 1000;


    /**
     * Adds the numbers provided in the string, considering possible custom delimiters.
     *
     * @param input the input string containing numbers and possibly custom delimiters
     * @return the sum of the numbers
     */
    public static int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        // Extract delimiters and numbers from the input
        String[] parts = extractDelimitersAndNumbers(input);
        String delimiterPattern = parts[0];
        String numbers = parts[1];

        // Split the numbers using the delimiter pattern, filter out invalid numbers, and check for negative numbers
        List<Integer> numberList = Arrays.stream(numbers.split(delimiterPattern))
                .filter(s -> !s.isEmpty()) // Filter out empty strings
                .map(Integer::parseInt)
                .filter(num -> num <= MAX_NUMBER) // Ignore numbers greater than 1000
                .toList();

        // Check for negative numbers and throw an exception if any are found
        List<Integer> negativeNumbers = numberList.stream()
                .filter(num -> num < 0)
                .toList();

        if (!negativeNumbers.isEmpty()) {
            String negativeNumbersString = negativeNumbers.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("negatives not allowed: " + negativeNumbersString);
        }

        return numberList.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Extracts the delimiters and the actual number string from the input.
     * Handles custom delimiters if specified; otherwise, uses default delimiters.
     *
     * @param input the input string containing delimiters and numbers
     * @return a string array where the first element is the delimiter pattern and the second is the numbers string
     */
    private static String[] extractDelimitersAndNumbers(String input) {
        if (input.startsWith("//")) {
            return extractCustomDelimiters(input);
        }
        return new String[]{DEFAULT_DELIMITERS, input};
    }

    /**
     * Extracts custom delimiters from the input and returns them along with the numbers string.
     *
     * @param input the input string containing custom delimiters and numbers
     * @return a string array where the first element is the regex pattern for delimiters and the second is the numbers string
     */
    private static String[] extractCustomDelimiters(String input) {
        int delimiterIndex = input.indexOf("\n");
        if (delimiterIndex == -1) {
            // Handle case where the format is invalid (e.g., "//\n1,2,3")
            return new String[] { DEFAULT_DELIMITERS, input };
        }
        String delimiterPart = input.substring(2, delimiterIndex);
        String numbers = input.substring(delimiterIndex + 1);

        // If delimiterPart is empty, use the default delimiter
        if (delimiterPart.isEmpty()) {
            return new String[] { DEFAULT_DELIMITERS, numbers };
        }

        String delimiterPattern = delimiterPart.startsWith("[")
                ? extractMultipleDelimiters(delimiterPart)
                : Pattern.quote(delimiterPart);

        return new String[]{delimiterPattern, numbers};
    }

    /**
     * Extracts and constructs a regex pattern for multiple delimiters.
     *
     * @param delimiterPart the part of the input string containing multiple delimiters, e.g., "[***][%]"
     * @return a regex pattern that matches any of the specified delimiters, e.g., ".*?***|.*?%|"
     */
    private static String extractMultipleDelimiters(String delimiterPart) {
        Matcher matcher = Pattern.compile("\\[(.+?)]").matcher(delimiterPart);
        StringBuilder delimiters = new StringBuilder();

        while (matcher.find()) {
            // Append each custom delimiter, quoting special characters to treat them literally
            delimiters.append(Pattern.quote(matcher.group(1))).append("|");
        }

        // Remove the trailing "|" character
        return !delimiters.isEmpty() ? delimiters.substring(0, delimiters.length() - 1) : DEFAULT_DELIMITERS;
    }

}
