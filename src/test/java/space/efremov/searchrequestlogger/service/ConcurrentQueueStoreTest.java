package space.efremov.searchrequestlogger.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.searchrequestlogger.model.SearchRequest;
import space.efremov.searchrequestlogger.model.SearchResult;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConcurrentQueueStoreTest {

    @Autowired
    private SearchRequestStore store;

    private Long curr = ZonedDateTime.now().getLong(ChronoField.INSTANT_SECONDS);

    private final int REQUEST_COUNT = 10;

    private List<SearchRequest> requests = LongStream.range(curr, curr + REQUEST_COUNT).mapToObj(i -> new SearchRequest(i, "UID" + i, "Search text", (double) i / 17471, (double) i / 18181, new SearchResult("Search engine" + i, Arrays.asList("values1", "values2", "values3")))).collect(Collectors.toList());

    @Test
    public void storeShouldPersistOneObject() {
        final SearchRequest element = requests.get(0);
        store.put(element);
        final Optional<SearchRequest> get = store.get();
        assertThat(get).contains(element);
    }

    @Test
    public void storeShouldPersistObjects() {
        requests.forEach(store::put);
        final Iterable<SearchRequest> storeRequests = store.get(REQUEST_COUNT * 2);
        assertThat(requests).containsAll(storeRequests);
    }
}
