package space.efremov.searchrequestlogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "space.efremov.searchrequestlogger.repository")
public class SearchRequestLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchRequestLoggerApplication.class, args);
	}
}
