package space.efremov.searchrequestlogger.service;

import space.efremov.searchrequestlogger.model.SearchRequest;

import java.util.Optional;

public interface SearchRequestStore {

    void put(SearchRequest element);

    Optional<SearchRequest> get();

    Iterable<SearchRequest> get(int size);

    void raisedException();
}
