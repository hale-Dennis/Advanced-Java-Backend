import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int maxElements;

    public Producer(BlockingQueue<Integer> queue, int maxElements) {
        this.queue = queue;
        this.maxElements = maxElements;
    }

    @Override
    public void run() {
        for (int i = 1; i <= maxElements; i++) {
            try {
                System.out.println("Produced: " + i);
                queue.put(i); // Put element in the queue
                Thread.sleep(100); // Simulate time-consuming task
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
        try {
            queue.put(-1); // Sentinel value to indicate end of production
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int value = queue.take(); // Take element from the queue
                if (value == -1) { // Sentinel value indicating end of consumption
                    break;
                }
                System.out.println("Consumed: " + value);
                Thread.sleep(150); // Simulate time-consuming task
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer interrupted");
        }
    }
}

public class Main{
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5); // Buffer size of 5

        Producer producer = new Producer(queue, 10); // Producer will produce 10 elements
        Consumer consumer = new Consumer(queue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
