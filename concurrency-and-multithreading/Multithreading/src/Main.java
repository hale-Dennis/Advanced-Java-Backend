import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// BankAccount class representing a bank account with thread-safe operations
class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method to deposit money
    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
    }

    // Synchronized method to withdraw money
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + " but insufficient balance.");
        }
    }

    public int getBalance() {
        return balance;
    }
}

// Runnable class for performing transactions
class Transaction implements Runnable {
    private final BankAccount account;
    private final boolean deposit; // True for deposit, false for withdrawal
    private final int amount;

    public Transaction(BankAccount account, boolean deposit, int amount) {
        this.account = account;
        this.deposit = deposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (deposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }
}

public class Main{
    public static void main(String[] args) {
        // Creating a shared bank account with an initial balance
        BankAccount sharedAccount = new BankAccount(1000);

        // Creating and starting threads directly
        Thread t1 = new Thread(new Transaction(sharedAccount, true, 200));  // Deposit 200
        Thread t2 = new Thread(new Transaction(sharedAccount, false, 150)); // Withdraw 150
        Thread t3 = new Thread(new Transaction(sharedAccount, false, 300)); // Withdraw 300

        t1.start();
        t2.start();
        t3.start();

        // Using join() to wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final balance after direct thread operations: " + sharedAccount.getBalance());

        // Implementing a thread pool to handle multiple transactions
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Adding multiple transactions to the thread pool
        executor.execute(new Transaction(sharedAccount, true, 100));  // Deposit 100
        executor.execute(new Transaction(sharedAccount, false, 50));  // Withdraw 50
        executor.execute(new Transaction(sharedAccount, true, 400));  // Deposit 400
        executor.execute(new Transaction(sharedAccount, false, 700)); // Withdraw 700

        // Shutdown the executor
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }

        System.out.println("Final balance after thread pool operations: " + sharedAccount.getBalance());
    }
}
