import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OptimizedProgram {

    public static void main(String[] args) {
        OptimizedProgram program = new OptimizedProgram();
        program.runBottlenecks();
    }

    void runBottlenecks() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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
        // 1. Efficient Algorithm: Sorting Large List
        List<Integer> numbers = IntStream.range(0, 1_000_000)
                .map(i -> (int) (Math.random() * 1_000_000))
                .boxed()
                .collect(Collectors.toList());
        Collections.sort(numbers); // Sorting still necessary, but now more memory-efficient.

        // 2. Reduced Synchronization: Efficient Counter Increment
        Counter counter = new Counter();
        IntStream.range(0, 1_000)
                .parallel()
                .forEach(i -> counter.increment()); // Use parallel stream to reduce synchronization impact
        System.out.println("Counter value: " + counter.getValue());

        // 3. Efficient Data Structures: Pre-sized HashMap
        Map<Integer, Integer> efficientMap = new HashMap<>(1_000_000);
        IntStream.range(0, 1_000_000)
                .forEach(i -> efficientMap.put(i, i * 2)); // Pre-size HashMap to avoid frequent resizing
        System.out.println("Map size: " + efficientMap.size());

        // 4. Controlled Cache: Bounded Cache
        List<Object> cache = new LinkedList<>();
        IntStream.range(0, 1_000_000)
                .forEach(i -> {
                    if (cache.size() >= 100_000) {
                        cache.remove(0); // Implement a simple eviction policy
                    }
                    cache.add(new Object());
                });
        System.out.println("Cache size: " + cache.size());
    }

    private static class Counter {
        private final AtomicInteger count = new AtomicInteger();

        public void increment() {
            count.incrementAndGet();
        }

        public int getValue() {
            return count.get();
        }
    }
}
