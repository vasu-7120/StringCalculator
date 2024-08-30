package com.example.calculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Service {
	public int add(String numbers) {
		if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",";
        String numberString = numbers;

        // Check if there's a custom delimiter
        if (numbers.startsWith("//")) {
            int newlineIndex = numbers.indexOf('\n');
            if (newlineIndex == -1) {
                throw new IllegalArgumentException("Invalid input format");
            }
            delimiter = numbers.substring(2, newlineIndex);
            numberString = numbers.substring(newlineIndex + 1);
        }

        // Replace new lines and custom delimiters with commas
        numberString = numberString.replaceAll("\n", ",").replaceAll(Pattern.quote(delimiter), ",");

        // Split the numbers by commas
        String[] numberArray = numberString.split(",");

        List<Integer> negativeNumbers = new ArrayList<>();
        int sum = 0;

        for (String numStr : numberArray) {
            numStr = numStr.trim();
            if (numStr.isEmpty()) continue; // Ignore empty strings

            try {
                int num = Integer.parseInt(numStr);
                if (num < 0) {
                    negativeNumbers.add(num);
                } else {
                    sum += num;
                }
            } catch (NumberFormatException e) {
                // Ignore non-numeric values
            }
        }

        if (!negativeNumbers.isEmpty()) {
            String negativeNumbersString = String.join(",", negativeNumbers.stream().map(String::valueOf).toArray(String[]::new));
            throw new IllegalArgumentException("Negative numbers not allowed: " + negativeNumbersString);
        }

        return sum;
	}
}
