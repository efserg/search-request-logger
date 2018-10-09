package space.efremov.searchrequestlogger.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "search_request")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(exclude = {"id"})
public class SearchRequest {

    @Id
    private String id;

    @Field("event_date")
    private LocalDate eventTimestamp;

    @Field("uid")
    private String uid;

    @Field("search_text")
    private String searchText;

    @Field("long")
    private Long longitude;

    @Field("lat")
    private Long latitude;

    @Field("search_result")
    private String searchResult;

}
