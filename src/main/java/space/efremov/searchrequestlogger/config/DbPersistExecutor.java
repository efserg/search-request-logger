package space.efremov.searchrequestlogger.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Log4j2
public class DbPersistExecutor implements AsyncConfigurer {

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

    @Override
    public Executor getAsyncExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("Exception message: {}; Method name: {}; params: {}", ex.getMessage(), method.getName(), Arrays.toString(params));
            ex.printStackTrace();
        };
    }
}
