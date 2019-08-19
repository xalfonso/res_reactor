import reactor.core.publisher.Flux;

import java.util.Arrays;

public class MainClass {

    private static Flux<Object> objectFlux;

    public static void main(String[] args) {
        JobStatusNotificator jobStatusNotificator = new JobStatusNotificator();
        jobStatusNotificator.getNotifications().subscribe(x -> System.out.println(Thread.currentThread().getName() + ": Receive Data: " + x));


        //fluxFeed.subscribe(x -> System.out.println(Thread.currentThread().getName()  +": "+ x));

        Thread threadPublisher1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                jobStatusNotificator.notify("Send Data From Thread " + Thread.currentThread().getName() + ".  " + String.valueOf(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPublisher1.setName("threadPublisher1");
        threadPublisher1.start();

        Thread threadPublisher2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                jobStatusNotificator.notify("Send Data From Thread " + Thread.currentThread().getName() + ".  " + String.valueOf(i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPublisher2.setName("threadPublisher2");
        threadPublisher2.start();

        /*Thread threadSubscriber1 = new Thread(() -> {
            jobStatusNotificator.getNotifications().subscribe(x -> System.out.println(Thread.currentThread().getName() + ": Receive Data: " + x));
        });
        threadSubscriber1.setName("threadSubscriber1");
        threadSubscriber1.start();*/
    }
}
