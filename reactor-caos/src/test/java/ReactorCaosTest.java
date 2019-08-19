import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ReactorCaosTest {

    @Test
    public void createFromIterableTest(){
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("Hello", "World", "Live"));
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void createTest(){
        Flux<Object> fluxFeed = Flux.create(fluxSink -> fluxSink.next("Hello"));
        fluxFeed.subscribe(System.out::println);
    }
}
