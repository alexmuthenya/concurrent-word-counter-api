package com.mithrion.WordCounterAPI.strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SingleThreadWordCounter implements  WordCounterStrategy{
    private int totalCount;
    @Override
    public int CountWord(File file, String word) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                totalCount += countInLine(line, word);

            }


        }
        return totalCount;
    }

    private int countInLine(String line, String target) {
        int count = 0;
        // Split by whitespace (spaces, tabs, newlines)
        String[] words = line.split("\\s+");

        for (String word : words) {
            // Clean the word (remove punctuation) and compare
            if (word.replaceAll("[^a-zA-Z]", "").equalsIgnoreCase(target)) {
                count++;
            }
        }
        return count;
    }
}
