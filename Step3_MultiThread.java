public class Step3_MultiThread {
    public static void main(String[] args) {
        // 멀티 스레드 환경에서는 개발자가 직접 새로운 스레드를 생성해 작업을 동시에 수행할 수 있습니다.
        // 방법 1: `Runnable`을 `Thread`에 전달해 새로운 스레드를 생성합니다.
        Thread threadA = new Thread(() -> work("작업 A"), "thread-A");
        Thread threadB = new Thread(() -> work("작업 B"), "thread-B");

        // `start()`를 호출해야 새로운 스레드가 실제로 실행됩니다.
        // `run()`을 직접 호출하면 새로운 스레드가 생성되지 않고 현재 스레드에서 실행되므로 주의해야 합니다.
        threadA.start();
        threadB.start();

        // `main` 스레드는 생성한 두 스레드의 작업이 모두 끝날 때까지 대기합니다.
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[main] 두 스레드가 동시에 돌다가 모두 끝남");
    }

    static void work(String name) {
        String threadName = Thread.currentThread().getName();
        for (int i = 1; i <= 3; i++) {
            System.out.println("[" + threadName + "] " + name + " - " + i + "번째");
            sleep(300);
        }
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}