public class Step1_MainThread {
    public static void main(String[] args) {
        // `main()`을 실제로 실행하는 주체를 확인합니다.
        // JVM이 시작되면 `main`이라는 이름의 스레드가 자동으로 생성됩니다.
        // 이 `main` 스레드가 `main()` 메서드를 위에서부터 순차적으로 실행합니다.
        Thread current = Thread.currentThread();

        System.out.println("Hello World!");
        System.out.println("이 코드를 실행 중인 스레드 이름 = " + current.getName());
        System.out.println("스레드 ID = " + current.threadId());
    }
}