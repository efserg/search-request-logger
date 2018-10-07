package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(exclude = {"id"})
class SearchResult {

    @Id
    @JsonIgnore
    private String id;

    @JsonProperty("search_engine")
    private String searchEngine;

    @JsonProperty("values")
    private List<String> values;

}
