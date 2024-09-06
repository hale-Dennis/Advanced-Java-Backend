
class MatchTwo implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Goal in match TWO!!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Match two thread interrupted.");
            }
        }
    }
}
