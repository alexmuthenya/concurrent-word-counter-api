package com.mithrion.WordCounterAPI.service;


import com.mithrion.WordCounterAPI.model.WordCountResponse;
import com.mithrion.WordCounterAPI.strategy.MultiThreadWordCounter;
import com.mithrion.WordCounterAPI.strategy.SingleThreadWordCounter;
import com.mithrion.WordCounterAPI.strategy.WordCounterStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Service
public class WordCountService {

    private final SingleThreadWordCounter singleThreadWordCounter;
    private final MultiThreadWordCounter multiThreadWordCounter;

    public WordCountService(SingleThreadWordCounter single, MultiThreadWordCounter multi) {
        this.singleThreadWordCounter = single;
        this.multiThreadWordCounter = multi;
    }

    public WordCountResponse countWordOccurrences(MultipartFile multipartFile, String word, String mode) throws Exception {
        File file = convertToFile(multipartFile);
        WordCounterStrategy strategy = mode.equalsIgnoreCase("multi")
                ? multiThreadWordCounter
                : singleThreadWordCounter;

        long startTime = System.currentTimeMillis();
        int count = strategy.CountWord(file, word);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        file.delete();
        return new WordCountResponse(word, count, mode, duration);
    }

    private File convertToFile(MultipartFile multipartFile) throws Exception {
        File file = File.createTempFile("upload_", ".txt");
        multipartFile.transferTo(file);
        return file;
    }
}


