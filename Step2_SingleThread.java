public class Step2_SingleThread {
    public static void main(String[] args) {
        // 싱글 스레드 환경에서는 `main` 스레드 하나가 모든 작업을 순서대로 처리합니다.
        // 즉, A 작업이 완전히 끝난 이후에야 B 작업이 시작됩니다.
        work("작업 A");
        work("작업 B");

        System.out.println("[main] 두 작업 모두 순서대로 끝남");
    }

    static void work(String name) {
        String threadName = Thread.currentThread().getName();
        for (int i = 1; i <= 3; i++) {
            System.out.println("[" + threadName + "] " + name + " - " + i + "번째");
            // 작업이 오래 걸리는 상황을 재현하기 위해 의도적으로 실행을 지연합니다.
            sleep(300);
        }
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}