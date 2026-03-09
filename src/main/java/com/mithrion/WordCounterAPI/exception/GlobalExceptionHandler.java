package com.mithrion.WordCounterAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "File too large!");
        error.put("message", "The maximum allowed size is 600MB.");

        return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception exc) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", exc.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
