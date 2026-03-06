package com.mithrion.WordCounterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordCountResponse {
    private String word;
    private int count;
    private String mode;
    private long executionTimeMs;
}


