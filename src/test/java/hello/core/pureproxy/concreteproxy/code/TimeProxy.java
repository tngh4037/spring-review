package hello.core.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = concreteLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);

        return result;
    }
}

// 구체 클래스를 상속받은 프록시에서, 굳이 구체클래스를 멤버변수로 받는 이유 (super를 호출해도 되지않나..)
// - https://www.inflearn.com/community/questions/375792/%EA%B5%AC%EC%B2%B4-%ED%81%B4%EB%9E%98%EC%8A%A4-%EA%B8%B0%EB%B0%98-%ED%94%84%EB%A1%9D%EC%8B%9C-%EC%98%88%EC%A0%9C2
// - https://www.inflearn.com/community/questions/1162673/%EC%BD%98%ED%81%AC%EB%A6%AC%ED%8A%B8%ED%94%84%EB%A1%9D%EC%8B%9C%EB%A5%BC-%EA%B5%AC%ED%98%84%EC%8B%9C-%EC%9B%90%EB%B3%B8-%EA%B5%AC%EC%B2%B4%EB%A5%BC-%ED%8C%8C%EB%9D%BC%EB%A9%94%ED%84%B0%EB%A1%9C-%EB%B0%9B%EB%8A%94%EC%9D%B4%EC%9C%A0
// - ex)
/*
// [ 콘크리트프록시를 구현시 원본 구체를 파라메터로 받는이유 ]
// 프록시는 원본 객체를 상속 받기 때문에, 프록시에서 오버라이딩 한 기능이 호출되어 버릴 수 있다.
// 이런 문제가 발생하지 않도록, 프록시 객체와 원본 객체를 명확하게 분리하기 위해서 별도로 나누게 됩니다.
public class TestClass {

    @Test
    void test() {
        B b = new B();
        b.print();
    }

    static class A {
        public void print() {
            System.out.println("A.print");
            parse(); // B.parse
        }
        public void parse() {
            System.out.println("A.parse");
        }
    }

    static class B extends A {
        @Override
        public void print() {
            super.print();
        }
        @Override
        public void parse() {
            System.out.println("B.parse");
        }
    }
}
 */
// 자바에서 메서드 오버라이딩이 이루어졌을 때, 부모 클래스에서 호출된 메서드라도 실제 객체 타입에 맞는 오버라이딩된 메서드가 호출됩니다.
// [ 동적 메서드 호출 ]
//  ㄴ 자바는 객체가 어떤 타입인지에 따라 실제로 호출되는 메서드를 결정합니다.
//  ㄴ b가 B 클래스의 객체이므로, A 클래스에서 parse()를 호출하더라도 실제로는 B 클래스의 parse()가 실행됩니다.
