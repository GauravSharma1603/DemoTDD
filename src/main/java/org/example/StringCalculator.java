package org.example;

import java.util.Arrays;

public class StringCalculator {

    public static int add(String numbers) {
        if(numbers.isEmpty()){
            return 0;
        }
        return Arrays.stream(numbers.split(",")).mapToInt(Integer::parseInt).sum();
    }
}
