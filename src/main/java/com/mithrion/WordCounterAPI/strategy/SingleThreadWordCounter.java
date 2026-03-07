package com.mithrion.WordCounterAPI.strategy;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class SingleThreadWordCounter implements  WordCounterStrategy{
    private int totalCount;
    @Override
    public int CountWord(File file, String word) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalCount += countInLine(line, word);
            }}
        return totalCount;}
    private int countInLine(String line, String target) {
        int count = 0;
        int index = 0;
        String lowerLine = line.toLowerCase();
        String lowerTarget = target.toLowerCase();

        while ((index = lowerLine.indexOf(lowerTarget, index)) != -1) {
            count++;
            index += lowerTarget.length();
        }
        return count;
    }
}
