package server;

import java.util.concurrent.ExecutorService;

public class ExecutorServiceHandler<S, X extends Throwable> implements Handler<S, X> {

    private final ExecutorService pool;
    private final ExceptionHandler<S,X> next;

    public ExecutorServiceHandler(ExecutorService pool, ExceptionHandler<S, X> next) {
        this.pool = pool;
        this.next = next;
    }

    @Override
    public void handle(S s) {
        pool.submit(() -> next.handle(s));
    }
}
