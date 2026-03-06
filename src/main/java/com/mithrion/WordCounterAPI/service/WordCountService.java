package com.mithrion.WordCounterAPI.service;


import com.mithrion.WordCounterAPI.model.WordCountResponse;
import com.mithrion.WordCounterAPI.strategy.SingleThreadWordCounter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class WordCountService {

    private final SingleThreadWordCounter counter = new SingleThreadWordCounter();

    public WordCountResponse countWord(MultipartFile multipartFile, String word) throws Exception {

        File file = convertToFile(multipartFile);

        long start = System.currentTimeMillis();

        int count = counter.CountWord(file, word);

        long end = System.currentTimeMillis();

        return new WordCountResponse(
                word,
                count,
                "single",
                end - start
        );
    }

    private File convertToFile(MultipartFile multipartFile) throws Exception {
        File file = File.createTempFile("upload_", ".txt");
        multipartFile.transferTo(file);
        return file;
    }
}


