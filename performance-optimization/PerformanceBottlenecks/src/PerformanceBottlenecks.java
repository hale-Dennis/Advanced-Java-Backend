import java.util.*;
import java.util.concurrent.*;

public class PerformanceBottlenecks {

    public static void main(String[] args) {
        OptimizedProgram program = new OptimizedProgram();
        program.runBottlenecks();
    }

    private void runBottlenecks() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Run intensive tasks for a long time
        for (int i = 0; i < 10; i++) {
            executor.submit(this::performIntensiveTasks);
        }

        // Let the tasks run for a while
        try {
            Thread.sleep(5 * 60 * 1000); // Sleep for 5 minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void performIntensiveTasks() {
        // 1. Inefficient Algorithm: Sorting Large List
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            numbers.add((int) (Math.random() * 1_000_000));
        }
        Collections.sort(numbers); // Inefficient sorting for large lists

        // 2. Excessive Synchronization: Inefficient Counter Increment
        Counter counter = new Counter();
        for (int j = 0; j < 1_000; j++) {
            counter.increment(); // Excessive synchronization
        }
        System.out.println("Counter value: " + counter.getValue());

        // 3. Inefficient Data Structures: Frequent HashMap Resizing
        Map<Integer, Integer> inefficientMap = new HashMap<>();
        for (int i = 0; i < 1_000_000; i++) {
            inefficientMap.put(i, i * 2); // Frequent resizing due to large number of entries
        }
        System.out.println("Map size: " + inefficientMap.size());

        // 4. Memory Leaks: Unbounded Cache
        List<Object> cache = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            cache.add(new Object()); // Simulating memory leak
        }
        System.out.println("Cache size: " + cache.size());
    }

    private static class Counter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getValue() {
            return count;
        }
    }
}
