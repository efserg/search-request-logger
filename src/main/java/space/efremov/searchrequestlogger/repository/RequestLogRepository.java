package space.efremov.searchrequestlogger.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import space.efremov.searchrequestlogger.model.SearchRequest;

import java.time.LocalDate;

public interface RequestLogRepository extends MongoRepository<SearchRequest, String> {

    Long countByEventDate(LocalDate date);

}
