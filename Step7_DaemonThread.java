public class Step7_DaemonThread {

    public static void main(String[] args) {
        // 데몬 스레드는 일반 스레드(사용자 스레드)의 작업을 보조하는 스레드입니다.
        // JVM은 모든 사용자 스레드가 종료되면 데몬 스레드가 남아 있더라도 기다리지 않고 종료합니다.

        Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("[daemon] 백그라운드에서 계속 실행 중입니다.");
                sleep(200);
            }
        }, "daemon-thread");

        daemon.setDaemon(true); // 데몬 스레드로 설정합니다. `start()`를 호출하기 전에 반드시 설정해야 합니다.
        daemon.start();

        // `main`은 사용자 스레드입니다. 잠시 작업을 수행한 뒤 종료합니다.
        sleep(600);
        System.out.println("[main] main 스레드가 종료됩니다.");

        // `main` 사용자 스레드가 종료되면, 무한 반복 중이던 데몬 스레드도 JVM과 함께 즉시 종료됩니다.
        // `setDaemon(true)`를 제거하면 해당 스레드는 사용자 스레드로 동작하게 되며,
        // 무한 반복이 끝나지 않기 때문에 프로그램도 종료되지 않습니다.
    }

    static void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}