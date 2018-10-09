package space.efremov.searchrequestlogger.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
class SearchResultDao {

    @JsonProperty("search_engine")
    private String searchEngine;

    @JsonProperty("values")
    private List<String> values;

}
