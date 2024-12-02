package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired
        public TestBean(@Autowired(required = false) Member member) {
            System.out.println("member = " + member);
        }

        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("NoBean1 member = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("NoBean2 member = " + member);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member) {
            System.out.println("NoBean3 member = " + member);
        }
    }

}
