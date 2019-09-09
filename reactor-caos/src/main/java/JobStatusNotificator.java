import reactor.core.publisher.Flux;

public class JobStatusNotificator {

    private Flux<String> notifications;
    private JobStatusListener jobNotifierListener;

    public JobStatusNotificator() {
        notifications = Flux.create(emitter -> {
            jobNotifierListener = t -> emitter.next(t);
        });
    }

    public void notify(String value) {
        if (jobNotifierListener == null) {
            throw new IllegalStateException("The job notification listener must be set before send any value");
        }

        jobNotifierListener.listen(value);
    }

    public Flux<String> getNotifications() {
        return notifications;
    }
}
