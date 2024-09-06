

class MatchOne implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Goal in match ONE!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Goal count thread interrupted.");
            }
        }
    }
}