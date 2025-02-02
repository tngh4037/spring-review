package hello.core.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // for logging
    @Pointcut("execution(* hello.core.order..*(..))")
    public void allOrder(){} // pointcut signature

    // for transaction
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {} // pointcut signature

    // 조합
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {} // pointcut signature

}
