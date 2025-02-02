package hello.core.config.v6_aop.aspect;

import hello.core.trace.TraceStatus;
import hello.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // advisor 를 편리하게 생성해주는 애노테이션
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* hello.core.app..*(..))") // PointCut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // Advice
        TraceStatus status = null;

        // log.info("target={}", joinPoint.getTarget()); // 실제 호출 대상
        // log.info("getArgs={}", joinPoint.getArgs()); // 전달인자
        // log.info("getSignature={}", joinPoint.getSignature()); // join point 시그니처

        try {

            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message); // ex) OrderController.request()

            // target 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

}
