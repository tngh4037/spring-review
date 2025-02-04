package hello.core.exam.aop;

import hello.core.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed(); // 한번에 성공하면 return 이므로 그냥 나감.
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}

/**
 Object obj = null;
 for (int i = 1; i <= retry.value(); i++) {
 try {
 obj = joinPoint.proceed();
 break;
 } catch (IllegalStateException e) {
 if (i == retry.value()) {
 throw e;
 }
 }
 }
 return obj;
 */