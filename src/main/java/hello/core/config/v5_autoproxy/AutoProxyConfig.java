package hello.core.config.v5_autoproxy;

import hello.core.config.AppV1Config;
import hello.core.config.AppV2Config;
import hello.core.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.core.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    // @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // @Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.core.app..*(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.core.app..*(..)) && !execution(* hello.core.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
