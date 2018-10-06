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

    @GetMapping("find")
    public List<SearchRequest> find(
        @RequestParam(name = "startDate", required = false) ZonedDateTime startDate,
        @RequestParam(name = "endDate", required = false) ZonedDateTime endDate) {
        return service.find(startDate, endDate);
    }

    @PostMapping("add")
    @ResponseStatus(value = HttpStatus.OK)
    public void add(@RequestBody SearchRequest request) {
        service.persist(request);
    }

}
