package space.efremov.searchrequestlogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.service.SearchRequestService;

import java.time.LocalDate;

@RestController("/")
public class RequestLoggerController {

    private final SearchRequestService service;

    public RequestLoggerController(@Autowired SearchRequestService service) {
        this.service = service;
    }

    //    @PerformanceTracing
    @PostMapping("add")
    @ResponseStatus(value = HttpStatus.OK)
    public void add(@RequestBody SearchRequest request) {
        service.processingRequest(request);
    }

    @GetMapping("count")
    public Long count(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.count(date);
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
