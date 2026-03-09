# High-Performance WordCounter API

A production-grade Spring Boot REST API engineered to process massive text files  with high efficiency. This project demonstrates advanced Java concepts including **Concurrent Programming**, **Memory Optimization**, and **Global Exception Handling**.



## Performance Benchmarks
Measured using a **20-million-line** ($~650\text{MB}$) text file.

| Execution Mode | Duration (ms) | Duration (sec) | Status |
| :--- | :--- | :--- | :--- |
| **Single-Threaded** | $16, 345\text{ms}$ | $16.3\text{s}$ | Stable |
| **Multi-Threaded** |$13,806\text{ms}$ | $13.8\text{s}$|  **16% Faster** |

##  Technical Architecture & Decisions
### 1. Algorithmic Optimization (`indexOf` vs `split`)
Initially, the project used `String.split()`. However, processing 20M lines created hundreds of millions of temporary `String` objects, causing the JVM to suffer from massive **Garbage Collection (GC) pauses**.
* **Solution:** Switched to a manual `indexOf()` loop. This reduced object allocation by ~90%, bringing execution time down from minutes to seconds.

### 2. Strategic Concurrency
The API utilizes the **Strategy Design Pattern** to switch between processing modes.
* **Producer-Consumer Model:** The main thread reads the file in chunks of **100,000 lines**, acting as a producer. 
* **Worker Pool:** An `ExecutorService` with a fixed thread pool (scaled to the number of CPU cores) processes these chunks in parallel using `Callable` tasks and `Future` result aggregation.
  
## 3. Concurrency Tuning: The Search for the "Sweet Spot"

A critical part of this project was determining the optimal `CHUNK_SIZE` for the `MultiThreadWordCounter`  tested various sizes to balance **Context Switching Overhead** vs. **Thread Saturation**.

### Chunk Size Benchmarks (20M Lines)
* **1,000 lines:** $16.8\text{s}$
* **20,000 lines:** $14.7\text{s}$
* **100,000 lines: 13.8s (OPTIMAL)**
* **200,000 lines:** $15.6\text{s}$

**Conclusion:** A chunk size of **100k lines** provided the best throughput, ensuring that worker threads remained fully utilized without the main thread becoming a bottleneck during task dispatching.

---

##  Installation & Setup

### 1. Configuration
Update `src/main/resources/application.properties` to allow large uploads:
```properties
spring.servlet.multipart.max-file-size=600MB
spring.servlet.multipart.max-request-size=600MB 
```

### 2. Build and Run

```bash
./mvnw clean spring-boot:run
```

---

##  API Documentation

**Endpoint:** `POST /api/wordcount`

**Content-Type:** `multipart/form-data`

| Parameter | Type | Required | Default |
| --- | --- | --- | --- |
| file | File (.txt) | Yes | - |
| word | String | Yes | - |
| mode | String | No | single |

### Success Response (200 OK)
JSON
```
{
    "word": "beta",
    "count": 20000000,
    "mode": "multi",
    "executionTimeMs": 13,806
}

```

### Error Response (413 Content Too Large)
JSON
```code-container formatted ng-tns-c2432481757-255
{
    "error": "File Too Large",
    "message": "Maximum upload size is 600MB.",
    "status": "413"
}

```

---

##  Author

  **Alex Muthenya** 

