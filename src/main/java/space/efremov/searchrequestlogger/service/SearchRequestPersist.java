package space.efremov.searchrequestlogger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.repository.RequestLogRepository;

@Service
public class SearchRequestPersist {

    @Value("${app.dbpersist.batch-size}")
    private int batchSize;

    private final SearchRequestStore store;

    private final RequestLogRepository logRepository;

    public SearchRequestPersist(SearchRequestStore store, RequestLogRepository logRepository) {
        this.store = store;
        this.logRepository = logRepository;
    }

    //    @Scheduled(fixedDelayString = "${app.dbpersist.delay}")
    @Transactional
    public void persist() {
        final Iterable<SearchRequest> requests = store.get(batchSize);
        logRepository.saveAll(requests);
    }


}
