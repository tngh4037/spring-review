package hello.core.config.v1_proxy.concrete_proxy;

import hello.core.app.v2.OrderRepositoryV2;
import hello.core.app.v2.OrderServiceV2;
import hello.core.trace.TraceStatus;
import hello.core.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target,
                                     LogTrace logTrace) {
        super(null); // OrderServiceConcreteProxy 클래스는 프록시 역할로 만들 용도이기 때문에, 위의 부모 클래스를 전혀 사용하지 않는다. (부모는 다형성 용도로만 필요함)
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;

        try {
            status = logTrace.begin("OrderService.orderItem()");

            // target 호출
            target.orderItem(itemId);

            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
