package space.efremov.searchrequestlogger.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.repository.RequestLogRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SearchRequestService {

    private final SearchRequestStore store;

    private final RequestLogRepository logRepository;

    public SearchRequestService(@Qualifier("concurrentQueue") SearchRequestStore store, RequestLogRepository logRepository) {
        this.store = store;
        this.logRepository = logRepository;
    }

    //    @PerformanceTracing
    @Async("storePersistExecutor")
    public void requestProcessing(SearchRequest request) {
        store.put(request);
    }

    @Transactional(readOnly = true)
    public List<SearchRequest> find(ZonedDateTime startDate, ZonedDateTime endDate) {
        final Long startTimestamp = Optional.ofNullable(startDate).map(c -> c.toInstant().toEpochMilli()).orElse(0L);
        final Long endTimestamp = Optional.ofNullable(endDate).map(c -> c.toInstant().toEpochMilli()).orElse(ZonedDateTime.now().toInstant().toEpochMilli());

        return logRepository.findByEventTimestampBetween(startTimestamp, endTimestamp);
    }

    @Transactional(readOnly = true)
    public Long count() {
        return logRepository.count();
    }

    public void clear() {
        logRepository.deleteAll();
    }

    public void raisedException() {
        store.raisedException();
    }
}
