package browserstack;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncTask {



    public static CompletableFuture pollForCompletion() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        CompletableFuture<String> completionFuture = new CompletableFuture<>();
        AtomicInteger retry= new AtomicInteger();

            final ScheduledFuture<Void> checkFuture = (ScheduledFuture<Void>) executor.scheduleAtFixedRate(() -> {


                System.out.println("Retry count =>"+retry.get());
                try {
                    if (NetworkUtils.isDataReady() || retry.getAndIncrement() >5) {
                        completionFuture.complete("Complete");
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

            }, 0, 20, TimeUnit.SECONDS);



            completionFuture.whenComplete((result, thrown) -> {
                checkFuture.cancel(true);
            });

        return completionFuture;
    }


}



