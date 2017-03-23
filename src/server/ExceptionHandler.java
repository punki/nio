package server;

import java.util.function.Consumer;

public class ExceptionHandler<S, X extends Throwable> implements Handler<S, X> {
    private final Handler<S, X> next;
    private final Consumer<Throwable> exceptionConsumer;

    public ExceptionHandler(Handler<S, X> next, Consumer<Throwable> exceptionConsumer) {
        this.next = next;
        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    public void handle(S s) {
        try {
            next.handle(s);
        } catch (Throwable t) {
            exceptionConsumer.accept(t);
        }

    }
}
