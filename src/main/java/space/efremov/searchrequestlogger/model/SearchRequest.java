package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(exclude = {"id"})
public class SearchRequest {

    @Id
    @JsonIgnore
    private String id;

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
    private SearchResult searchResult;

}
