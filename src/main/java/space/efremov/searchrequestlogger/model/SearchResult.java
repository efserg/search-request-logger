package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
class SearchResult {

    @JsonProperty("search_engine")
    @Field("search_engine")
    private String searchEngine;

    @JsonProperty("values")
    @Field("values")
    private List<String> values;

}
