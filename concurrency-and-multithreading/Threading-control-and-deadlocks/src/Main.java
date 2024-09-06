import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Main {

    // Example class demonstrating thread interruption
    static class InterruptibleTask implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " running: " + i);
                    Thread.sleep(500); // Simulate long-running task
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted!");
            }
        }
    }

    // Example class demonstrating a deadlock situation
    static class Resource {
        public synchronized void method1(Resource r) {
            System.out.println(Thread.currentThread().getName() + " acquired method1");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            r.method2(this);
        }

        public synchronized void method2(Resource r) {
            System.out.println(Thread.currentThread().getName() + " acquired method2");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            r.method1(this);
        }
    }

    // Solution class to avoid deadlock
    static class SafeResource {
        public void method1(SafeResource r) {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " acquired Safe method1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
            r.method2();
        }

        public void method2() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " acquired Safe method2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }
    }

    // Example class demonstrating Fork/Join framework
    static class FibonacciTask extends RecursiveTask<Integer> {
        private final int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            FibonacciTask task1 = new FibonacciTask(n - 1);
            task1.fork(); // Asynchronously execute task1
            FibonacciTask task2 = new FibonacciTask(n - 2);
            return task2.compute() + task1.join(); // Compute task2 and wait for task1 to complete
        }
    }

    public static void main(String[] args) {
        // 1. Thread Interruption Example
        Thread interruptibleThread = new Thread(new InterruptibleTask(), "InterruptibleThread");
        interruptibleThread.start();

        try {
            Thread.sleep(2000); // Let the thread run for a while
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        interruptibleThread.interrupt(); // Interrupt the thread

        // 2. Deadlock Scenario Example
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Thread deadlockThread1 = new Thread(() -> resource1.method1(resource2), "DeadlockThread-1");
        Thread deadlockThread2 = new Thread(() -> resource2.method1(resource1), "DeadlockThread-2");

        deadlockThread1.start();
        deadlockThread2.start();

        try {
            deadlockThread1.join();
            deadlockThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Deadlock Solution Example
        SafeResource safeResource1 = new SafeResource();
        SafeResource safeResource2 = new SafeResource();

        Thread safeThread1 = new Thread(() -> safeResource1.method1(safeResource2), "SafeThread-1");
        Thread safeThread2 = new Thread(() -> safeResource2.method1(safeResource1), "SafeThread-2");

        safeThread1.start();
        safeThread2.start();

        try {
            safeThread1.join();
            safeThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Fork/Join Framework Example
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask fibonacciTask = new FibonacciTask(10);
        int result = pool.invoke(fibonacciTask);
        System.out.println("Fibonacci result: " + result);
    }
}
