class Counter {
    private int count = 0;

    // Synchronized method to increment the counter
    public synchronized void increment() {
        count++;
    }

    // Getter for the counter value
    public int getCount() {
        return count;
    }
}

class UnsynchronizedCounter {
    private int count = 0;

    // Unsynchronized method to increment the counter
    public void increment() {
        count++;
    }

    // Getter for the counter value
    public int getCount() {
        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        // Problem Scenario: Unsynchronized Counter
        UnsynchronizedCounter unsynchronizedCounter = new UnsynchronizedCounter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsynchronizedCounter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsynchronizedCounter.increment();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Unsynchronized Counter result: " + unsynchronizedCounter.getCount());

        // Solution: Synchronized Counter
        Counter synchronizedCounter = new Counter();

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronizedCounter.increment();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronizedCounter.increment();
            }
        });

        thread3.start();
        thread4.start();

        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Synchronized Counter result: " + synchronizedCounter.getCount());
    }
}
