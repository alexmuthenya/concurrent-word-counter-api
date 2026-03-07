package com.mithrion.WordCounterAPI.controller;


import com.mithrion.WordCounterAPI.model.WordCountResponse;
import com.mithrion.WordCounterAPI.service.WordCountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/wordcount")
public class WordCountController {

    private final WordCountService wordCountService;

    public WordCountController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @PostMapping
    public WordCountResponse countWord(
            @RequestParam("file") MultipartFile file,
            @RequestParam("word") String word,
            @RequestParam(value = "mode", defaultValue = "single") String mode) throws Exception {

        return wordCountService.countWordOccurrences(file, word, mode);
    }
}