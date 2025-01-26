package hello.core.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeAdvice 실행");

        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed(); // target 을 직접 정의하지 X, invocation 안에 이미 target을 가지고 있음
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeAdvice 종료 resultTime={}", resultTime);
        return result;
    }
}
