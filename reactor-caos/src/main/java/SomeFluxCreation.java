import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class SomeFluxCreation {

    public static void main(String[] args) {
        doOnRequestExample();

    }

    public static void fluxError() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) throw new RuntimeException("Got to 4");
                    return i;
                });


        ints.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
    }

    public static void fluxAllCallBack() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"));
    }

    public static void baseSubscriber() {
        SampleSubscriber<Integer> ss = new SampleSubscriber<>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> {
                    System.out.println("Done");
                },
                s -> s.request(10));


        System.out.println("-----------------------------------------------------");
        ints.subscribe(ss);
    }


    public static void doOnRequestExample() {
        Flux.range(1, 10)
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        request(integer);
                        //System.out.println("Cancelling after having received " + integer);
                        //cancel();
                    }
                });
    }

    static class SampleSubscriber<T> extends BaseSubscriber<T> {

        public void hookOnSubscribe(Subscription subscription) {
            System.out.println("Subscribed");
            request(1);
        }

        public void hookOnNext(T value) {
            System.out.println(value);
            request(1);
        }


    }
}
