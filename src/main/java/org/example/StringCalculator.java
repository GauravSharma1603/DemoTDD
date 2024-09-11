package org.example;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    // Default delimiters used if none are specified
    private static final String DEFAULT_DELIMITERS = ",|\n";

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

        // Split the numbers using the delimiter pattern and sum them up
        return Arrays.stream(numbers.split(delimiterPattern))
                .filter(s -> !s.isEmpty()) // Filter out empty strings
                .mapToInt(Integer::parseInt)
                .sum();
    }

    /**
     * Extracts the delimiters and the actual number string from the input.
     *
     * @param input the input string containing delimiters and numbers
     * @return a string array where the first element is the delimiter pattern and the second is the numbers string
     */
    private static String[] extractDelimitersAndNumbers(String input) {
        // Default delimiter pattern
        String delimiterPattern = DEFAULT_DELIMITERS;
        String numbers = input;

        // Check for custom delimiter format
        if (input.startsWith("//")) {
            int delimiterIndex = input.indexOf("\n");
            String delimiterPart = input.substring(2, delimiterIndex);
            numbers = input.substring(delimiterIndex + 1);

            // Handle multiple custom delimiters enclosed in brackets
            if (delimiterPart.startsWith("[")) {
                delimiterPattern = extractMultipleDelimiters(delimiterPart);
            } else {
                // Single custom delimiter
                delimiterPattern = Pattern.quote(delimiterPart);
            }
        }

        return new String[] { delimiterPattern, numbers };
    }

    /**
     * Extracts and constructs a regex pattern for multiple delimiters.
     *
     * @param delimiterPart the part of the input string containing multiple delimiters
     * @return a regex pattern for all the delimiters
     */
    private static String extractMultipleDelimiters(String delimiterPart) {
        Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(delimiterPart);
        StringBuilder delimiters = new StringBuilder();

        while (matcher.find()) {
            // Append each custom delimiter, quoting special characters
            delimiters.append(Pattern.quote(matcher.group(1))).append("|");
        }

        // Remove the last "|" character
        return !delimiters.isEmpty() ? delimiters.substring(0, delimiters.length() - 1) : DEFAULT_DELIMITERS;
    }
}
