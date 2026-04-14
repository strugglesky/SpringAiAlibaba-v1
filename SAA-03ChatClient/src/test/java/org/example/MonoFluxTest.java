package org.example;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class MonoFluxTest {
    @Test
    public void test() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        flux.subscribe(e -> System.out.println(e+1));

        flux.subscribe(System.out::println);
    }
    @Test
    public void test2() {
        Mono<Integer> mono = Mono.just(1);
        mono.subscribe(e -> System.out.println(e+1));
    }
    @Test
    public void test3() {
        Flux<Integer> flux = Flux.range(1,10);
        flux.map(e -> {
                    if (e == 10) {
                        throw new RuntimeException("Error");
                    }
                    return e * 2;}
                )
                .subscribe(System.out::println,
                        throwable -> System.out.println("Error" + throwable),
                        () -> System.out.println("Completed"));

    }
}
