package hello.core.jdkdynamic;

import hello.core.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class}, // 어떤 인터페이스를 기반으로 프록시를 생성할지 타입 지정
                handler // target, 프록시가 처리할 로직 지정
        );

        proxy.call();

        log.info("targetClass={}", target.getClass()); // targetClass=class hello.core.jdkdynamic.code.AImpl
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class jdk.proxy3.$Proxy12
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class}, // 어떤 인터페이스를 기반으로 프록시를 생성할지 타입 지정
                handler // target, 프록시가 처리할 로직 지정
        );

        proxy.call();

        log.info("targetClass={}", target.getClass()); // targetClass=class hello.core.jdkdynamic.code.AImpl
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class jdk.proxy3.$Proxy12
    }
}

// 자바에서 동적 프록시 클래스는 런타임 시에 생성되며, 이 프록시 클래스가 어떤 클래스로 로드될지를 결정하는 것이 바로 ClassLoader입니다.
// ClassLoader는 JVM에 의해 클래스를 메모리로 로드하는 책임을 지고 있습니다.
// 이때 ClassLoader를 넘겨주지 않으면, 동적 프록시 클래스가 어떤 클래스 로더를 통해 로드되어야 할지 알 수 없게 되므로, 이를 명시적으로 지정해야 하는 것입니다.
