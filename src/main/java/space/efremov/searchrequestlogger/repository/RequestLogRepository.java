package space.efremov.searchrequestlogger.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import space.efremov.searchrequestlogger.model.SearchRequest;

import java.util.List;

public interface RequestLogRepository extends MongoRepository<SearchRequest, String> {

    List<SearchRequest> findByEventTimestampBetween(Long startDate, Long endDate);

}
