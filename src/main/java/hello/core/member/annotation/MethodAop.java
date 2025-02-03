package hello.core.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 메서드에 붙일 수 있는 애노테이션
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 런타임으로 해야, 실행 이후에 동적으로 애노테이션을 읽을 수 있다.
public @interface MethodAop {
    String value();
}
