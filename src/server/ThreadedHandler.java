package server;

public class ThreadedHandler<S, X extends Throwable> implements Handler<S, X> {

    private final ExceptionHandler<S,X> next;

    public ThreadedHandler(ExceptionHandler<S, X> next) {
        this.next = next;
    }

    @Override
    public void handle(S s) {
        new Thread(() -> next.handle(s)).start();
    }
}
