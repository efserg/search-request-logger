package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SearchResult {

    @JsonProperty("search_engine")
    @Field("search_engine")
    private String searchEngine;

    @JsonProperty("values")
    @Field("values")
    private List<String> values;

}
