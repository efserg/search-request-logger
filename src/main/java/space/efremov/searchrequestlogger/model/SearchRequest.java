package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Document(collection = "search_request")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class SearchRequest {

    @Id
    private String id;

    @Field("event_date")
    @NotNull
    private LocalDate eventDate;

    @Field("uid")
    @NotBlank
    private String uid;

    @Field("search_text")
    private String searchText;

    @Field("long")
    private Double longitude;

    @Field("lat")
    private Double latitude;

    @Field("search_result")
    private SearchResult searchResult;

    @JsonCreator
    public SearchRequest(@JsonProperty("event_timestamp") @NotNull Long eventTimestamp,
                         @JsonProperty("uid") @NotBlank String uid,
                         @JsonProperty("search_text") String searchText,
                         @JsonProperty("long") Double longitude,
                         @JsonProperty("lat") Double latitude,
                         @JsonProperty("search_result") SearchResult searchResult) {
        this.eventDate = Instant.ofEpochSecond(eventTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        this.uid = uid;
        this.searchText = searchText;
        this.longitude = longitude;
        this.latitude = latitude;
        this.searchResult = searchResult;
    }

}
