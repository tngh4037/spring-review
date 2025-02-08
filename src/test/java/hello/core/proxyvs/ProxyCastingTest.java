package hello.core.proxyvs;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시
        
        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy(); // 성공

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
        assertThatThrownBy(() -> {
                    MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
                }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglib() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy(); // 성공

        // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy; // 성공
    }

}
