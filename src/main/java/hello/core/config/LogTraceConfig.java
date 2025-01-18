package hello.core.config;

import hello.core.trace.logtrace.FieldLogTrace;
import hello.core.trace.logtrace.LogTrace;
import hello.core.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        // return new FieldLogTrace();
        return new ThreadLocalLogTrace();
    }
}
