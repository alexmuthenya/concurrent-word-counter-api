package com.mithrion.WordCounterAPI.worker;

import java.util.List;
import java.util.concurrent.Callable;

public class WordCountTask implements Callable<Integer> {

    private final List<String> lines;
    private final String targetWord;

    public WordCountTask(List<String> lines, String targetWord) {
        this.lines = lines;
        this.targetWord = targetWord;
    }
    @Override
    public Integer call() throws Exception {
        int count = 0;
        for (String line: lines) {
            count += countInLine(line, targetWord);
        }
        return count;

    }
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
