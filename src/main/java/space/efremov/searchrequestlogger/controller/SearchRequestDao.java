package space.efremov.searchrequestlogger.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class SearchRequestDao {

    @JsonProperty("event_timestamp")
    @NotNull
    private Long eventTimestamp;

    @JsonProperty("uid")
    @NotBlank
    private String uid;

    @JsonProperty("search_text")
    private String searchText;

    @JsonProperty("long")
    private Long longitude;

    @JsonProperty("lat")
    private Long latitude;

    @JsonProperty("search_result")
    private SearchResultDao searchResult;
}
