package server;

public class PrintingHandler<S, X extends Throwable> implements Handler<S, X> {
    private final Handler<S,X> next;

    public PrintingHandler(Handler<S, X> next) {
        this.next = next;
    }

    @Override
    public void handle(S s) throws X {
        System.out.println("Handling: "+s);
        try{
            next.handle(s);
        }finally {
            System.out.println("Finished: "+s);
        }
    }
}
