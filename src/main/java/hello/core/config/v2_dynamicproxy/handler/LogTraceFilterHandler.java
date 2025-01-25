package hello.core.config.v2_dynamicproxy.handler;

import hello.core.trace.TraceStatus;
import hello.core.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 메서드 이름 필터
        String name = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, name)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;

        try {
            String className = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();
            status = logTrace.begin(className + "." + methodName + "()"); // ex) OrderController.request()

            // target 호출
            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
