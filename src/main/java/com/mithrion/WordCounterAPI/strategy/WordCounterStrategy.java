package com.mithrion.WordCounterAPI.strategy;

import java.io.File;

public interface WordCounterStrategy {

    int CountWord(File file, String word) throws Exception;
}
