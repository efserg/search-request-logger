package space.efremov.searchrequestlogger.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.model.SearchResult;
import space.efremov.searchrequestlogger.repository.RequestLogRepository;
import space.efremov.searchrequestlogger.service.SearchRequestService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@WebAppConfiguration
@WebMvcTest(RequestLoggerController.class)
public class RequestLoggerControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SearchRequestService service;

    @MockBean
    private RequestLogRepository logRepository;

    private MockMvc mockMvc;

    private final ZonedDateTime current = ZonedDateTime.now();
    private final Long RECORDS_COUNT_WITH_DATE = 2L;
    private final Long RECORDS_COUNT_WITHOUT_DATE = 42L;

    private final SearchRequestTest request = new SearchRequestTest(current.getLong(ChronoField.INSTANT_SECONDS), "UID-12345", "Search text", 12.345d, 67.89d, new SearchResult("Search engine", Arrays.asList("values1", "values2", "values3")));

    @Before
    public void init() {
        given(service.count(any(LocalDate.class))).willReturn(RECORDS_COUNT_WITH_DATE);
        given(service.count(isNull())).willReturn(RECORDS_COUNT_WITHOUT_DATE);

        doNothing().when(service).processingRequest(any(SearchRequest.class));

        this.documentationHandler = document("{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(this.documentationHandler)
            .build();

    }

    @Test
    public void whenUserSendCountRequestWithDateShouldReturnCount() throws Exception {
        mockMvc.perform(get("/count").param("date", current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
            .andExpect(status().isOk())
            .andExpect(content().string(RECORDS_COUNT_WITH_DATE.toString()))
            .andDo(documentationHandler.document(
                requestParameters(
                    parameterWithName("date").description("Date in format yyyy-MM-dd"))
            ));
    }

    @Test
    public void whenUserSendCountRequestWithoutDateShouldReturnCount() throws Exception {
        mockMvc.perform(get("/count"))
            .andExpect(status().isOk())
            .andExpect(content().string(RECORDS_COUNT_WITHOUT_DATE.toString()))
            .andDo(documentationHandler.document());
    }

    @Test
    public void whenUserSendAddRequestShouldReturnOk() throws Exception {
        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(documentationHandler.document(
                requestFields(fieldWithPath("event_timestamp").description("The event timestamp").type(Integer.class),
                    fieldWithPath("uid").description("The event UID").type(String.class),
                    fieldWithPath("search_text").description("The search request text").type(String.class),
                    fieldWithPath("long").description("Longitude").type(Double.class).optional(),
                    fieldWithPath("lat").description("Latitude").type(Double.class).optional(),
                    fieldWithPath("search_result").description("The result of search").type(SearchResult.class).optional(),
                    fieldWithPath("search_result.search_engine").description("The engine of search").type(String.class),
                    fieldWithPath("search_result.values[]").description("The values of search").type(String.class))
            ));
    }

    @Test
    public void whenUserSendClearRequestShouldReturnNoContent() throws Exception {
        mockMvc.perform(get("/clear"))
            .andExpect(status().isNoContent())
            .andDo(documentationHandler.document());
    }

    private static class SearchRequestTest {
        @JsonProperty("event_timestamp")
        private Long eventTimestamp;
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("search_text")
        private String searchText;
        @JsonProperty("long")
        private Double longitude;
        @JsonProperty("lat")
        private Double latitude;
        @JsonProperty("search_result")
        private SearchResult searchResult;

        private SearchRequestTest() {
        }

        SearchRequestTest(Long eventTimestamp, String uid, String searchText, Double longitude, Double latitude, SearchResult searchResult) {
            this.eventTimestamp = eventTimestamp;
            this.uid = uid;
            this.searchText = searchText;
            this.longitude = longitude;
            this.latitude = latitude;
            this.searchResult = searchResult;
        }
    }

}
