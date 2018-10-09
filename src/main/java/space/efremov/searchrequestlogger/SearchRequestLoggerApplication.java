package space.efremov.searchrequestlogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@EnableMongoRepositories(basePackages = "space.efremov.searchrequestlogger.repository")
@EnableWebMvc
public class SearchRequestLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchRequestLoggerApplication.class, args);
	}
}
