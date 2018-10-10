package space.efremov.searchrequestlogger.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.repository.RequestLogRepository;

import java.time.LocalDate;
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
    public void processingRequest(SearchRequest request) {
        store.put(request);
    }

    @Transactional(readOnly = true)
    public Long count(LocalDate date) {
        return Optional.ofNullable(date).map(logRepository::countByEventDate).orElse(logRepository.count());
    }

    public void clear() {
        logRepository.deleteAll();
    }

    public void raisedException() {
        store.raisedException();
    }
}
