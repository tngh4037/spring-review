package hello.core.proxyfactory;

import hello.core.common.advice.TimeAdvice;
import hello.core.common.service.ConcreteService;
import hello.core.common.service.ServiceImpl;
import hello.core.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class jdk.proxy3.$Proxy13

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 당연히 내가 코드로 직접 만든 프록시에는 동작하지 않고, 프록시팩토리를 통해서 만들어진 프록시에 대해서만 기능한다.
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class hello.core.common.service.ConcreteService$$SpringCGLIB$$0

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 당연히 내가 코드로 직접 만든 프록시에는 동작하지 않고, 프록시팩토리를 통해서 만들어진 프록시에 대해서만 기능한다.
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면, 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class hello.core.common.service.ServiceImpl$$SpringCGLIB$$0

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 당연히 내가 코드로 직접 만든 프록시에는 동작하지 않고, 프록시팩토리를 통해서 만들어진 프록시에 대해서만 기능한다.
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
