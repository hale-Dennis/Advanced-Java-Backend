
public class Main {
    public static void main(String[] args) {

        Thread countUpThread = new Thread(new MatchOne());
        Thread countDownThread = new Thread(new MatchTwo());

        countUpThread.start();
        countDownThread.start();

        // Wait for both threads to complete
        try {
            countUpThread.join();
            countDownThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Matches completed!");
    }
}