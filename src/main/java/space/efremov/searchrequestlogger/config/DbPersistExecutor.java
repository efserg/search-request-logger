package space.efremov.searchrequestlogger.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class DbPersistExecutor {

    @Value("${app.dbpersist.thread.core-pool-size}")
    private int corePoolSize;

    @Value("${app.dbpersist.thread.max-pool-size}")
    private int maxPoolSize;

    @Value("${app.dbpersist.thread.keep-alive-seconds}")
    private int keepAliveSeconds;

    @Value("${app.dbpersist.queue-capacity}")
    private int queueCapacity;

    @Bean
    @Qualifier("dbPersistExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);

        return threadPoolTaskExecutor;
    }
}
