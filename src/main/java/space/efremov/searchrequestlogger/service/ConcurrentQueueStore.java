package space.efremov.searchrequestlogger.service;

import org.springframework.stereotype.Service;
import space.efremov.searchrequestlogger.aspect.PerformanceTracing;
import space.efremov.searchrequestlogger.model.SearchRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service("concurrentQueue")
public class ConcurrentQueueStore implements SearchRequestStore {

    private final Queue<SearchRequest> queue = new ConcurrentLinkedQueue<>();

    @PerformanceTracing
    @Override
    public void put(SearchRequest element) {
        queue.add(element);
    }

    @Override
    public Optional<SearchRequest> get() {
        return Optional.ofNullable(queue.poll());
    }

    @Override
//    @PerformanceTracing
    public Iterable<SearchRequest> get(int size) {

        List<SearchRequest> result = new ArrayList<>();

        AtomicInteger count = new AtomicInteger();
        Optional<SearchRequest> request;

        do {
            request = get();
            request.ifPresent(r -> {
                result.add(r);
                count.getAndIncrement();
            });
        } while (count.get() < size && request.isPresent());

        return result;

    }

    @Override
    public void raisedException() {
        throw new RuntimeException("Exception raised!");
    }
}
