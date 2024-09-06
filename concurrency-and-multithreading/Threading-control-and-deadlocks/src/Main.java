import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TicketBookingSystem class to manage ticket booking
class TicketBookingSystem {
    private int availableTickets;

    public TicketBookingSystem(int totalTickets) {
        this.availableTickets = totalTickets;
    }

    // Synchronized method to book tickets
    public synchronized boolean bookTicket(int numTickets) {
        if (numTickets <= availableTickets) {
            System.out.println(Thread.currentThread().getName() + " booked " + numTickets + " ticket(s).");
            availableTickets -= numTickets;
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to book " + numTickets + " ticket(s) but only " + availableTickets + " available.");
            return false;
        }
    }

    public int getAvailableTickets() {
        return availableTickets;
    }
}

// Runnable class for booking tickets
class TicketBookingTask implements Runnable {
    private final TicketBookingSystem ticketBookingSystem;
    private final int numTickets;

    public TicketBookingTask(TicketBookingSystem ticketBookingSystem, int numTickets) {
        this.ticketBookingSystem = ticketBookingSystem;
        this.numTickets = numTickets;
    }

    @Override
    public void run() {
        ticketBookingSystem.bookTicket(numTickets);
    }
}

// Main class demonstrating thread creation, management, synchronization, and thread pool implementation
public class Main {
    public static void main(String[] args) {
        // Creating a ticket booking system with 10 tickets available
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10);

        // Creating and starting threads directly
        Thread t1 = new Thread(new TicketBookingTask(bookingSystem, 3), "User-1"); // Book 3 tickets
        Thread t2 = new Thread(new TicketBookingTask(bookingSystem, 4), "User-2"); // Book 4 tickets
        Thread t3 = new Thread(new TicketBookingTask(bookingSystem, 5), "User-3"); // Book 5 tickets

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

        System.out.println("Tickets remaining after direct thread operations: " + bookingSystem.getAvailableTickets());

        // Implementing a thread pool to handle multiple booking requests
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Adding multiple booking tasks to the thread pool
        executor.execute(new TicketBookingTask(bookingSystem, 2));  // Book 2 tickets
        executor.execute(new TicketBookingTask(bookingSystem, 1));  // Book 1 ticket
        executor.execute(new TicketBookingTask(bookingSystem, 4));  // Book 4 tickets
        executor.execute(new TicketBookingTask(bookingSystem, 2));  // Book 2 tickets

        // Shutdown the executor
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }

        System.out.println("Tickets remaining after thread pool operations: " + bookingSystem.getAvailableTickets());
    }
}
