public class Step6_ThreadState {

    public static void main(String[] args) throws InterruptedException {
        // `Thread.State`로 표현되는 스레드의 생명주기 상태는 총 6가지입니다.
        // `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, `TERMINATED`

        // 1) 하나의 스레드가 `NEW` → `RUNNABLE` → `TIMED_WAITING` → `TERMINATED`로
        // 상태가 전환되는 과정을 확인합니다.
        Thread worker = new Thread(() -> sleep(200), "worker");

        System.out.println("start() 호출 전 = " + worker.getState());   // NEW
        worker.start();
        System.out.println("start() 직후   = " + worker.getState());   // RUNNABLE
        sleep(50);
        System.out.println("sleep(200) 중  = " + worker.getState());   // TIMED_WAITING
        worker.join();
        System.out.println("작업 종료 후   = " + worker.getState());   // TERMINATED

        System.out.println("---");

        // 2) `BLOCKED`: 다른 스레드가 점유하고 있는 `synchronized` 락을 획득하기 위해 대기하는 상태입니다.
        Object lock = new Object();
        Thread holder = new Thread(() -> {
            synchronized (lock) {
                sleep(300); // 락을 300ms 동안 점유합니다.
            }
        }, "holder");
        Thread blocked = new Thread(() -> {
            synchronized (lock) { /* 락을 획득할 때까지 대기합니다. */ }
        }, "blocked");

        holder.start();
        sleep(50);          // `holder`가 먼저 락을 획득할 수 있도록 잠시 대기합니다.
        blocked.start();
        sleep(50);          // `blocked`가 락 대기 상태에 진입할 수 있도록 잠시 대기합니다.
        System.out.println("락을 기다리는 blocked = " + blocked.getState()); // BLOCKED
        holder.join();
        blocked.join();

        System.out.println("---");

        // 3) `WAITING`: `wait()`처럼 시간 제한 없이 다른 스레드의 신호를 기다리는 상태입니다.
        Object monitor = new Object();
        Thread waiter = new Thread(() -> {
            synchronized (monitor) {
                try {
                    monitor.wait();  // `notify()`가 호출될 때까지 무기한 대기합니다.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "waiter");

        waiter.start();
        sleep(50);          // `waiter`가 `wait()`를 호출해 대기 상태에 진입할 수 있도록 잠시 대기합니다.
        System.out.println("신호를 기다리는 waiter = " + waiter.getState()); // WAITING
        synchronized (monitor) {
            monitor.notify();   // 대기 중인 `waiter`를 깨웁니다.
        }
        waiter.join();
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}