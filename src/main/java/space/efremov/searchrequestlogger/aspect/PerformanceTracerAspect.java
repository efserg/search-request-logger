package space.efremov.searchrequestlogger.aspect;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Component
@Log4j2
public class PerformanceTracerAspect {

    @Value("${app.performance-trace}")
    private boolean isEnable;

    private final String alphabet = "ABCDEFGHIKLMNOPQRSTVXYZ";
    private final Random r = new Random();

    @Around(value = "@annotation(space.efremov.searchrequestlogger.aspect.PerformanceTracing)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isEnable) {
            final String id = randomId();
            final long start = System.currentTimeMillis();
            log.warn("{} Start method \"{}\"", id, joinPoint.getSignature().getName());
            final Object result = joinPoint.proceed();
            log.warn("{} End method \"{}\", duration {} ms", id, joinPoint.getSignature().getName(), System.currentTimeMillis() - start);
            return result;
        } else {
            return joinPoint.proceed();
        }
    }

    private String randomId() {
        return alphabet.charAt(r.nextInt(alphabet.length())) + "-" + (r.nextInt(900) + 100);
    }
}
