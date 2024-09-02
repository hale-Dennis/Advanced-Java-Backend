import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple", "grape", "orange"};

        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit tasks to the executor
        for (String word : words) {
            executor.submit(() -> incrementWordCount(word));
        }

        // Shutdown the executor
        executor.shutdown();

        // Wait for all tasks to complete
        while (!executor.isTerminated()) {
            // Busy wait
        }

        // Print the word counts
        System.out.println("Word Counts: " + wordCountMap);
    }

    private static void incrementWordCount(String word) {
        wordCountMap.merge(word, 1, Integer::sum);
    }
}