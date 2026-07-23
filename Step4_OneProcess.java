public class Step4_OneProcess {
    public static void main(String[] args) {
        // JVM도 하나의 프로세스라는 점을 확인합니다.
        // 프로세스는 하나(PID 1개)이지만, 그 내부에서는 여러 스레드가 동시에 실행됩니다.
        long pid = ProcessHandle.current().pid();
        System.out.println("이 JVM 프로세스의 PID = " + pid);

        // 프로세스가 바로 종료되지 않도록 실행 중인 스레드를 몇 개 더 생성합니다.
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                // 스레드가 약 2초 동안 실행 상태를 유지하도록 합니다.
                sleep(2000);
            }, "worker-" + id);
            t.start();
        }

        // 현재 JVM 프로세스 내부에 존재하는 모든 스레드를 나열합니다.
        System.out.println("\n=== JVM(프로세스 1개) 안에서 동시에 살아있는 스레드들 ===");
        Thread.getAllStackTraces().keySet().stream()
                .sorted((a, b) -> Long.compare(a.threadId(), b.threadId()))
                .forEach(t -> System.out.println("  - " + t.getName() +
                        " (id=" + t.threadId() + ", daemon=" + t.isDaemon() + ")"));

        System.out.println("\n프로세스는 1개, 스레드는 여러 개 => 싱글 프로세스 / 멀티 스레딩");
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}