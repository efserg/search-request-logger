package space.efremov.searchrequestlogger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.repository.RequestLogRepository;

import java.time.ZonedDateTime;
import java.util.List;

@RestController("/")
public class RequestLoggerController {

    private final RequestLogRepository logRepository;

    public RequestLoggerController(RequestLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("find")
    public List<SearchRequest> find(
        @RequestParam(name = "startDate", required = false) ZonedDateTime startDate,
        @RequestParam(name = "endDate", required = false) ZonedDateTime endDate) {
        if (startDate == null && endDate == null) {
            return logRepository.findAll();
        }
        return logRepository.findByEventTimestampBetween(startDate.toInstant().toEpochMilli(), endDate.toInstant().toEpochMilli());
    }

    @GetMapping("find/{id}")
    public SearchRequest findById(@PathVariable("id") String id) {
        return logRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("add")
    @ResponseStatus(value = HttpStatus.OK)
    public SearchRequest add(@RequestBody SearchRequest request) {
        return logRepository.save(request);
    }

}
