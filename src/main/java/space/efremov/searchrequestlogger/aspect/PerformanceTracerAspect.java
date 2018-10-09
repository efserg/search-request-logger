package space.efremov.searchrequestlogger.aspect;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Component
@Log4j2
public class PerformanceTracerAspect {

    private final String alphabet = "ABCDEFGHIKLMNOPQRSTVXYZ";
    private final Random r = new Random();

    @Around(value = "@annotation(space.efremov.searchrequestlogger.aspect.PerformanceTracing)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final String id = randomId();
        final long start = System.currentTimeMillis();

        log.warn("{} Start method \"{}\" with args: {}", id, joinPoint.getSignature().getName()); //, Arrays.toString(joinPoint.getArgs()));
        final Object result = joinPoint.proceed();
        log.warn("{} End method \"{}\", duration {} ms", id, joinPoint.getSignature().getName(), System.currentTimeMillis() - start);
        return result;
    }

    private String randomId() {
        return alphabet.charAt(r.nextInt(alphabet.length())) + "-" + (r.nextInt(900) + 100);
    }
}
