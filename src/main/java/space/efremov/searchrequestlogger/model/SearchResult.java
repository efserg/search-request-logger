package space.efremov.searchrequestlogger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@NoArgsConstructor
@Getter
@Setter
public class SearchResult {

    @JsonProperty("search_engine")
    private String searchEngine;

    private ArrayList<String> values;

}
