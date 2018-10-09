package space.efremov.searchrequestlogger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.service.SearchRequestService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController("/")
public class RequestLoggerController {

    private final SearchRequestService service;

    public RequestLoggerController(SearchRequestService service) {
        this.service = service;
    }

    //    @PerformanceTracing
    @PostMapping("add")
    @ResponseStatus(value = HttpStatus.OK)
    public void add(@RequestBody SearchRequest request) {
//        service.requestProcessing(request);
    }

    @GetMapping("find")
    public List<SearchRequest> find(
        @RequestParam(name = "startDate", required = false) ZonedDateTime startDate,
        @RequestParam(name = "endDate", required = false) ZonedDateTime endDate) {
        return service.find(startDate, endDate);
    }

    @GetMapping("count")
    public Long count() {
        return service.count();
    }

    @GetMapping("clear")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void clear() {
        service.clear();
    }

    @GetMapping("exception")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ex() {
        service.raisedException();
    }

}
