public class Step5_ExtendThread {

    // 방식 1: `Thread` 클래스를 상속하고 `run()` 메서드를 재정의합니다.
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("[" + getName() + "] Thread 상속 방식으로 실행됩니다.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 방식 1: `Thread`를 상속한 클래스의 인스턴스를 생성해 실행합니다.
        Thread t1 = new MyThread();
        t1.setName("extend-thread");
        t1.start();

        // 방식 2: `Runnable`을 람다식으로 구현한 뒤 `Thread`에 전달합니다.
        Thread t2 = new Thread(
                () -> System.out.println("[" + Thread.currentThread().getName() + "] Runnable 구현 방식으로 실행됩니다."),
                "runnable-thread");
        t2.start();

        t1.join();
        t2.join();

        // 두 방식 모두 새로운 스레드에서 `run()` 메서드가 실행되지만, 일반적으로는 `Runnable` 방식 사용을 권장합니다.
        // Java는 단일 상속만 지원하기 때문에 `Thread`를 상속하면 다른 클래스를 상속할 수 없습니다.
        // 반면 `Runnable`은 인터페이스이므로 다른 클래스 상속에 제약이 없고,
        // 작업(로직)과 실행(스레드)을 분리할 수 있다는 장점이 있습니다.
        System.out.println("[main] 두 방식 모두 새 스레드에서 실행되었습니다.");
    }
}