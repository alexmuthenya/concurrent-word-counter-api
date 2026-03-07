package com.mithrion.WordCounterAPI.strategy;

import com.mithrion.WordCounterAPI.worker.WordCountTask;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Component
public class MultiThreadWordCounter implements WordCounterStrategy {
    private static final int CHUNK_SIZE = 20000; // How many lines per worker

    @Override
    public int CountWord(File file, String word) throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        List<Future<Integer>> futures = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> currentChunk = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                currentChunk.add(line);

                if (currentChunk.size() == CHUNK_SIZE) {
                    Callable<Integer> task = new WordCountTask(new ArrayList<>(currentChunk), word);
                    futures.add(executor.submit(task));
                    currentChunk.clear();
                }
            }
            if (!currentChunk.isEmpty()) {
                futures.add(executor.submit(new WordCountTask(currentChunk, word)));
            }
        }

        //Aggregate Results
        int totalCount = 0;
        for (Future<Integer> future : futures) {
            // .get() blocks until the thread is done.
            totalCount += future.get();
        }

        //Clean up
        executor.shutdown();

        return totalCount;
    }
}
